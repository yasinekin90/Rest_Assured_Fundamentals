package oauth;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static io.restassured.RestAssured.*;

public class OauthTest {
    public static void main(String[] args) {



        /*https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWgavdcJZBegCwaGaL10D4UPywFIHW6uNq44lTgO2HQooyQqjxz8izQR2AFJOAJrUHbLew&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none*/

        String currentUrl="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWgavdcJZBegCwaGaL10D4UPywFIHW6uNq44lTgO2HQooyQqjxz8izQR2AFJOAJrUHbLew&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

        //google dont accept automation once you want to automate your gmail login..So you should manually get the url code ..
        String partialCode=currentUrl.split("code=")[1];
        String code = partialCode.split("&scope")[0];

        System.out.println("code = " + code);


        String accessTokenResponse =
                given()
                .urlEncodingEnabled(false)
                .queryParam("code", code)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString(); //to get access_token

        JsonPath js=new JsonPath(accessTokenResponse);
        String access_token = js.getString("access_token");


        String response = given().queryParam("access_token", access_token)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println("response = " + response);
    }
}
