package br.com.app.api.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.json.JSONWriter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class JWT {

    private String access_token;
    private String expires_in;
    private String token_type;

    @XmlTransient
    private String b64Header;
    @XmlTransient
    private JSONObject payload;
    @XmlTransient
    private String b64Payload;
    @XmlTransient
    private String signature;
}
