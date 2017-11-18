package com.sefryek.doublepizza.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Bhareh Koohestani on 6/2/14.
 */
public class FileUpload {

    private long size;
    private String filePath;
    private MultipartFile file;

    //getter and setter methods


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
