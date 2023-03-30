package com.close.close.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
/**
 * This class provides methods for generating and validating JSON Web Tokens (JWTs) using the io.jsonwebtoken library.
 */
@Service
public class JwtService {
    /**
     * The secret key used for signing and verifying JWTs.
     */
    //TODO: Should be change by a key created by a library
    private static final String SECRET_KEY = "73367639792442264529482B4D6251655468576D5A7134743777217A25432A46\n";
    /**
     * The expiration time for JWTs, in milliseconds.
     */
    private final int TOKEN_EXPIRATION =  1000*60*24;
    /**
     * Extracts a claim from a JWT using a function that takes a Claims object and returns a type T.
     *
     * @param token The JWT.
     * @param claimsResolver The function that extracts the claim.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * Extracts the username from a JWT.
     *
     * @param token The JWT.
     * @return The username.
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Returns the signing key used for signing and verifying JWTs.
     *
     * @return The signing key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT for a given user.
     *
     * @param userDetails The user.
     * @return The generated JWT.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    /**
     * Validates whether a JWT is valid for a given user.
     *
     * @param token The JWT.
     * @param userDetails The user.
     * @return true if the JWT is valid for the user; false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    /**
     * Checks whether a JWT has expired.
     *
     * @param token The JWT.
     * @return true if the JWT has expired; false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
     * Extracts the expiration time from a JWT.
     *
     * @param token The JWT.
     * @return The expiration time.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a token given extra claims (such as authorities, etc.) and a User
     * @param extraClaims HashMap with the extra claims. Apart from subject, expiration, etc.
     * @param userDetails UserDetails object from the user to generate the token by
     * @return String with the token generated
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Decodes a token and returns all the claims from it
     * @param token String with the token to analyze
     * @return Claims object with the claims extracted
     */
    private Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



}
