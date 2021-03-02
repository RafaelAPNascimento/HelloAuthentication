package unit;

import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import br.com.app.security.TokenFactory;
import br.com.app.security.TokenValidation;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

import static util.Constants.TOKEN_HEADER;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenFactoryTest {

    private static Credentials credentials;

    @BeforeAll
    public void init() {
        credentials = new Credentials("daniel", "78910", "read_only");
    }

    @Test
    public void headerShouldBeValid() {

        JWT jwt = TokenFactory.issueToken(credentials);
        String b64Header = Base64.getUrlEncoder().withoutPadding().encodeToString(TOKEN_HEADER.getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(b64Header, jwt.getB64Header());
    }

    @Test
    public void payloadShouldBeValid() {

        JWT jwt = TokenFactory.issueToken(credentials);
        String b64Payload = Base64.getUrlEncoder().withoutPadding().encodeToString(jwt.getPayload().toString().getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(b64Payload, jwt.getB64Payload());
    }


    @Test
    public void tokenShouldBeValid() {

        JWT jwt = TokenFactory.issueToken(credentials);
        Assertions.assertTrue(TokenValidation.isValid(jwt), "Token should be valid!");
    }

    @Test
    public void tokenShouldNotdBeValid() {

        JWT jwt = TokenFactory.issueToken(credentials);

        JSONObject payload = jwt.getPayload();
        payload.put("exp", LocalDateTime.now().plusDays(3).toEpochSecond(ZoneOffset.UTC));
        jwt.setB64Payload(Base64.getUrlEncoder().withoutPadding().encodeToString(payload.toString().getBytes(StandardCharsets.UTF_8)));

        Assertions.assertFalse(TokenValidation.isValid(jwt), "Token should be INvalid");
    }
}
