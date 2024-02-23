package com.bread.bakelab.domains.dto;

import com.bread.bakelab.domains.vo.ImagesVO;
import com.bread.bakelab.domains.vo.ProductVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ProductDTO {
    private String product_name;
    private ProductVO productVO;
    private List<ImagesVO> imagesVO;
}
