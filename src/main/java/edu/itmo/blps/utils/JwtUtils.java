package edu.itmo.blps.utils;

import edu.itmo.blps.DemoApplication;
import edu.itmo.blps.dao.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.NonNull;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtils {
	private static final long EXPIRATION = DemoApplication.getApplicationContext()
			.getEnvironment().getProperty("app.jwt.expiration", Integer.class, 1440) * 1000 * 60;;
	private static final String TOKEN_SECRET = DemoApplication.getApplicationContext()
			.getEnvironment().getProperty("app.jwt.secret", "privateKey");

//	private static final SecretKey accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TOKEN_SECRET));

	public static String generateToken(User user){
		return Jwts.builder()
				.setSubject(user.getUsername())
				.signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.compact();
	}

	public static Claims parseToken(String token){
		return  Jwts
				.parserBuilder()
				.setSigningKey(TOKEN_SECRET)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public static boolean validateAccessToken(@NonNull String accessToken) {
		return validateToken(accessToken, TOKEN_SECRET);
	}

	private static boolean validateToken(@NonNull String token, @NonNull String secret) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(secret)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}
