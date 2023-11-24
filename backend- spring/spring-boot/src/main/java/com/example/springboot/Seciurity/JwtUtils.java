package com.example.springboot.Seciurity;

import com.example.springboot.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private long jwtExpirationMs = 86400000; // 24 hours

    public String generateJwtToken(User userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token);
        return jws.getBody().getSubject();
    }
}
