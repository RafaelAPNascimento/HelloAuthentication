package integration.api;

import br.com.app.api.model.auth.Credentials;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static util.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationTest {


    private static Credentials credentials;

    @BeforeAll
    public void init() {
        credentials = new Credentials("daniel", "78910", "read_only");
    }

    @Test
    public void shouldAuthenticate() {

        String path = "/auth/connect/token";

        given().baseUri(BASE_URI)
                .basePath(path)
                .contentType(ContentType.JSON)
                .request()
                .body(credentials)
                .log().all()
                .when().post()
                .then().assertThat().statusCode(SC_OK)
                .log().all();
    }

    public void tes2() {
//        Response response =
//                given().baseUri(BASE_URI)
//                        .basePath(path)
//                        .contentType(ContentType.JSON)
//                        .request()
//                        .body(credentials)
//                        .log().all()
//                        .when().post()
//                        .then().log().all()
//                        .extract().response();
//
//        JWT token = response.jsonPath().getObject("$", JWT.class);
    }
}
