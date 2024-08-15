package team_alcoholic.jumo_server.domain.liquorsearch.service;

import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.liquorsearch.domain.LiquorES;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LiquorSearchService {

    private final OpenSearchClient openSearchClient;
    private static final String indexName = "test";

    public LiquorSearchService(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    public List<LiquorES> search(String keyword) {

        log.info("들어온 키워드"+ keyword);

        List<LiquorES> resultList = new ArrayList<>();

//        SearchRequest request = new SearchRequest.Builder()
//            .index(indexName)
//            .query(q -> q.queryString(qs -> qs.fields("ko_name").query(keyword)))
//            .build();

//        SearchRequest request = new SearchRequest.Builder()
//                .index(indexName)
//                .query(q -> q.match(m -> m.field("ko_name")
//                        .query(FieldValue.of(keyword))
//                        .fuzziness("AUTO")))
//                .build();




        SearchRequest request = new SearchRequest.Builder()
            .index(indexName)
            .query(q -> q.multiMatch(m -> m
                .query(keyword)
                .fields("ko_name.nori", "en_name.english")
                .fuzziness("AUTO")
            ))
            .size(20)
            .build();

        SearchResponse<LiquorES> response = null;
        try {
            log.info("호출"+ keyword);

            response = openSearchClient.search(request, LiquorES.class);
            for (Hit<LiquorES> hit : response.hits().hits()) {
                resultList.add(hit.source());

            }
                log.info("검색 결과 수"+resultList.size());
            log.info("검색 결과"+ resultList);
            return resultList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
