package ru.vasilev.homeWork5.service;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Service;
import ru.vasilev.homeWork5.model.TokenPayload;

import java.util.Date;

@Service
public class ClaimService {
    public JWTClaimsSet buildClaims(TokenPayload payload) {
        return new JWTClaimsSet.Builder()
                .claim("username", payload.getUsername())
                .claim("role", payload.getRole())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 1000))
                .build();
    }
}
