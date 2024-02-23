package com.bread.bakelab.controller;

import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.ReviewVO;
import com.bread.bakelab.mapper.ReviewMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired ReviewMapper reviewMapper;

    // 리뷰 조회
    @GetMapping
    public List<ReviewVO> get_all_reviews(
            @RequestParam String product_name
    ){
        log.info(reviewMapper.get_all_reviews(product_name));
        return reviewMapper.get_all_reviews(product_name);
    }

    // 리뷰 작성
    @PostMapping
    public void post_review_write(
            @AuthenticationPrincipal SecurityUser user,
            ReviewVO reviewVO
    ){
        reviewVO.setUserID(user.getName());
        log.info(reviewVO);
        reviewMapper.post_review_write(reviewVO);
    }

    // 리뷰 수정
    @PostMapping("update")
    public void get_review_update(
           ReviewVO reviewVO
    ){
        reviewMapper.review_update(reviewVO);
    }

    // 리뷰 삭제
    @GetMapping("delete")
    public void get_review_delete(
            @RequestParam int no
    ){
        reviewMapper.review_delete(no);
    }


}
