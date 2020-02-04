package ftn.xscience.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ftn.xscience.model.user.TUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

    @Value("${secret-key}")
    private String secret;

    public TUser validate(String token) {
        TUser user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            user = new TUser();
            user.setUsername(body.getSubject());
            user.setPassword(body.getId());
            user.setRole((String)body.get("role"));
        }
        catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Token is invalid or missing!");
        }

        return user;
    }
}
