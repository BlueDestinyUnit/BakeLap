package com.bread.bakelab.domains.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ImagesDTO {
    private List<MultipartFile> images;
}
