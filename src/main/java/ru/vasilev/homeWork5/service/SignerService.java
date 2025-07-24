package ru.vasilev.homeWork5.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

@Service
public class SignerService {

    private final RSAKey rsaKey;

    public SignerService(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public SignedJWT sign(JWTClaimsSet claimsSet) throws Exception {
        JWSSigner signer = new RSASSASigner(rsaKey);
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                claimsSet
        );
        signedJWT.sign(signer);
        return signedJWT;
    }

    public boolean verify(SignedJWT signedJWT) throws Exception {
        JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
        return signedJWT.verify(verifier);
    }
}
