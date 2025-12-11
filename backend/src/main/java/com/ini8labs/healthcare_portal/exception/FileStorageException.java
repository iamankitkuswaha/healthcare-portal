package com.ini8labs.healthcare_portal.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException{
    private final HttpStatus status;
    public FileStorageException(HttpStatus status, String msg){
        super(msg);
        this.status = status;
    }
    public HttpStatus getStatus(){
        return status;
    }
}
