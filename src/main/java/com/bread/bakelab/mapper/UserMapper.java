package com.bread.bakelab.mapper;


import com.bread.bakelab.domains.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserVO get_user(String id);
    //일반 회원가입
    void join_user(UserVO vo);
    //소셜 회원가입
    void social_join_user(UserVO vo);
    // 아이디 중복 체크
    int check_user_id(String id);
    // 아이디 찾기
    UserVO get_user_find_id(@Param("name") String name,
                            @Param("email") String email);

    UserVO get_user_email(String email);

    // 비번 변경
    void update_pw(UserVO userVO);
    //회원 탈퇴
    void delete_account(String id);
    /* ********** 마이 페이지 ************ */
    void update_user(UserVO userVO);

}