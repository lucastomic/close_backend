package com.close.close.authentication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import io.jsonwebtoken.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**

 This class represents a filter that intercepts requests and checks for a valid JWT token in the Authorization header.
 If a valid token is found, it sets the Spring authentication with the token claims.
 If a token is not found or is invalid, it clears the Spring authentication context.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey"; //TODO:Change for a good key

    /**
     This method parses the JWT token from the Authorization header and validates it using the configured secret key.
     @param request The HTTP request object containing the Authorization header.
     @return The parsed and validated JWT token claims.
     */
    private Claims validateToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        token = this.removePrefix(token);
        return this.decodeClaims(token);
    }

    /**
     * removePrefix takes a token and removes its prefix
     * @param token token to parse
     * @return String with the token without the prefix
     */
    private String removePrefix(String token){
        return token.replace(PREFIX, "");
    }

    /**
     * decodeClaims decodes a token and return his claims body
     * @param token token to decode
     * @return token's claims
     */
    private Claims decodeClaims(String token){
        JwtParser parser = Jwts.parser();
        parser.setSigningKey(SECRET.getBytes());
        return parser.parseClaimsJws(token).getBody();
    }
    /**
     This method sets the Spring authentication context with the user claims extracted from the JWT token.
     @param claims The user claims extracted from the JWT token.
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                this.parseAuthoritiesFromStrings(authorities)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * parseAuthoritiesFromStrings takes a string's list and parse every string into
     * a SimpleGrantedAuthority object
     * @param authorities list of strings to parse
     * @return list with strings parsed into SimpleGrantedAuthority objects
     */
    private List<SimpleGrantedAuthority> parseAuthoritiesFromStrings(List<String> authorities){
       return authorities
               .stream()
               .map(SimpleGrantedAuthority::new)
               .collect(Collectors.toList());
    }

    /**
     This method checks if a JWT token exists in the Authorization header of the HTTP request.
     @param request The HTTP request object to check for the Authorization header.
     @return true if a valid Authorization header with a Bearer token is found, false otherwise.
     */
    private boolean tokenExist(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    /**
     This method is called by the servlet container for each HTTP request that passes through this filter.
     It checks for a valid JWT token and sets the Spring authentication context with the token claims if found.
     If a token is not found or is invalid, it clears the Spring authentication context.
     @param request The HTTP request object.
     @param response The HTTP response object.
     @param chain The filter chain for the next filters to be executed.
     @throws ServletException if there is an error during the filter processing.
     @throws IOException if there is an I/O error during the filter processing.
     */
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, IOException {
        try {
            if (tokenExist(request)) {
                this.setAuthClaimsFromRequest(request);
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * setAuthClaimsFromRequest takes the authority claims of the token's request and set them
     * in the Spring authentication context. If there isn't any auth claim it clears the Spring auth context
     * @param request
     */
    private void setAuthClaimsFromRequest(HttpServletRequest request){
        Claims claims = validateToken(request);
        if (claims.get("authorities") != null) {
            setUpSpringAuthentication(claims);
        } else {
            SecurityContextHolder.clearContext();
        }
    }
}

