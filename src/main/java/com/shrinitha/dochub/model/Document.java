package com.shrinitha.dochub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String contentType;
    private String s3Url;
    private String esId;
    @ManyToOne
    private User uploadedBy;
    private Instant uploadTime;
}
