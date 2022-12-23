package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraTest {
    public static void main(String[] args) {

        baseURI = "http://localhost:8081";
        //Login Scenario
        //get session id
        SessionFilter sessionFilter = new SessionFilter(); //you can store response in sessionFilter variable


        String response = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\":\"ekinyasin90\",\n" +
                        "    \"password\":\"159357258Aa.\"\n" +
                        "}").log().all().filter(sessionFilter).when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();//produces a session id


        String expectedMessage = "Hi How are you?";
        //Add comment
        String addCommentResponse = given().pathParam("id", "10100").log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"" + expectedMessage + "\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(sessionFilter) //get the session id from session filter
                .when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(addCommentResponse);
        String idComment = js.getString("id");


        //Add attachment
       /* given().header("X-Atlassian-Token","no-check")
                .filter(sessionFilter)
                .pathParam("id", "10100")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("jira.txt")) //adding attachment
                .when().post("/rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);*/

        //Get Issue

        String issueDetails = given().filter(sessionFilter)
                .pathParam("Key", "10100")
                .queryParam("fields", "comment")
                .when().get("/rest/api/2/issue/{Key}")
                .then().log().all().extract().response().asString();

        System.out.println("issueDetails = " + issueDetails);

        JsonPath jsonPath = new JsonPath(issueDetails);

        int commentsCount = jsonPath.getInt("fields.comment.comments.size()");
        String message=null;
        for (int i = 0; i < commentsCount; i++) {

            String commentIdIssue = jsonPath.get("fields.comment.comments[" + i + "].id").toString();
            if (commentIdIssue.equalsIgnoreCase(idComment)) {
                 message = jsonPath.get("fields.comment.comments[" + i + "].body").toString();
            }
        }

        Assert.assertEquals(message,expectedMessage);
    }
}
