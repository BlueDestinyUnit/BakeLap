package com.bread.bakelab.mapper;

import com.bread.bakelab.domains.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    // 제품에 알맞는 모든 댓글 가져오기
    List<ReviewVO> get_all_reviews(String product_name);

    // 댓글 추가
    void post_review_write(ReviewVO reviewVO);

    // 댓글 수정
    void review_update(ReviewVO reviewVO);

    // 댓글 삭제
    void review_delete(int no);
}
