package integration.api;

import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static util.Constants.BASE_URI;

public abstract class AbstractTest {

    protected static JWT jwt;
    private static Credentials credentials = new Credentials("daniel", "78910", "read_only");

    protected static void setToken() {
        requestToken(credentials);
    }

    protected static void setToken(Credentials credentials) {
        requestToken(credentials);
    }

    private static void requestToken(Credentials credentials) {

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
    }
}
