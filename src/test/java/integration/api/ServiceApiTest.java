package integration.api;

import annotations.IntegrationTest;
import br.com.app.api.model.SamplePayload;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.*;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static util.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceApiTest extends AbstractTest {

    @BeforeAll
    public void init() {
        setToken();
    }

    @IntegrationTest
    @DisplayName("should return 200 OK when send payload")
    public void test01() {

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
                .then().assertThat().statusCode(SC_OK)
                .log().all();
    }

    @IntegrationTest
    @DisplayName("should return 200 OK when request with name path")
    public void test02() {

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
}
