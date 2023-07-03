package com.rova.transactionservice.util;


import com.rova.transactionservice.dals.IAuthorities;
import com.rova.transactionservice.dals.IUserDetails;
import com.rova.transactionservice.exceptions.CommonsException;
import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.rova.transactionservice.util.Constants.ENABLED;
import static com.rova.transactionservice.util.Constants.EntityColumns.ID;
import static com.rova.transactionservice.util.Constants.SCOPES;
import static com.rova.transactionservice.util.Constants.TYPE;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final JwtSecretUtils jwtSecret;


    public JwtDto getValidJwtDetails(String token, TokenType tokenType) throws CommonsException {
        String message = "invalid.jwt.token";
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(jwtSecret.getValue())
                    .parseClaimsJws(token);
            Claims claims = jws.getBody();
            String id = claims.getId();
            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();
            Long userId = claims.get(ID, Long.class);
            Boolean enabled = claims.get(ENABLED, Boolean.class);
            ArrayList<?> scopes = claims.get(SCOPES, ArrayList.class);
            String type = (String) claims.getOrDefault(TYPE, null);
            JwsHeader<?> header = jws.getHeader();

            if (tokenType.name.equals(type)) {
                return JwtDto.builder().valid(true).id(id).type(type).userId(userId)
                        .enabled(enabled).username(subject).header(header).scopes(scopes)
                        .expiration(expiration).build();
            }

            message = "invalid jwt token type";
        } catch (PrematureJwtException e) {
            message = "premature jwt exception";
        } catch (ExpiredJwtException e) {
            message = "expired jwt exception";
        } catch (Exception e) {
            log.debug(message, e.getMessage());
        }
        throw new CommonsException(message, HttpStatus.UNAUTHORIZED);
    }


    @Setter
    @Builder
    public static class JwtDto {
        public String id;
        public Long userId;
        public JwsHeader<?> header;
        public boolean valid;
        public ArrayList<?> scopes;
        public ArrayList<?> perms;
        public String username;
        public Date expiration;
        public String type;
        public boolean enabled;

        public IUserDetails asUser() {
            IUserDetails iUserDetails = new IUserDetails();
            iUserDetails.setId(userId == null ? 0 : userId);
            iUserDetails.setUsername(username);
            iUserDetails.setEnabled(enabled);

            List<IAuthorities> authorities = scopes.stream()
                    .map(scope -> IAuthorities.instance(username, (String) scope))
                    .collect(Collectors.toList());
            iUserDetails.setAuthorities(authorities);
            return iUserDetails;
        }
    }


    @Getter
    public enum TokenType {
        ACCESS("access"),
        REFRESH("refresh");

        private final String name;

        TokenType(String name) {
            this.name = name;
        }
    }
}

