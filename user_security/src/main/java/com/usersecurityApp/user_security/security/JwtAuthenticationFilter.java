package com.usersecurityApp.user_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                     throws ServletException, IOException {

        try{
                String jwt=getJwtFromRequest(request);

                if (StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)){

                    Long id = tokenProvider.getIdFromJWT(jwt);
                    UserDetails userDetails=customUserDetailsService.loadUserById(id);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else
                {
                    deleteUserSession(jwt);
                }

                
        }
        catch (Exception ex) {
//			String jwt = getJwtFromRequest(request);
//			System.err.println(jwt);
//			deleteUserSession(jwt);
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);

    }

    private void deleteUserSession(String jwt) {
     //   String userId = tokenProvider.getUserIdFromJWT(jwt);
//		System.err.println(userId);
//		tokenExpireRepository.deleteByJwt(jwt);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
