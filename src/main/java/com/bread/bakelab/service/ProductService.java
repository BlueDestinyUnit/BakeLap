package com.bread.bakelab.service;


import com.bread.bakelab.config.properties.FilePathProperties;
import com.bread.bakelab.domains.dto.ProductDTO;
import com.bread.bakelab.domains.vo.ProductVO;
import com.bread.bakelab.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.List;

@Service
@Log4j2
public class ProductService {

    private final FilePathProperties filePathProperties;
    private String IMAGE_FILE_PATH;

    @Autowired
    public ProductService(FilePathProperties filePathProperties) {
        this.filePathProperties = filePathProperties;
        this.IMAGE_FILE_PATH = filePathProperties.getImageFilePath();
    }

    @Autowired
    ProductMapper productMapper;

    // 모든 상품을 가져오기
    public List<ProductDTO> get_all_products(String search,String category){
        return productMapper.get_all_products(search,category);
    }

     //특정 상품 가져오기
    public ProductDTO get_product(String product_name){return productMapper.get_product(product_name);}

    // 특정 상품 바꾸기
    public void update_product(ProductVO productVO){
       productMapper.update_product(productVO);
    }


    // 특정 상품 이미지 데이터 삭제
    public void delete_images(String product_name){
        productMapper.delete_images(product_name);
    }

    // 상품 재고 업데이트
    public void update_product_stock(int count, String product_name){
        productMapper.update_product_stock(count,product_name);
    }

    // 각 상품 이미지를 다운로드 해주는 서비스
    public ResponseEntity<Resource> get_product_image_file(String fileName) throws Exception{
        Resource resource = new FileSystemResource(IMAGE_FILE_PATH + fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

}
