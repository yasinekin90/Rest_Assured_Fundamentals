package oauth;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.*;

public class OauthTest {
    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
          WebDriver driver=new ChromeDriver();


          driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
          driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ekinyasin90@gmail.com", Keys.ENTER);
          driver.findElement(By.cssSelector("input[type='password']")).sendKeys("159357258Aa.", Keys.ENTER);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currentUrl = driver.getCurrentUrl();


        String accessTokenResponse = given()
                .queryParam("code", "")
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js=new JsonPath(accessTokenResponse);
        String access_token = js.getString("access_token");


        String response = given().queryParam("access_token", access_token)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();
    }
}
