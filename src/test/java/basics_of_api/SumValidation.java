package basics_of_api;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import static files.PayLoad.coursePrice;

public class SumValidation {

    @Test
    public void sumOfCourses(){
        JsonPath jsonPath = new JsonPath(coursePrice());

        int sum=0;
        int size=jsonPath.getInt("courses.size()");

        for (int i = 0; i <size ; i++) {
          int price=jsonPath.get("courses["+i+"].price");
          int copies=jsonPath.get("courses["+i+"].copies");
          int amount=price*copies;
          sum=sum+amount;

        }

        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");

        Assert.assertEquals(sum,purchaseAmount);
    }

}
