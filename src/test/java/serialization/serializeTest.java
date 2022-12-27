package serialization;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import serialization.pojo.AddPlace;
import serialization.pojo.Location;
import java.util.ArrayList;
import java.util.Arrays;


import static io.restassured.RestAssured.given;

public class serializeTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";


        AddPlace addPlace=new AddPlace(65,"Selami","12321546483"
                ,"istanbul/basaksehir","www.abc.com","english"
                ,new Location(-38.2535,63.456),new ArrayList<>(Arrays.asList("shoe park","shop","shake")));

    String response =  given()
                .log().all()
                .queryParam("key", "qaclick123")
                .body(addPlace)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).defaultParser(Parser.JSON).toString();


    }
}
