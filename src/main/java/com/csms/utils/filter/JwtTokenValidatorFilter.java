package com.csms.utils.filter;

import com.csms.config.UserDetailsImpl;
import com.csms.model.Users;
import com.csms.repository.UserRepository;
import com.csms.utils.exception.customExceptions.NotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    public Environment environment;
    public UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException,MalformedJwtException,ExpiredJwtException,IllegalArgumentException {
        String jwt = request.getHeader("Authorization");
        if(null != jwt) {
            try {
                jwt = jwt.startsWith("Bearer") ? jwt.substring("Bearer".length()).trim() : jwt;

                final SecretKey secretKey = Keys.hmacShaKeyFor(environment.getProperty("JWT_SECRET_KEY").getBytes());

                if(null != secretKey) {
                    Claims claims = Jwts.parser().verifyWith(secretKey)
                            .build().parseSignedClaims(jwt).getPayload();

                    Long id = Long.valueOf(claims.get("id").toString());

                    String authorities = String.valueOf(claims.get("role"));
                    Users user = userRepo.findById(id).orElseThrow(()->new NotFoundException("User not Found"));
                    UserDetailsImpl userDetails = new UserDetailsImpl(user.getEmail(),user.getPassword(),user.getId().toString(), AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (MalformedJwtException e) {
//                SuccessResponse.errorResponse("Invalid JWT signature or malformed token.",HttpStatus.BAD_REQUEST);
            } catch (ExpiredJwtException e) {
//                SuccessResponse.errorResponse("JWT token is expired.",HttpStatus.BAD_REQUEST);
            } catch (UnsupportedJwtException e) {
//                SuccessResponse.errorResponse("Unsupported JWT token.",HttpStatus.BAD_REQUEST);
            } catch (IllegalArgumentException e) {
//                SuccessResponse.errorResponse("JWT claims string is empty.",HttpStatus.BAD_REQUEST);
            }
            catch (Exception exception) {
//                SuccessResponse.errorResponse("Invalid Token received!",HttpStatus.BAD_REQUEST);
            }
        }else {
//            SuccessResponse.errorResponse("Please provide access token to access protected APIs.",HttpStatus.BAD_REQUEST);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().contains("/api");
    }
}

