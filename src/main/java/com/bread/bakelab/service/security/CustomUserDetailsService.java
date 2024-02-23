package com.bread.bakelab.service.security;

import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.UserVO;
import com.bread.bakelab.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" ======= loadUserByUsername: ["+ username + "]");
        // 로그인 시도 한 유저 이름(id)가 DB에 있는지 확인해서 유저를 가져온다
        UserVO userVO = userMapper.get_user(username);
        // DB에 해당 이름의 유저가 없었다!
        if(userVO == null){
            throw new UsernameNotFoundException("ERROR: USER NOT FOUND!");
        }
        // 유저가 있었다면 그 유저의 내용으로 User 객체를 생성한 뒤 반환
        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + userVO.getRole());
        return new SecurityUser(userVO, List.of(authorities));
    }
}

