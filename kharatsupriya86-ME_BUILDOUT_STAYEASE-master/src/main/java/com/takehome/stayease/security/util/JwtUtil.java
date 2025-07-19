package com.takehome.stayease.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

//    @Value("${jwt.secret}")
    private final String ACCESS_TOKEN = "f09234b30bc47a321bca9ec6bc9fa6946c7f2bc7985b046df92ab81a6acc78e2316d1888a0e075070bc329fc109fafdb3fc5a7229fc7dbc51cf296d8a953447e2de32a2ef44d9f05ac539ab090a8e00425f7d5d6db823ee08c2c7b249ab0705013bc5efcc923b46384e08eebffc9aeb49bc37dafe8e8fea8373d6023f82068f14568bc475c5cb52437d63c36363a7fe56952bc7f642a471c54b097605e1db627dcf4a0057c9fa491b0737b062bc11853f7254f8d56572b1815e53f19873c89523d4c635b206ce7ef0477d63af7e08a780dd48ac63014c30d6be7900b2d828628493c5a87e199baea9478940c04af5feae105dd9124603b0f9e50ec1890f42de7";
    private final SecretKey key = Keys.hmacShaKeyFor(ACCESS_TOKEN.getBytes());
    private final long expiration = 1000 * 60 * 60 * 24; // 1 hour

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

