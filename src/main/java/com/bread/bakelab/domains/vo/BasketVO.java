package com.bread.bakelab.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class BasketVO {
    int no;
    String userID;
    String product_name;
    int count;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate basket_regis_date;

    public String getProduct_name() {
        return product_name;
    }

    public LocalDate getBasket_regis_date() {
        return basket_regis_date;
    }
}
