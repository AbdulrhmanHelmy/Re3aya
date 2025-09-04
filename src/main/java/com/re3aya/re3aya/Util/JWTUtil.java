package com.re3aya.re3aya.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service

public class JWTUtil {
    private String  SEKRET_KEY="sdfdfkdgkdsfnkgsdfkgfsdkgnfdjgsfsjjnjnfdajkfdajafd";

    public String generateToken(UserDetails userDetails){
        Map<String, Object> map=new HashMap<>();

        String role=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        map.put("role",role);
        return Jwts.builder()
                .addClaims(map)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .setSubject(userDetails.getUsername())
                .signWith(gitSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T > claimResolver){
        final Claims claims =extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(gitSignKey())
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean IsTokenValid(String token,UserDetails userDetails){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isExpired(token));
    }

    public boolean isExpired(String token){
        return extractEx(token).before(new Date());
    }

    public Date extractEx(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private Key gitSignKey(){
        return Keys.hmacShaKeyFor(SEKRET_KEY.getBytes());
    }
}