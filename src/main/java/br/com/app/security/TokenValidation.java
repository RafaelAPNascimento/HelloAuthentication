package br.com.app.security;

import br.com.app.api.model.auth.JWT;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static br.com.app.security.TokenFactory.encode;

public class TokenValidation {

    public static boolean isValid(JWT jwt) {

        JSONObject payload = jwt.getPayload();
        boolean isTimeValid = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) < payload.getLong("exp");
        boolean isSignatureValid = isSignatureValid(jwt);

        return isTimeValid && isSignatureValid;
    }

    private static boolean isSignatureValid(JWT jwt) {

        String b64Data = jwt.getB64Header() + "." + jwt.getB64Payload();
        byte[] bytesSignature = TokenFactory.hs256(b64Data);

        String expectedSignature = encode(bytesSignature);

        return expectedSignature.equals(jwt.getSignature());
    }
}
