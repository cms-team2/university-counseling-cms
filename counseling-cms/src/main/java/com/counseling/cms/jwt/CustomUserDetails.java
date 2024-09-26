package com.counseling.cms.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.counseling.cms.entity.UserInfoEntity;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    String userId, userPassword, authority;

    public CustomUserDetails(UserInfoEntity userInfoEntity) {
        this.userId = userInfoEntity.getUserId();
        this.userPassword = userInfoEntity.getUserPassword();
        this.authority = userInfoEntity.getUserAuthority();
    }

    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    public String getUserAuthority() {
        return this.authority;
    }

    @Override
    public String getUsername() {
        return this.userId; // 사용자 ID 반환
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 안됨
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 안됨
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화
    }
}
