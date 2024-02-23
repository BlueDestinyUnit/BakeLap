package com.bread.bakelab.domains.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRegisVO {
    private String product_name;
    private int price;
    private String context;
    private String nutrition;
    private String allergy;
    private String category;
    private int stock;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regis_date;
    private List<MultipartFile> images;
}
