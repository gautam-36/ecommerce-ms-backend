package com.example.user_service.security;


import com.example.user_service.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class JwtService {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private final  String SECRET_KEY = "B374A26A71490437AA024E4FADD5B497FDFF1A8EA6FF12F6FB65AF2720B59CCF";

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(Map<String,Object> extraClaims,
                                User userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("tokenType", "access")
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(User userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String extractUsername(String token ){
        return extractClaim(token,Claims::getSubject);
    }

    public long extractExpirationInSeconds(String token) {
        Claims claims = extractAllClaims(token);
        Date expiration = claims.getExpiration();
        Instant instant = expiration.toInstant();
        long expirationInSeconds = instant.getEpochSecond();
        log.info(expirationInSeconds+" expirationInSeconds");
        return expirationInSeconds ;
    }

    public boolean isTokenValid(String token, User userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[]keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();

    }


}
