package team_alcoholic.jumo_server.v1.liquorsearch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v1.liquorsearch.domain.LiquorSearch;
import team_alcoholic.jumo_server.v1.liquorsearch.repository.LiquorSearchRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiquorSearchService {

    private final LiquorSearchRepository liquorSearchRepository;

    public List<LiquorSearch> search(String keyword) {
        return liquorSearchRepository.search(keyword, PageRequest.of(0, 20));
    }
}
