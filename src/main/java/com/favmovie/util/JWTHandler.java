package com.favmovie.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JWTHandler {
    private static Algorithm algorithm;

    private JWTHandler() {
    }

    private static Algorithm algorithmBuilder() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        return Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
    }

    private static Algorithm getAlgorithm() throws NoSuchAlgorithmException {
        if (algorithm == null) algorithm = algorithmBuilder();
        return algorithm;
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(getAlgorithm()).build().verify(token);
        } catch (Exception e) {
            return null;
        }
    }

    public static String createToken(Long id, String username) throws NoSuchAlgorithmException {
        return JWT.create().withIssuer("auth0")
                .withClaim(CustomClaims.USER_ID.getValue(), id)
                .withClaim(CustomClaims.USER_NAME.getValue(), username)
                .sign(getAlgorithm());
    }
}
