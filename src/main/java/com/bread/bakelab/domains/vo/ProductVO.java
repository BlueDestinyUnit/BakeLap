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

    // 빌더 클래스 정의
    public static class Builder {
        private ProductVO productVO;

        public Builder() {
            this.productVO = new ProductVO();
        }

        public Builder withProductName(String product_name) {
            this.productVO.product_name = product_name;
            return this; // 자기 자신을 반환하여 메서드 체이닝 가능하도록 합니다.
        }

        public Builder withPrice(int price) {
            this.productVO.price = price;
            return this;
        }
        public Builder withContext(String context) {
            this.productVO.context = context;
            return this;
        }

        public Builder withNutrition(String nutrition) {
            this.productVO.nutrition = nutrition;
            return this;
        }

        public Builder withAllergy(String allergy) {
            this.productVO.allergy = allergy;
            return this;
        }
        public Builder withCategory(String category) {
            this.productVO.category = category;
            return this;
        }

        public Builder withStock(int stock) {
            this.productVO.stock = stock;
            return this;
        }

        public ProductVO build() {
            return this.productVO;
        }
    }

}
