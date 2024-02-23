package com.bread.bakelab.mapper;

import com.bread.bakelab.domains.dto.ProductDTO;
import com.bread.bakelab.domains.vo.PaymentVO;
import com.bread.bakelab.domains.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 상품 검색 리스트
    List<ProductDTO> get_all_products(@Param("search") String search, @Param("category")String category);

    // 상품 검색
    ProductDTO get_product(String product_name);

    // 이미지 삭제
    void delete_images(String product_name);
    // 상품 업데이트
    void update_product(ProductVO productVO);
    // 상품 재고 업데이트
    void update_product_stock(@Param("count") int count, @Param("product_name")String product_name);



}
