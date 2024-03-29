package com.mannavoca.zenga.common.security.jwt;

import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.security.exception.ExpiredTokenException;
import com.mannavoca.zenga.common.security.exception.InvalidTokenException;
import com.mannavoca.zenga.domain.auth.application.dto.response.TokenResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.mannavoca.zenga.common.consts.ApplicationConst.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private String buildAccessToken(Long id, Date now) {
        return buildToken(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenPeriod()))
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .compact();
    }

    private String buildRefreshToken(Long id, Date now) {
        final String refreshToken = buildToken(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenPeriod()))
                .setSubject(id.toString())
                .setId(UUID.randomUUID().toString())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .compact();
        storeRefreshToken(id, refreshToken);
        return refreshToken;
    }

    private void storeRefreshToken(Long id, String refreshToken) {
        redisTemplate.opsForValue().set(
                refreshToken,
                id.toString(),
                jwtProperties.getRefreshTokenPeriod(),
                TimeUnit.MILLISECONDS);
    }

    private JwtBuilder buildToken(Date now) {
        final Key key = getSecretKey();
        return Jwts.builder()
                .setIssuedAt(now)
                .signWith(key);
    }

    public String generateAccessToken(Long id) {
        final Date now = new Date();
        return buildAccessToken(id, now);
    }

    public String generateRefreshToken(Long id) {
        final Date now = new Date();
        return buildRefreshToken(id, now);
    }

    /**
     * TODO : 예외 처리 관련해서 좀 더 고민해보기
     * @throws InvalidTokenException : 유효하지 않은 토큰
     * @throws ExpiredTokenException : 만료된 토큰
     * @throws IllegalArgumentException : 토큰이 null일 경우
     */
    public void validateToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        try {
            jwtParser.parse(token);
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw InvalidTokenException.of(Error.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.of(Error.EXPIRED_TOKEN);

        }
    }

    public Long extractId(String token) {
        return Long.valueOf(
                Jwts.parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public TokenResponseDto reIssueTokens(final Long userId) {
        final String accessToken = generateAccessToken(userId);
        final String refreshToken = generateRefreshToken(userId);
        redisTemplate.opsForValue().set(refreshToken, userId.toString(), jwtProperties.getRefreshTokenPeriod(), TimeUnit.MILLISECONDS);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Long validateRefreshToken(final String refreshToken) {
//        validateToken(refreshToken);

        return Optional.ofNullable(getRefreshToken(refreshToken))
                .orElseThrow(() -> InvalidTokenException.of(Error.REFRESH_TOKEN_NOT_FOUND));
    }

    private Long getRefreshToken(final String refreshToken) {
        String userIdString = redisTemplate.opsForValue().getAndDelete(refreshToken);
        return userIdString == null ? null : Long.valueOf(userIdString);
    }
}