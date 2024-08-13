package team_alcoholic.jumo_server.domain.liquorsearch.service;

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

@Service
public class LiquorSearchService {

    private final OpenSearchClient openSearchClient;
    private static final String indexName = "liquors";

    public LiquorSearchService(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    public List<LiquorES> search(String keyword) {
        List<LiquorES> resultList = new ArrayList<>();

//        SearchRequest request = new SearchRequest.Builder()
//            .index(indexName)
//            .query(q -> q.queryString(qs -> qs.fields("ko_name").query(keyword)))
//            .build();

        SearchRequest request = new SearchRequest.Builder()
                .index(indexName)
                .query(q -> q.match(m -> m.field("ko_name")
                        .query(FieldValue.of(keyword))
                        .fuzziness("AUTO")))
                .build();

        SearchResponse<LiquorES> response = null;
        try {
            response = openSearchClient.search(request, LiquorES.class);
            for (Hit<LiquorES> hit : response.hits().hits()) {
                resultList.add(hit.source());
            }
            return resultList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
