package integration.api;

import br.com.app.api.model.SamplePayload;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static util.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceApiTest {

    @Test
    @DisplayName("should return 200 OK when send payload")
    public void test01() {

        SamplePayload payload = new SamplePayload(1, "Rafael");

        given().baseUri(BASE_URI)
                .basePath("/service/send")
                .contentType(ContentType.JSON)
                .request().body(payload)
                .log().all()
                .when()
                .post()
                .peek()
                .then().assertThat().statusCode(SC_OK)
                .log().all();
    }

    @Test
    @DisplayName("should return 200 OK when request with name path")
    public void test02() {

        given().baseUri(BASE_URI)
                .basePath("/service/{name}")
                .pathParam("name", "hello auth")
                .request()
                .log().all()
                .when()
                .get()
                .peek()
                .then().assertThat().statusCode(SC_OK)
                .log().all();
    }
}
