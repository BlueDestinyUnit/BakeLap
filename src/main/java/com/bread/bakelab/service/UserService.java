package com.bread.bakelab.service;

import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.UserVO;
import com.bread.bakelab.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {
    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    // 유저 회원가입
    public void join_user(UserVO userVO){
        // 유저의 비밀번호 인코딩
        userVO.setPw(passwordEncoder.encode(userVO.getPw()));
        // 유저 등록
        userMapper.join_user(userVO);
    }

    //소셜 회원가입 시
    public void social_join_user(UserVO userVO, Authentication authentication){
        SecurityUser user=(SecurityUser)authentication.getPrincipal();
        userVO.setPw(passwordEncoder.encode(userVO.getPw()));
        user.getUserVO().setState("KAKAO");
        userMapper.social_join_user(userVO);
        user.setUserVO(userVO);
    }


    // 비번 변경
    public void update_pw(SecurityUser user, UserVO userVO){

        // 비번 수정 시, 인코딩 후 세팅
        String encodePw = passwordEncoder.encode(userVO.getPw());
        userVO.setPw(encodePw);
        // 마이페이지로 들어온 인증된 유저일 경우
        if (user != null){
            userVO.setId(user.getUserVO().getId());
        }

        log.info(userVO);

        //update 실행
        userMapper.update_pw(userVO);

        // 로그인 된 userVO 객체를 수정한 UserVO 객체로 수정
//        user.setUserVO(userVO);
    }


    /* **************** 마이페이지 회원 수정 ******************** */
    public void update_user(SecurityUser user, UserVO userVO){
        // 기존 유저정보와 사용자가 입력한 유저 정보를 매칭
        UserVO updateUserVO = set_update_user_info(user.getUserVO(), userVO);
        log.info(updateUserVO);
        //update 실행
        userMapper.update_user(updateUserVO);
        // 로그인 된 userVO 객체를 수정한 UserVO 객체로 수정
        user.setUserVO(updateUserVO);
    }

    // 자신의 정보 수정 시 정보 체크
    private UserVO set_update_user_info(UserVO originalUserVO, UserVO userVO){
        // 추가 setting 필요시
        userVO.setId(originalUserVO.getId());
        userVO.setName(originalUserVO.getName());

        // 수정 하지 않은 정보와 수정한 정보 판단
        String address = userVO.getAddress();
        String email = userVO.getEmail();
        String tel = userVO.getTel();


        ///////////////// 기존 정보 동일 하게 유지
        // 주소 수정 x
        if (address.equals(",,")){
            userVO.setAddress(originalUserVO.getAddress());
        }
        // 이메일 수정 x
        if (email.equals("@")){
            userVO.setEmail(originalUserVO.getEmail());
        }
        // 전화번호 수정x
        if (tel.isBlank()){
            userVO.setTel(originalUserVO.getTel());
        }

        return userVO;
    }
}