package team_alcoholic.jumo_server.v1.liquorsearch.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import team_alcoholic.jumo_server.v1.liquorsearch.domain.LiquorSearch;

import java.util.List;

public interface LiquorSearchRepository extends ElasticsearchRepository<LiquorSearch, Long> {

    @Query("""
        {
            "multi_match": {
                "query": "#{#keyword}",
                "fields": ["ko_name.jaso", "en_name.ngram"]
            }
        }
    """)
    List<LiquorSearch> search(@Param("keyword") String keyword, Pageable pageable);
}
