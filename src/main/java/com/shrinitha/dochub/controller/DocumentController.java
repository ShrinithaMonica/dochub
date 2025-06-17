package com.shrinitha.dochub.controller;

import com.shrinitha.dochub.dto.SearchRequest;
import com.shrinitha.dochub.dto.SearchResponse;
import com.shrinitha.dochub.dto.UploadResponse;
import com.shrinitha.dochub.model.User;
import com.shrinitha.dochub.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) {
        UploadResponse response = documentService.upload(file, user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest req, @AuthenticationPrincipal User user) {
        SearchResponse response = documentService.search(req.getKeyword(), user.getUsername());
        return ResponseEntity.ok(response);
    }
}
