package ru.vasilev.homeWork5.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import ru.vasilev.homeWork5.exception.TokenProcessingException;

@Service
public class EncryptionService {

    private final RSAKey rsaKey;

    public EncryptionService(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String encrypt(SignedJWT signedJWT) throws Exception {
        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                        .contentType("JWT")
                        .build(),
                new Payload(signedJWT)
        );

        jweObject.encrypt(new RSAEncrypter(rsaKey.toRSAPublicKey()));
        return jweObject.serialize();
    }

    public SignedJWT decrypt(String token) {
        try {
            JWEObject jweObject = JWEObject.parse(token);
            jweObject.decrypt(new RSADecrypter(rsaKey.toRSAPrivateKey()));
            return jweObject.getPayload().toSignedJWT();
        } catch (JOSEException e) {
            throw new TokenProcessingException("decrypt-error");
        } catch (java.text.ParseException e) {
            throw new TokenProcessingException("malformed");
        }
    }
}
