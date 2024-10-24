package com.prgama.foodcourt_microservice.infrastructure.security.adapter;

import com.prgama.foodcourt_microservice.domain.spi.ITokenProviderPort;
import com.prgama.foodcourt_microservice.infrastructure.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TokenAdapter implements ITokenProviderPort {
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(SecurityConstants.CLAIMS_ROLE, String.class);
    }

    @Override
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
