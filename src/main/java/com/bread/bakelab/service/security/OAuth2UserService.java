package com.bread.bakelab.service.security;

import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.UserVO;
import com.bread.bakelab.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("로그인한 유저 객체:"+userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());
        String email=((Map<String,String>)oAuth2User.getAttribute("kakao_account")).get("email");
        System.out.println(email);

        UserVO vo= userMapper.get_user_email(email);
        //소셜 로그인한 유저가 디비에 없다
        if(vo==null){
            vo=new UserVO();
            vo.setId(email);
            vo.setPw("");
            vo.setState("securityuser");
            vo.setEmail(email);
            SecurityUser user= new SecurityUser(vo,  List.of(new SimpleGrantedAuthority("USER")));
            user.setAttributes(oAuth2User.getAttributes());
            return user;
        }else{
            SecurityUser user= new SecurityUser(vo,  List.of(new SimpleGrantedAuthority("USER")));
            user.setAttributes(oAuth2User.getAttributes());
            return user;
        }
    }
}
