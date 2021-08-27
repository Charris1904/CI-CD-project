import io.restassured.response.Response;
import model.Comment;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static util.Constants.*;
import static util.UrlMapping.*;

public class CommentTests extends BaseTest {

    public static String postId = "6054";
    public static String postIdError = "605";

    @Test(priority = 1)
    public void createCommentSuccess() {
        Comment comment = new Comment("Name", "Comment");
        String path = String.format(CREATE_COMMENT, postId);

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(comment)
                .post(path)
                .then()
                .body(MESSAGE, equalTo(COMMENT_CREATED))
                .and()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 2)
    public void createCommentWithPostIdIncorrect() {
        Comment comment = new Comment("Name", "Comment");
        String path = String.format(CREATE_COMMENT, postIdError);

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(comment)
                .post(path)
                .then()
                .body(MESSAGE, equalTo(COMMENT_NOT_CREATED))
                .and()
                .statusCode(406)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 3)
    public void getCommentByPostIdSuccess() {
        String path = String.format(GET_ALL_COMMENTS, postId);

        Response response = given()
                .spec(RequestSpecs.generateBasicAuth())
                .get(path)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.path(KEY_RESULT_DATA).toString());
    }

    @Test(priority = 4)
    public void getCommentWithoutPostId() {
        String path = String.format(GET_ALL_COMMENTS, "");

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .get(path)
                .then()
                .body(containsString(ERROR_MSG))
                .statusCode(404);
    }

    @Test(priority = 5)
    public void getCommentByPostIdAndCommentIdSuccess() {
        String commentId = "3553";
        String path = String.format(COMMENT_BY_ID, postId, commentId);

        Response response = given()
                .spec(RequestSpecs.generateBasicAuth())
                .get(path)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.path(KEY_DATA).toString());
    }

    @Test(priority = 6)
    public void getCommentByPostIdAndCommentIdInvalid() {
        String commentId = "3498";
        String path = String.format(COMMENT_BY_ID, postId, commentId);

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .get(path)
                .then()
                .statusCode(404)
                .and()
                .body("Message", equalTo(COMMENT_NOT_FOUND))
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 7)
    public void updateCommentSuccess() {
        String commentId = "3553";
        String path = String.format(COMMENT_BY_ID, postId, commentId);
        Comment comment = new Comment("Name Updated", "Comment Updated");

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(comment)
                .put(path)
                .then()
                .statusCode(200)
                .and()
                .body(MESSAGE, equalTo(COMMENT_UPDATED))
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 8)
    public void updateCommentWithInvalidId() {
        String commentId = "3498";
        String path = String.format(COMMENT_BY_ID, postId, commentId);
        Comment comment = new Comment("Name Updated", "Comment Updated");

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(comment)
                .put(path)
                .then()
                .statusCode(406)
                .and()
                .body(MESSAGE, equalTo(COMMENT_NOT_UPDATED))
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 9)
    public void deleteComment() {
        String commentId = "3553";
        String path = String.format(COMMENT_BY_ID, postId, commentId);

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .delete(path)
                .then()
                .statusCode(200)
                .and()
                .body(MESSAGE, equalTo(COMMENT_DELETED))
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(priority = 10)
    public void deleteCommentInvalidCommentId() {
        String commentId = "3498";
        String path = String.format(COMMENT_BY_ID, postId, commentId);

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .delete(path)
                .then()
                .statusCode(406)
                .and()
                .body(MESSAGE, equalTo(COMMENT_NOT_DELETED))
                .spec(ResponseSpecs.defaultSpec());
    }
}
