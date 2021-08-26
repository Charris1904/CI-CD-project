package helpers;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import javax.xml.crypto.Data;

import static io.restassured.RestAssured.given;

public class RequestHelper {

    public static String getUserToken(){
        Response response = given().body(DataHelper.getTestUser()).post("/v1/user/login");

        JsonPath jsonPathEvaluator = response.jsonPath();
        String token = jsonPathEvaluator.get("token.access_token");
        return token;
    }

    public static RequestSpecification basicAuthentication(){
        RequestSpecification requestSpecification = RestAssured.given()
                .auth()
                .basic(DataHelper.getUserBasicAuth().getName(), DataHelper.getUserBasicAuth().getPassword());
        return requestSpecification;
    }
}
