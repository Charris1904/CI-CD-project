import io.restassured.RestAssured;
import org.testng.annotations.*;

public class BaseTest {

    @Parameters("baseUrl")
    @BeforeClass
    public void setup(@Optional("http://localhost:9000") String baseUrl ) {

        RestAssured.baseURI = baseUrl;
    }
}
