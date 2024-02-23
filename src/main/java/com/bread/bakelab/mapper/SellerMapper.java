package com.bread.bakelab.mapper;

import com.bread.bakelab.domains.vo.ImagesVO;
import com.bread.bakelab.domains.vo.ProductRegisVO;
import com.bread.bakelab.domains.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerMapper {
    // 상품 등록
    void post_product(ProductRegisVO productRegisVO);
    // 이미지 추가
    void post_images(List<ImagesVO> imagesVOS);
    // 재고 추가
    void post_stock(String product_name);
    // 판매수 변경
    void update_sell_stock(@Param("count") int count, @Param("product_name")String product_name);

    // 판매수
    List<StockVO> find_stock();
}