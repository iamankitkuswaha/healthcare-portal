package com.ini8labs.healthcare_portal.service;

import com.ini8labs.healthcare_portal.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Document store(MultipartFile file);
    List<Document> listAll();

    Optional<Document> find(Long id);
    boolean delete(Long id);
}
