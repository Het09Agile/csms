package com.csms.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUtils {
    private final SecretKey secretKey;

    public String generate(String username,Long id, String role) {
        System.out.println(secretKey);
        return Jwts.builder()
                .issuer("Cricket Score Management System")
                .subject("Access Token")
                .claim("id",id)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) throws JwtException {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) throws JwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) throws JwtException {
        return extractExpirationDate(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) throws JwtException {
        final Claims claims = extractClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}

