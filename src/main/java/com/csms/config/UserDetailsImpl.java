package com.csms.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String email;
    private String password;
    private String id;
    private List<? extends GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return id;
    }

    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}

