package com.csms.service;

import com.csms.config.UserDetailsImpl;
import com.csms.model.Users;
import com.csms.repository.UserRepository;
import com.csms.utils.enums.Role;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.messages.ErrorMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Users getUserDetails(UserDetailsImpl principal){
        return userRepository.findByEmail(principal.getEmail()).orElseThrow(()->new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    public Users getUserByRoleAndId(long id, Role role){
        return userRepository.findByIdAndRole(id, role).orElseThrow(()-> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

}
