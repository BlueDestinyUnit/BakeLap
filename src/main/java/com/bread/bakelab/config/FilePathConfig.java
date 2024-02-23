package com.bread.bakelab.config;

import com.bread.bakelab.config.properties.FilePathProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({
        "classpath:properties/file-path.properties"
})
@Log4j2
public class FilePathConfig {

    @Value("${SAVE_PATH}")
    private String SAVE_PATH;
    @Value("${IMAGE_FILE_PATH}")
    private String IMAGE_FILE_PATH;
    @Value("${LOCAL_IMAGE_FILE_PATH}")
    private String LOCAL_IMAGE_FILE_PATH;
    @Value("${LOCAL_SAVE_PATH}")
    private String LOCAL_SAVE_PATH;

    @Bean
    public FilePathProperties filePathProperties() {
        FilePathProperties properties = new FilePathProperties();
        if ("local".equals(activeProfile())) {
            properties.setImageFilePath(LOCAL_IMAGE_FILE_PATH);
            properties.setSavePath(LOCAL_SAVE_PATH);
            log.info(properties);
        } else {
            properties.setImageFilePath(SAVE_PATH);
            properties.setSavePath(IMAGE_FILE_PATH);
        }
        return properties;
    }

    @Autowired
    private Environment environment;

    private String activeProfile() {
        return environment.getProperty("spring.profiles.active");
    }
}
