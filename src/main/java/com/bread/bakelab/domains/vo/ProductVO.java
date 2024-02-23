package com.bread.bakelab.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class ProductVO {
    private String product_name;
    private int price;
    private String context;
    private String nutrition;
    private String allergy;
    private String category;
    private int stock;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regis_date;
}
