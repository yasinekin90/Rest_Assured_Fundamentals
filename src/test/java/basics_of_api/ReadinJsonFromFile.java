package basics_of_api;

import files.PayLoad;
import files.ReusableMethods;
import io.restassured.RestAssured;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReadinJsonFromFile extends ReusableMethods {

    public static void main(String[] args) throws IOException {
        //validate if Add Place API is working as expected
        //given -all input details
        //when-submit the API-resource,http method
        //Then-validate the response
        //content of the file to String ->content of file can convert into byte->Byte data to string

        //Add place ->Update Place with New Adress ->Get Place to validate if New adress is present in response

        //ADDPLACE
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\user\\IdeaProjects\\RestAssuredTutorials\\src\\test\\java\\basics_of_api\\addPlace.json"))))
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();


        String placeId = rawToJson(response).getString("place_id");

        //Update Place

        String newAddress = "60 winter walk, USA";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));


        //GET PLACE

        String getPlaceResponse = given().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();


        String actualAddress = rawToJson(getPlaceResponse).getString("address");

        // System.out.println(actualAddress);

        Assert.assertEquals(newAddress, actualAddress);


    }


}
