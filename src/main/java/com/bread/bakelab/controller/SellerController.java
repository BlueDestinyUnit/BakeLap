package com.bread.bakelab.controller;

import com.bread.bakelab.domains.dto.ImagesDTO;
import com.bread.bakelab.domains.dto.ProductDTO;
import com.bread.bakelab.domains.dto.StockDTO;
import com.bread.bakelab.domains.vo.ImagesVO;
import com.bread.bakelab.domains.vo.ProductRegisVO;
import com.bread.bakelab.domains.vo.ProductVO;
import com.bread.bakelab.domains.vo.StockVO;
import com.bread.bakelab.service.ProductService;
import com.bread.bakelab.service.SellerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class SellerController {
    @Autowired
    SellerService sellerService;
    @Autowired
    ProductService productService;
    @Value("${IMAGE_FILE_PATH}")
    private String IMAGE_FILE_PATH;

    // 상품을 등록
    @GetMapping("/seller/regist")
    public void get_seller(){}
    @PostMapping("/seller/regist")
    public void post_seller(ProductRegisVO productRegisVO){
        sellerService.insert_product(productRegisVO);
        log.info(productRegisVO);
    }
    // 상품 정보 뷰
    @GetMapping("/seller/update")
    public String get_update(@RequestParam(name = "product_name") String product_name, Model model) {
        ProductDTO productDTO = productService.get_product(product_name);
        model.addAttribute("productDTO", productDTO);
        log.info(productDTO);
        return "seller/update";
    }
    // 전체 판매 수량
    @GetMapping("/seller/stock")
    public void get_stock(Model model) {
        List<StockVO> stockVOS = sellerService.find_stock();
        List<StockDTO> stockDTOS = new ArrayList<>();

        stockVOS.forEach(stockVO -> {
            ProductDTO productDTO = productService.get_product(stockVO.getProduct_name());
            log.info(productDTO);
            String image = productDTO.getImagesVO().get(0).getImage();

            StockDTO stockDTO = new StockDTO();
            stockDTO.setStockVO(stockVO);
            stockDTO.setImage(image);
            stockDTO.setPrice(productDTO.getProductVO().getPrice());

            stockDTOS.add(stockDTO); // 리스트에 StockDTO 객체 추가
        });

        log.info(stockDTOS);
        model.addAttribute("stockDTOS", stockDTOS); // 모델에 stockDTOS 추가
    }

    // 상품 정보 바꾸기
    @PostMapping("/seller/update")
    public String post_update(ProductRegisVO productRegisVO){
        sellerService.update_product(productRegisVO);
        return "redirect:/";
    }

}
