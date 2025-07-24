package ru.vasilev.homeWork5.service;


import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import ru.vasilev.homeWork5.exception.InvalidTokenException;
import ru.vasilev.homeWork5.model.TokenPayload;

import java.util.Date;

@Service
public class TokenService {

    private final ClaimService claimService;
    private final SignerService signerService;
    private final EncryptionService encryptionService;

    public TokenService(ClaimService claimService, SignerService signerService, EncryptionService encryptionService) {
        this.claimService = claimService;
        this.signerService = signerService;
        this.encryptionService = encryptionService;
    }

    public String generateToken(TokenPayload payload) throws Exception {
        JWTClaimsSet claims = claimService.buildClaims(payload);
        SignedJWT signedJWT = signerService.sign(claims);
        return encryptionService.encrypt(signedJWT);
    }

    public TokenPayload validateToken(String token) throws Exception {
        SignedJWT signedJWT = encryptionService.decrypt(token);

        if (!signerService.verify(signedJWT)) {
            throw new InvalidTokenException("invalid-signature");
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        if (claims.getExpirationTime().before(new Date())) {
            throw new InvalidTokenException("expired");
        }

        return new TokenPayload(
                claims.getStringClaim("username"),
                claims.getStringClaim("role")
        );
    }
}
