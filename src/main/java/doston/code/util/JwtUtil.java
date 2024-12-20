package doston.code.util;

import doston.code.dto.JwtDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.token.live-time}")
    private long tokenLiveTime;

    @Value("${jwt.refresh-token.live-time}")
    private long refreshTokenLiveTime;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String encode(String login, String role) {
        return getString(login, role, tokenLiveTime);
    }

    private String getString(String login, String role, long tokenLiveTime) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("login", login);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshToken(String login, String role) {
        return getString(login, role, refreshTokenLiveTime);
    }

    public TokenValidationResult validateToken(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            Date now = new Date();

            if (expiration.before(now)) {
                return new TokenValidationResult(false, "Token has expired");
            }

            return new TokenValidationResult(true, "Token is valid");

        } catch (ExpiredJwtException e) {
            return new TokenValidationResult(false, "Token has expired");
        } catch (MalformedJwtException e) {
            return new TokenValidationResult(false, "Invalid token format");
        } catch (Exception e) {
            return new TokenValidationResult(false, "Invalid token");
        }
    }

    public JwtDTO decode(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String login = (String) claims.get("login");
        String role = (String) claims.get("role");

        return new JwtDTO(login, role);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public record TokenValidationResult(boolean valid, String message) {
    }


}
