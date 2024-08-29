package com.rsl.event.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    // JWT token validity in seconds (60 minutes = 1 hour)
    public static final long JWT_TOKEN_VALIDITY = 60 * 60;

    // Secret key used for signing the JWT
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    
//      Retrieve the username from the JWT token.
//     
//      @param token the JWT token
//      @return the username extracted from the token
//     
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

//    
//      Retrieve the expiration date from the JWT token.
//   
//      @param token the JWT token
//      @return the expiration date extracted from the token
//     
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

//    
//      Retrieve a specific claim from the JWT token.
//     
//      @param token the JWT token
//      @param claimsResolver a function to extract the specific claim
//      @param <T> the type of the claim
//      @return the extracted claim
//     
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

//    
//      Retrieve all claims from the JWT token using the secret key.
//     
//      @param token the JWT token
//      @return all claims contained in the token
//     
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

//    
//      Check if the JWT token has expired.
//     
//      @param token the JWT token
//      @return true if the token is expired, false otherwise
//     
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    
//      Generate a JWT token for the user.
//     
//      @param userDetails the user details
//      @return the generated JWT token
//     
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

//    
//      Create the JWT token with claims, subject, issued date, expiration date,
//      and sign it with the secret key.
//     
//      @param claims the claims to be included in the token
//      @param subject the subject (username) for whom the token is generated
//      @return the generated JWT token
//     
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

//    
//      Validate the JWT token.
//     
//      @param token the JWT token
//      @param userDetails the user details for validation
//      @return true if the token is valid and matches the user details, false otherwise
//    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
