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
    private static final String indexName = "liquors";

    public LiquorSearchService(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    public List<LiquorES> search(String keyword) {

        log.info("검색어: "+ keyword);

        List<LiquorES> resultList = new ArrayList<>();

        SearchRequest request = new SearchRequest.Builder()
            .index(indexName)
            .query(q -> q.multiMatch(m -> m
                .query(keyword)
                .fields("ko_name.jaso", "en_name.english")
            ))
            .size(20)
            .build();

        SearchResponse<LiquorES> response = null;
        try {
            log.info("opensearch 요청: "+ keyword);

            response = openSearchClient.search(request, LiquorES.class);
            for (Hit<LiquorES> hit : response.hits().hits()) {
                resultList.add(hit.source());
            }

            log.info("opensearch 응답: "+ resultList);

            return resultList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
