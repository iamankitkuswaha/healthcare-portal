package com.ini8labs.healthcare_portal.controller;

import com.ini8labs.healthcare_portal.entity.Document;
import com.ini8labs.healthcare_portal.exception.FileStorageException;
import com.ini8labs.healthcare_portal.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "${frontend.origin}")
public class DocumentController {
    private final DocumentService service;
    public DocumentController(DocumentService service){
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        Document saved = service.store(file);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Document> listAll(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> download(@PathVariable Long id){
        Optional<Document> doc = service.find(id);
        if(!doc.isPresent())
            throw new FileStorageException(HttpStatus.NOT_FOUND,"Document not found");

        try{
            Path path = Path.of(doc.get().getFilepath());
            Resource res = new UrlResource(path.toUri());
            if(!res.exists() || !res.isReadable())
                throw new FileStorageException(HttpStatus.NOT_FOUND,"File not found on disk");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+doc.get().getFilename()+"\"")
                    .body(res);
        }catch(MalformedURLException e){
            throw new FileStorageException(HttpStatus.INTERNAL_SERVER_ERROR,"Error in reading file");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean ok = service.delete(id);
        if(!ok)
            throw new FileStorageException(HttpStatus.NOT_FOUND,id+" Not found");
        return ResponseEntity.ok().body("{\"message\":\"deleted\"}");
    }
}
