package com.shrinitha.dochub.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

    public static String extractText(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();

        if (contentType == null || filename == null) {
            throw new IllegalArgumentException("File must have content type and name");
        }

        if (contentType.equals("application/pdf")) {
            return extractTextFromPDF(file);
        } else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            return extractTextFromDOCX(file);
        } else if (contentType.equals("text/plain") || filename.endsWith(".txt")) {
            return extractTextFromTXT(file);
        } else if (contentType.equals("text/csv") || filename.endsWith(".csv")) {
            return extractTextFromCSV(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }
    }

    private static String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private static String extractTextFromDOCX(MultipartFile file) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            return paragraphs.stream()
                    .map(XWPFParagraph::getText)
                    .collect(Collectors.joining("\n"));
        }
    }

    private static String extractTextFromTXT(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static String extractTextFromCSV(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
