import io.restassured.response.Response;
import model.Post;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static util.Constants.*;
import static util.UrlMapping.*;

public class PostTests extends BaseTest {

    private static String postId = "6050";

    @Test(priority = 1)
    public void createPostSuccess(){
        Post post = new Post("Title", "Content");

        given()
                .spec(RequestSpecs.generateToken())
                .body(post)
                .post(CREATE_POST)
                .then()
                .body(MESSAGE, equalTo(POST_CREATED))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 2)
    public void createPostNotSuccess(){
        Post post = new Post("Title", "");

         given()
                .spec(RequestSpecs.generateToken())
                .body(post)
                .post(CREATE_POST)
                .then()
                .body(MESSAGE, equalTo(INVALID_FORM))
                .and()
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 3)
    public void getAllPosts() {
        Response response = given()
                .spec(RequestSpecs.generateToken())
                .get(GET_ALL_POSTS)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.path(KEY_RESULT_DATA).toString());
    }

    @Test(priority = 4)
    public void getAllPostsNotSuccess() {
        given()
                .get(GET_ALL_POSTS)
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 5)
    public void getPostByIdSuccess() {
        String path = String.format(POST_BY_ID, postId);

        Response response = given()
                .spec(RequestSpecs.generateToken())
                .get(path)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.path(KEY_DATA).toString());
    }

    @Test(priority = 6)
    public void getPostByIdNotSuccess() {
        String postId = "1";
        String path = String.format(POST_BY_ID, postId);

        given()
                .spec(RequestSpecs.generateToken())
                .get(path)
                .then()
                .body("Message", equalTo(POST_NOT_FOUND))
                .and()
                .statusCode(404)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 7)
    public void updatePostByIdSuccess(){
        Post post = new Post("Title Updated", "Content Updated");
        String postId = "6050";
        String path = String.format(POST_BY_ID, postId);

        given()
                .spec(RequestSpecs.generateToken())
                .body(post)
                .put(path)
                .then()
                .body(MESSAGE, equalTo(POST_UPDATED))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 8)
    public void updatePostByIdNotSuccess(){
        Post post = new Post("Title Updated", "Content Updated");
        String postId = "1";
        String path = String.format(POST_BY_ID, postId);

        given()
                .spec(RequestSpecs.generateToken())
                .body(post)
                .put(path)
                .then()
                .body(MESSAGE, equalTo(POST_NOT_UPDATED))
                .and()
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 9)
    public void deletePostByIdSuccess(){
        String path = String.format(POST_BY_ID, postId);

        given()
                .spec(RequestSpecs.generateToken())
                .delete(path)
                .then()
                .body(MESSAGE, equalTo(POST_DELETED))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 10)
    public void deletePostByIdNotSuccess(){
        String path = String.format(POST_BY_ID, postId);

        given()
                .spec(RequestSpecs.generateToken())
                .delete(path)
                .then()
                .body(MESSAGE, equalTo(POST_NOT_DELETED))
                .and()
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }
}
