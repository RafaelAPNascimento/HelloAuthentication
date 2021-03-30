package br.com.app.security;

import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

public class TokenFactory {

    // secrets should never be placed in code
    final static String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnm0123456789";
    final static String ISSUER = "rafael.senior.engineer";
    final static String HEADER = "{\"alg\":\"HS256\", \"typ\":\"jwt\"}";

    public static JWT issueToken(Credentials credentials) {

        JWT jwt = new JWT();
        fillHeader(jwt);
        fillPayload(jwt, credentials);
        fillSignature(jwt);
        return jwt;
    }

    private static void fillSignature(JWT jwt) {

        byte[] encryptedSignature = hs256(jwt.getB64Header() +"."+ jwt.getB64Payload());
        jwt.setSignature(encode(encryptedSignature));
    }

    static byte[] hs256(String data) {

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            mac.init(secretKey);

            byte[] encryptedData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return encryptedData;
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void fillPayload(JWT jwt, Credentials credentials) {

        JSONObject payload = new JSONObject();
        payload.put("iss", ISSUER);
        payload.put("scope", credentials.getScope());
        payload.put("name", credentials.getUsername());
        payload.put("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        payload.put("exp", LocalDateTime.now().plusMinutes(1).toEpochSecond(ZoneOffset.UTC));
        payload.put("jti", UUID.randomUUID().toString());

        jwt.setPayload(payload);
        jwt.setB64Payload(encode(payload.toString()));
        jwt.setExpires_in(String.valueOf(payload.getLong("exp")));
    }

    private static void fillHeader(JWT jwt) {
        jwt.setB64Header(encode(HEADER));
    }

    static String encode(String data) {
        return encode(data.getBytes(StandardCharsets.UTF_8));
    }

    static String encode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }
}
