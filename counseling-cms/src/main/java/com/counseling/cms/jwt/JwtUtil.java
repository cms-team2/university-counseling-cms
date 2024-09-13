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

    //JWT 토큰을 생성 후 return
    public String generateToken(String userId, String authority) {
    	Claims claims=Jwts.claims();
    	claims.put("userId", userId);
    	claims.put("authority", authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    

	//refresh토큰 생성
	public String generateRefreshToken(String userId, String authority) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("authority", authority);	
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime*24))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	//refresh토큰 생성
	public String autoLoginGenerateRefreshToken(String userId, String authority) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("authority", authority);	
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime*360))			//자동 로그인 체크시 15일 동안 유지
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
    
    //JWT 토큰에서 Claims 정보를 추출
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    //JWT 토큰에서 사용자 아이디 추출
    public String extractUserId(String token) {
        return extractClaims(token).get("userId").toString();
    }
    
    //JWT 토큰에서 사용자 권한 추출
    public String extractAuthority(String token) {
        return extractClaims(token).get("authority").toString();
    }

    //토큰 만료 여부 체크
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
    //쿠키에 토큰 저장
    public void saveCookie(HttpServletResponse res, String token) {
    	Cookie accessTokenCookie = new Cookie("accessToken", token);
	    accessTokenCookie.setHttpOnly(true);
	    accessTokenCookie.setPath("/");
	    accessTokenCookie.setMaxAge(1 * 24 * 60 * 60); 
	    res.addCookie(accessTokenCookie);
    }
    
    //쿠키 삭제
    public void removeCookie(HttpServletResponse res, HttpServletRequest req) {
		CookieUtility.deleteCookie(res, "accessToken", "/");
		req.getSession().invalidate();
		
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0"); // 캐시 방지
	    res.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    res.setDateHeader("Expires", 0);
    }

}
