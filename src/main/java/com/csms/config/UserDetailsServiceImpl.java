package com.csms.config;

import com.csms.model.Users;
import com.csms.repository.UserRepository;
import com.csms.utils.exception.customExceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        Users user = userRepository.findById(Long.parseLong(username)).orElseThrow(() -> new
                NotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
        return new User(user.getEmail(), user.getPassword(), authorities);


    }


}


