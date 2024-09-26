package com.counseling.cms.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.counseling.cms.utility.CookieUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(String userId, String authority) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("authority", authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String userId, String authority) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("authority", authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 24))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {
        return extractClaims(token).get("userId").toString();
    }

    public String extractAuthority(String token) {
        return extractClaims(token).get("authority").toString();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public void saveCookie(HttpServletResponse res, String token) {
        Cookie accessTokenCookie = new Cookie("accessToken", token);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1 * 24 * 60 * 60);
        res.addCookie(accessTokenCookie);
    }

    public void removeCookie(HttpServletResponse res, HttpServletRequest req) {
        CookieUtility.deleteCookie(res, "accessToken", "/");
        CookieUtility.deleteCookie(res, "loginStatus", "/");
        req.getSession().invalidate();

        res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
    }
}
