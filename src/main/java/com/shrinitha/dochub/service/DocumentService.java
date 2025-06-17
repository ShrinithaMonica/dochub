package com.shrinitha.dochub.service;

import com.shrinitha.dochub.dto.SearchResponse;
import com.shrinitha.dochub.dto.UploadResponse;
import com.shrinitha.dochub.model.Document;
import com.shrinitha.dochub.model.User;
import com.shrinitha.dochub.model.elastic.DocumentIndex;
import com.shrinitha.dochub.repository.DocumentRepository;
import com.shrinitha.dochub.util.FileUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ElasticsearchDocumentService elasticsearchService;

    @Autowired
    private UserService userService;

    @Transactional
    public UploadResponse upload(MultipartFile file, User user) {
        try {
            // 1. Generate unique file key
            String fileKey = "docs/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

            // 2. Upload file to S3
            String s3Url = s3Service.uploadFile(fileKey, file.getInputStream(), file.getContentType());

            // 3. Extract file content
            String content = FileUtil.extractText(file);

            // 4. Save metadata to PostgreSQL
            Document doc = new Document();
            doc.setFilename(file.getOriginalFilename());
            doc.setContentType(file.getContentType());
            doc.setS3Url(s3Url);
            doc.setUploadedBy(user);
            documentRepository.save(doc);

            // 5. Index content asynchronously
            indexDocumentAsync(doc.getId(), content, user.getUsername());

            return new UploadResponse("File uploaded successfully", doc.getId(), s3Url);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Async
    public void indexDocumentAsync(Long documentId, String content, String username) {
        elasticsearchService.indexDocument(documentId, content, username);
    }

    public SearchResponse search(String keyword, String username) {
        List<DocumentIndex> indexResults = elasticsearchService.search(keyword, username);
        List<String> filePaths = indexResults.stream()
                .map(DocumentIndex::getS3Url)
                .toList();

        return new SearchResponse(filePaths);
    }

}
