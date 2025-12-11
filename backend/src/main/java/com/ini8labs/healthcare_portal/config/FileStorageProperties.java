package com.ini8labs.healthcare_portal.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.max-file-size-bytes:20971520}")
    private long maxFileSizeBytes;

    public String getUploadDir() {
        return uploadDir;
    }

    public long getMaxFileSizeBytes() {
        return maxFileSizeBytes;
    }
}
