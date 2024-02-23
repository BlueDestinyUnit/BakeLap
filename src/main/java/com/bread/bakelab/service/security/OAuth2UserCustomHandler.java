package com.bread.bakelab.service.security;

import com.bread.bakelab.domains.security.SecurityUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class OAuth2UserCustomHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityUser user=(SecurityUser)authentication.getPrincipal();//현재 세션 사용자의 객체를 가져옴
        if(user.getUserVO().getState().equals("securityuser")){
            response.sendRedirect("/user/socialjoin");
        }else {
            response.sendRedirect("/");
        }
    }
}
