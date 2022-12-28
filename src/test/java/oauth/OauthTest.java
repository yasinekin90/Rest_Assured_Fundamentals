package oauth;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OauthTest {
    public static void main(String[] args) throws InterruptedException {



        /*https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWgavdcJZBegCwaGaL10D4UPywFIHW6uNq44lTgO2HQooyQqjxz8izQR2AFJOAJrUHbLew&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none*/

     /*   WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ekinyasin90");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys("xxx");
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        String url=driver.getCurrentUrl();*/

        String []courseTitles={"Selenium Webdriver Java","Cypress","Protractor"};
        String currentUrl = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&" +
                "code=4%2F0AWgavdeaStUnxuIbW0Pp7Y6zUVt3Lt6kSZnfhQyyTpQ3docA9bzw7uiXJFwGT8UcTjpJuw" +
                "&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid" +
                "&authuser=0" +
                "&prompt=none";

        //google dont accept automation once you want to automate your gmail login..So you should manually get the url code ..
        String partialCode = currentUrl.split("code=")[1];
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

        JsonPath js = new JsonPath(accessTokenResponse);
        String access_token = js.getString("access_token");


        GetCourse getCourse = given().queryParam("access_token", access_token)
                .expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class); //deserialization


     /*   System.out.println("getCourse.getLinkedIn() = " + getCourse.getLinkedIn());
        System.out.println("getCourse.getInstructor() = " + getCourse.getInstructor());

        System.out.println(getCourse.getCourses().getApi().get(1).getCourseTitle());*/

  /*      List<Api> apiCourses = getCourse.getCourses().getApi();

        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
                break;
            }
        }
*/

        ArrayList<String> courses=new ArrayList<>();
        List<WebAutomation> webAutomation = getCourse.getCourses().getWebAutomation();

        for (int i = 0; i < webAutomation.size() ; i++)
        {
            courses.add(webAutomation.get(i).getCourseTitle());
        }

        List<String> expectedList= Arrays.asList(courseTitles);

        Assert.assertTrue(courses.equals(expectedList));

    }
}
