package com.shrinitha.dochub.model.elastic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Document(indexName = "documents")
public class DocumentIndex {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String filename;

    @Field(type = FieldType.Keyword)
    private String contentType;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String content;

    @Field(type = FieldType.Date)
    private Instant uploadedAt;

    @Field(type = FieldType.Keyword)
    private String s3Url;

    @Field(type = FieldType.Keyword)
    private String username;
}
