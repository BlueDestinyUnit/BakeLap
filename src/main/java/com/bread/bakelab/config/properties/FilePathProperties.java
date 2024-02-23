package com.bread.bakelab.config.properties;



import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
public class FilePathProperties {

    private String imageFilePath;
    private String savePath;

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}