package br.com.app.api.model.auth;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement
public class Credentials {

    private String username;
    private String password;
    private String scope;

    public Credentials() {
    }

    public Credentials(String username, String password, String scope) {
        this.username = username;
        this.password = password;
        this.scope = scope;
    }
}
