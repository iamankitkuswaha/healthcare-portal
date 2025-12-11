package com.ini8labs.healthcare_portal.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.Instant;

@JsonPropertyOrder({ "id", "filename", "filepath", "filesize", "created_at" })
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="filename", nullable = false)
    private String filename;
    @Column(name="filepath", nullable = false)
    private String filepath;
    private Long filesize;
    private Instant created_at;

    public Document(){}
    public Document(String filename, String filepath, Long filesize) {
        this.filename = filename; this.filepath = filepath; this.filesize = filesize; this.created_at = Instant.now();
    }

    public Document(String filename, String filepath, Long filesize, Instant createdAt) {
        this.filename = filename; this.filepath = filepath; this.filesize = filesize; this.created_at = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public Long getFilesize() {
        return filesize;
    }

    public Instant getCreated_at() {
        return created_at;
    }
}
