package com.shrinitha.dochub.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.shrinitha.dochub.model.elastic.DocumentIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticsearchDocumentService {

    private static final String INDEX_NAME = "documents";

    @Autowired
    private ElasticsearchClient esClient;

    public void indexDocument(Long documentId, String content, String username) {
        DocumentIndex doc = new DocumentIndex();
        doc.setId(documentId.toString());
        doc.setContent(content);
        doc.setUsername(username);

        try {
            esClient.index(IndexRequest.of(i -> i.index(INDEX_NAME).id(doc.getId()).document(doc)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to index document", e);
        }
    }

    public List<DocumentIndex> search(String keyword, String username) {
        try {
            SearchResponse<DocumentIndex> response = esClient.search(s -> s.index(INDEX_NAME).query(q -> q.bool(b -> b.must(m -> m.multiMatch(mm -> mm.fields("filename", "content").query(keyword).fuzziness("AUTO"))).filter(f -> f.term(t -> t.field("username").value(username))))), DocumentIndex.class);

            return response.hits().hits().stream().map(hit -> hit.source()).toList();

        } catch (IOException e) {
            throw new RuntimeException("Search failed", e);
        }
    }

}
