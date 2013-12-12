package com.communityhelper.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.communityhelper.user.User;

public class RegisterUserDetails implements UserDetails {
    private static Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(){{
        add(new SimpleGrantedAuthority("read"));
        add(new SimpleGrantedAuthority("write"));
        add(new SimpleGrantedAuthority("ROLE_USER"));
    }};
    
    private User user;
    
    public RegisterUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }
    @Override
    public String getUsername() {
        return user.getPhonenum();
    }
    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }
    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
}
