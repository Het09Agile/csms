package com.csms.controller;

import com.csms.config.UserDetailsImpl;
import com.csms.dto.CreateUserDto;
import com.csms.dto.LoginDto;
import com.csms.dto.UpdateUserDto;
import com.csms.model.Users;
import com.csms.repository.UserRepository;
import com.csms.service.UserService;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.jwt.JwtUtils;
import com.csms.utils.messages.SuccessMessages;
import com.csms.utils.respnse.SuccessResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.csms.utils.messages.ErrorMessages.USER_NOT_FOUND;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/auth/signup")
    ResponseEntity userSignup(@Validated @RequestBody CreateUserDto body){
        Users user = new Users();
        user.setRole(body.getRole());
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setBirthDate(body.getBirthDate());
        userRepository.save(user);
        return SuccessResponse.success(SuccessMessages.USER_SIGNUP,user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) throws NotFoundException, BadCredentialsException {
        Users user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()->new NotFoundException(USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getEmail(),loginDto.getPassword());
        Authentication authentication  = authenticationManager.authenticate(authToken);

        if(authentication.isAuthenticated()){
            String token = jwtUtils.generate(user.getEmail(), user.getId(), user.getRole().toString());
            return SuccessResponse.dataOnly(token);
        }else{
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @GetMapping("/api/myProfile")
    public ResponseEntity myProfile(@AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);
        return SuccessResponse.dataOnly(user);
    }

    @PutMapping("/api/updateProfile")
    public ResponseEntity updateProfile(@RequestBody UpdateUserDto userDto, @AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthDate(userDto.getBirthDate());
        userRepository.save(user);
        return SuccessResponse.dataOnly(user);
    }




}
