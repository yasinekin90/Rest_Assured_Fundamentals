package dynamicjson;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static files.ReusableMethods.*;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(PayLoad.addBook("123xxxw","33354xcv"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = rawToJson(response);

        String id = jsonPath.get("ID");
        System.out.println(id);


    }
}
