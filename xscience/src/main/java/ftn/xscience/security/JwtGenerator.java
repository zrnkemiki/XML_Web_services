package ftn.xscience.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ftn.xscience.model.TUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

    @Value("${secret-key}")
    private String secret;

    public String generate(TUser user) {


        Claims claims = Jwts.claims()
                .setSubject(user.getEmail())
                .setId(user.getPassword());
        claims.put("role", user.getRoles().get(0));


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
