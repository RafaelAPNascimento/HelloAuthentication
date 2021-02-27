package integration.api;

import br.com.app.api.model.SamplePayload;
import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static util.Constants.BASE_URI;

@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationTest {


    private static Credentials credentials;
    private JWT jwt;

    @BeforeAll
    public void init() {
        credentials = new Credentials("daniel", "78910", "read_only");
    }

    @Test
    @Order(1)
    public void shouldAuthenticate() {

        String path = "/auth/connect/token";

        Response response =
            given().baseUri(BASE_URI)
                .basePath(path)
                .contentType(ContentType.JSON)
                .request()
                .body(credentials)
                .log().all()
                .when().post()
                .peek();

        jwt = response.jsonPath().getObject("$", JWT.class);
        Assertions.assertNotNull(jwt);
    }


    @Test
    @Order(2)
    public void shouldBeAuthorizedByJWT() {

        given().baseUri(BASE_URI)
                .basePath("/service/{name}")
                .pathParam("name", "hello auth")
                .header(AUTHORIZATION, jwt.toStringForRequest())
                .request()
                .log().all()
                .when()
                .get()
                .peek()
                .then().assertThat().statusCode(SC_OK)
                .log().all();
    }

    @Test
    @DisplayName("Should not authorize after modifying the token")
    @Order(3)
    public void shouldNotAuthorize() {

        modifyToken();

        given().baseUri(BASE_URI)
                .basePath("/service/{name}")
                .pathParam("name", "hello auth")
                .header(AUTHORIZATION, jwt.toStringForRequest())
                .request()
                .log().all()
                .when()
                .get()
                .peek()
                .then().assertThat().statusCode(SC_UNAUTHORIZED);
    }

    private void modifyToken() {

        String token = jwt.getAccess_token();
        char[] chars = token.toCharArray();
        chars[65] = 'p';
        token = String.valueOf(chars);
        jwt.setAccess_token(token);
    }

    @Test
    @DisplayName("Should not authorize because token has expired")
    @Order(4)
    @Disabled
    public void shouldNotAuthorizeDueToExpiration() throws InterruptedException {

        shouldAuthenticate();

        Thread.sleep(60000);

        SamplePayload payload = new SamplePayload(1, "Rafael");

        given().baseUri(BASE_URI)
                .basePath("/service/send")
                .header(AUTHORIZATION, jwt.toStringForRequest())
                .contentType(ContentType.JSON)
                .request().body(payload)
                .log().all()
                .when()
                .post()
                .peek()
                .then().assertThat().statusCode(SC_UNAUTHORIZED)
                .log().all();
    }
 }
