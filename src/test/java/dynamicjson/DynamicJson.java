package dynamicjson;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static files.ReusableMethods.*;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn,String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(PayLoad.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = rawToJson(response);

        String id = jsonPath.get("ID");
        System.out.println(id);

    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][]{{"xcv345","5578"},{"wer345","7790"},{"ass45","rty890"}};
    }
}
