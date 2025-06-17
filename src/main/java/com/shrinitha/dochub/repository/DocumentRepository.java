package com.shrinitha.dochub.repository;

import com.shrinitha.dochub.model.Document;
import com.shrinitha.dochub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUploadedBy(User user);
}

