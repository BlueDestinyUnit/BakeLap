package com.bread.bakelab.domains.dto;

import com.bread.bakelab.domains.vo.ImagesVO;
import com.bread.bakelab.domains.vo.StockVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class StockDTO {
    StockVO stockVO;
    String image;
    int price;
}
