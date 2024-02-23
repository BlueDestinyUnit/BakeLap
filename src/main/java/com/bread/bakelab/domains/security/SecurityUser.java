package com.bread.bakelab.domains.security;

import com.bread.bakelab.domains.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class SecurityUser extends User implements OAuth2User {
    private UserVO userVO;
    private Map<String, Object> attributes;

    public SecurityUser(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
        super(userVO.getId(), userVO.getPw(), authorities);
        this.userVO = userVO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.userVO.getId();
    }
}
