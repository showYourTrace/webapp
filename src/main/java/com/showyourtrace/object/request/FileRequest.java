package com.showyourtrace.object.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileRequest {
    private String name;
    private byte[] bytes;

    public FileRequest(MultipartFile multipartFile) throws IOException {
        this.name  = multipartFile.getOriginalFilename();
        this.bytes = multipartFile.getBytes();
    }

    public FileRequest(FileRequest origin){
        this.name = origin.getName();
        this.bytes = origin.getBytes();
    }

    public FileRequest(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public FileRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
