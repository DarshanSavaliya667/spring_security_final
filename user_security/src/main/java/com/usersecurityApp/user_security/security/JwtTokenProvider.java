package com.usersecurityApp.user_security.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usersecurityApp.user_security.model.User;
import com.usersecurityApp.user_security.payload.TokenObject;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    public String generateToken(Authentication authentication, String aud, User u, List<String> urls)
            throws JsonProcessingException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

//		System.err.println("randomString" + UUID.randomUUID().toString());
//		TokenObject
        String setSubject = setSubject(new TokenObject(u.getId(), u.getIsAdmin(),
                u.getIsSuperAdmin(), urls));

        return Jwts.builder().setSubject(setSubject).setIssuedAt(new Date()).setExpiration(expiryDate).setId(u.getId().toString())
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    private String setSubject(TokenObject tokenObject) throws JsonProcessingException {
        ObjectMapper MAPPER = new ObjectMapper();
        return MAPPER.writeValueAsString(tokenObject);
    }
    public Long getIdFromJWT(String token) {

        String claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
//		System.err.println(claims);
        return Long.valueOf(claims);
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } /*catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } */catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
