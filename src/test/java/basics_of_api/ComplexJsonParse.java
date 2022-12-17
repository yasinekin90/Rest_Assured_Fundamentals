package basics_of_api;

import files.PayLoad;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static files.PayLoad.*;
public class ComplexJsonParse  {

    public static void main(String[] args) {

/**
 * {
 * "dashboard": {
 * "purchaseAmount": 910,
 * "website": "rahulshettyacademy.com"
 * },
 * "courses": [
 * {
 * "title": "Selenium Python",
 * "price": 50,
 * "copies": 6
 * },
 * {
 * "title": "Cypress",
 * "price": 40,
 * "copies": 4
 * },
 * {
 * "title": "RPA",
 * "price": 45,
 * "copies": 10
 * }
 * ]
 * }
 */

        JsonPath jsonPath = new JsonPath(coursePrice());

        //print number of the courses
        List<Object> courses = jsonPath.getList("courses");

        int size=courses.size();
        System.out.println(courses.size());
        //print purchase amount
        System.out.println(jsonPath.getInt("dashboard.purchaseAmount"));
        //print title of the first course
        System.out.println(jsonPath.getString("courses.title[0]"));
        //print title of the 3rd course
        System.out.println(jsonPath.getString("courses.title[2]"));

        //print all course titles and their respective prices

        for (int i = 0; i <size ; i++) {
            System.out.println(jsonPath.getString("courses.title[" + i + "]"));
            System.out.println(jsonPath.getString("courses.price[" + i + "]"));
        }

           //print number of copies sold by RPA course

        for (int i = 0; i <size ; i++) {

           String courseTitles= jsonPath.getString("courses.title["+i+"]");
            if(courseTitles.equalsIgnoreCase("RPA")){
                //COPİES SOLD
                System.out.println(jsonPath.getString("courses.copies[" + i + "]"));
                break;
            }
        }



    }
}
