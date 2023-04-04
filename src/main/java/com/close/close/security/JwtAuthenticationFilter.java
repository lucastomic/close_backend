package com.close.close.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter executed once per request within a single request thread.
 * It checks whether the token JWT exists and if the user loaded on it is valid
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Checks whether a JWT exists in an auth header and if this token
     * starts with the correct prefix
     * @param authHeader header to be analyzed
     * @return <code>true</code> if the token exists and starts with the correct authentication prefix,
     * <code>false</code> otherwise
     */
    private boolean tokenExist(String authHeader){
        return authHeader != null && authHeader.startsWith(PREFIX);
    }

    /**
     * Retrieves the JWT in a header. This token is always before the Token's prefix
     * @param header header where the token is
     * @return String with the token gotten
     */
    private String getTokenFromHeader(String header){
        return header.substring(PREFIX.length());
    }

    /**
     * Checks whether the user is already authenticated
     * @return <code>true</code> if the user is already authenticated, <code>false</code> otherwise
     */
    private boolean userIsAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    /**
     * Generates a token from a request and user details and sets it in the SecurityContextHolder.
     * @param request It is the request where the info will be taken from
     * @param userDetails User details used to generate the token
     */
    private void setAuthTokenInContext(HttpServletRequest request, UserDetails userDetails){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * This filter checks whether a JWT exists
     *
     * @param request is the request it will handle
     * @param response is the response it will be returned
     * @param filterChain filterChain where the filter is inserted
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER);
        if(tokenExist(authHeader)){
            final String token = getTokenFromHeader(authHeader);
            final String username = jwtService.extractUsername(token);
            if(username != null && !userIsAuthenticated()){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(jwtService.isTokenValid(token, userDetails)){
                    setAuthTokenInContext(request,userDetails);
                }

            }
        }
        filterChain.doFilter(request,response);
    }
}
