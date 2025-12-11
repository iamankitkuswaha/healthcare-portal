package com.ini8labs.healthcare_portal.service;

import com.ini8labs.healthcare_portal.config.FileStorageProperties;
import com.ini8labs.healthcare_portal.entity.Document;
import com.ini8labs.healthcare_portal.exception.FileStorageException;
import com.ini8labs.healthcare_portal.repository.DocumentRepository;
import com.ini8labs.healthcare_portal.util.FileTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImp implements DocumentService{

    private final Path uploadDir;
    private final long maxSize;
    private final DocumentRepository repo;
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImp.class);
    public DocumentServiceImp(FileStorageProperties props, DocumentRepository repo){
        this.uploadDir = Paths.get(props.getUploadDir()).toAbsolutePath().normalize();
        this.maxSize = props.getMaxFileSizeBytes();
        this.repo = repo;
        try {
            Files.createDirectories(this.uploadDir); // ensures folder exists or throws with reason
            logger.info("Upload directory: {}", this.uploadDir.toString());
        } catch (IOException e) {
            logger.error("Unable to create upload dir {} : {}", this.uploadDir, e.getMessage(), e);
            throw new IllegalStateException("Cannot create upload directory: " + this.uploadDir, e);
        }
    }
    @Override
    public Document store(MultipartFile file) {
        try{
            if(file.isEmpty())
                throw new FileStorageException(HttpStatus.BAD_REQUEST,"Empty file");
            if(file.getSize()>maxSize)
                throw new FileStorageException(HttpStatus.PAYLOAD_TOO_LARGE,"File exceeds max allowed size");

            String original = Paths.get(file.getOriginalFilename()).getFileName().toString();
            if(original == null || original.isBlank())
                    throw new FileStorageException(HttpStatus.BAD_REQUEST,"Missing filename");

            if(!original.toLowerCase().endsWith(".pdf"))
                    throw new FileStorageException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"Only PDF allowed.");

            try(BufferedInputStream in = new BufferedInputStream(file.getInputStream())){
                if(!FileTypeDetector.isPdf(in)){
                    throw new FileStorageException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"File content not pdf");
                }
            }

            String storedName = UUID.randomUUID().toString().replace("-", "").substring(0, 8)+"_"+original.replaceAll("\\s+","_");
            storedName = storedName.replaceAll("_+", "_");
            Path target = uploadDir.resolve(storedName);
            try(InputStream in = file.getInputStream()){
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            Document doc = new Document(original, target.toString(), file.getSize());
            doc.setCreated_at(Instant.now());
            return repo.save(doc);
        }catch(IOException ex) {
            throw new FileStorageException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file");
        }
    }

    @Override
    public List<Document> listAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Document> find(Long id) {
        return repo.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Document> doc = repo.findById(id);
        logger.info(doc.get().getFilename()+" "+doc.get().getFilepath());
        if (doc.isEmpty())
            return false;
        try {
            Files.deleteIfExists(Paths.get(doc.get().getFilepath()));
            repo.deleteById(id);
            logger.info("File "+id+" deleted successfully");
        } catch (IOException e) {
            logger.error("Error in file deletion");
            return false;
        }
        return true;
    }
}
