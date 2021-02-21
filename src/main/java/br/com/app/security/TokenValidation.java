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
        boolean isSignValid = isSignatureValid(jwt);

        return isTimeValid && isSignValid;
    }

    private static boolean isSignatureValid(JWT jwt) {

        String b64Data = jwt.getB64Header() + "." + jwt.getB64Payload();
        byte[] encryptedSignature = TokenFactory.hs256(b64Data);
        String expected64Signature = encode(encryptedSignature);

        return expected64Signature.equals(jwt.getSignature());
    }
}
