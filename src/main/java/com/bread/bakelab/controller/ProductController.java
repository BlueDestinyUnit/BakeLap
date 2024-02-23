package com.bread.bakelab.controller;

import com.bread.bakelab.domains.dto.ImagesDTO;
import com.bread.bakelab.domains.dto.ProductDTO;
import com.bread.bakelab.domains.vo.ImagesVO;
import com.bread.bakelab.domains.vo.ProductVO;
import com.bread.bakelab.service.ProductService;
import com.bread.bakelab.service.SellerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;


@Log4j2
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    SellerService sellerService;

    // 상품 상세 정보 보여주기
    @GetMapping("/details")
    public void get_detatils(@RequestParam(name = "product_name")String product_name, Model model){
        ProductDTO productDTO = productService.get_product(product_name);
        model.addAttribute("productDTO", productDTO);
    }

    // 이미지 파일 매핑 경로
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> get_room_image_file(
            @PathVariable("imageName") String imageName
    ) throws Exception {
        return productService.get_product_image_file(imageName);
    }

    // 모든 상품 진열
    @GetMapping
    @ResponseBody
    public List<ProductDTO> get_all_products (
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String category
    ){
        return productService.get_all_products(search,category);
    }

}
