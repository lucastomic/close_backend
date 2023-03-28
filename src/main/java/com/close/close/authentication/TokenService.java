package com.close.close.authentication;

import com.close.close.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TokenService manages the Authentication Tokens.
 * Authentication tokens are used to verify if a user credentials are valid or not.
 */
//TODO: COMPLETE: https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
@Component
public class TokenService {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;


    /**
     * generateToken creates an authentication token from a User object.
     * It sets the token's subject as the user's name.
     * It signs the token with the HS512 algorithm.
     * @param user User to create the token from
     * @return String object with the token
     */
    //TODO: Implement authorities
    public String generateToken(User user) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        return Jwts.builder()
                .claim("authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}
