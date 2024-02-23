package com.bread.bakelab.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class StockVO {
    String product_name;
    int stock;
}
