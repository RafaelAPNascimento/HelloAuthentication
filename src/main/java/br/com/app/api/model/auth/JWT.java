package br.com.app.api.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class JWT {

    private String access_token;
    private String expires_in;
    private final String token_type = "Bearer";

    @XmlTransient
    private String b64Header;
    @XmlTransient
    private JSONObject payload;
    @XmlTransient
    private String b64Payload;
    @XmlTransient
    private String signature;

    public JWT(String access_token) {
        this.access_token = access_token;
        decode();
    }

    private void decode() {

        String[] parts = access_token.split("\\.");
        b64Header = parts[0];
        b64Payload = parts[1];
        payload = decodePayload(parts[1]);
        signature = parts[2];
    }

    private JSONObject decodePayload(String part) {

        byte[] bytes = Base64.getUrlDecoder().decode(part);
        String payload = new String(bytes, StandardCharsets.UTF_8);
        return new JSONObject(payload);
    }

    public void setSignature(String signature) {
        this.access_token = b64Header +"."+ b64Payload +"."+ signature;
        this.signature = signature;
    }

    public String toStringForRequest() {
        return token_type +" "+ access_token;
    }
}
