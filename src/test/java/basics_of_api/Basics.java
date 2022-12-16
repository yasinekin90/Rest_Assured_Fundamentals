package basics_of_api;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) {
        //validate if Add Place API is working as expected
        //given -all input details
        //when-submit the API-resource,http method
        //Then-validate the response
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(PayLoad.AddPlace())
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

       // System.out.println(response);

        JsonPath js=new JsonPath(response);//for parsing json

        String placeId=js.getString("place_id");
        System.out.println(placeId);

        //Add place ->Update Place with New Adress ->Get Place to validate if New adress is present in response

    }

}
