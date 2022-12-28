package serialization;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.pojo.AddPlace;
import serialization.pojo.Location;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class specBuilderTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";


        AddPlace addPlace = new AddPlace(65, "Selami", "12321546483"
                , "istanbul/basaksehir", "www.abc.com", "english"
                , new Location(-38.2535, 63.456), new ArrayList<>(Arrays.asList("shoe park", "shop", "shake")));

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given()
                        .spec(req)
                        .body(addPlace);


        Response response = res
                .when().post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();

        String resString = response.asString();

        System.out.println(resString);


    }
}
