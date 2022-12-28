package ecommerceapitest;

import ecommerceapitest.pojo.LoginRequest;
import ecommerceapitest.pojo.LoginResponse;
import ecommerceapitest.pojo.OrderDetail;
import ecommerceapitest.pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EcommerceApiTest {
    public static void main(String[] args) {


        RequestSpecification req =
                new RequestSpecBuilder()
                        .setBaseUri("https://www.rahulshettyacademy.com")
                        .setContentType(ContentType.JSON).build();
        LoginRequest loginRequest = new LoginRequest("qa.assurance.test@gmail.com", "19901990Aa.");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();

        //AddProduct
        RequestSpecification addProductBaseReq =
                new RequestSpecBuilder()
                        .setBaseUri("https://www.rahulshettyacademy.com")
                        .addHeader("Authorization", token).build();

        RequestSpecification reqAddProduct= given().log().all().spec(addProductBaseReq)
                .param("productName", "adidasShoes")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shoes")
                .param("productPrice", "12500")
                .param("productDescription","Addias Originals")
                .param("productFor", "man")
                .multiPart("productImage", new File("C:\\Users\\user\\Desktop\\110000149135584.jpg"));

        String addProductResponse = reqAddProduct
                .when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath jsonPath=new JsonPath(addProductResponse);

        String productId = jsonPath.get("productId");

        //CreateOrder

        RequestSpecification createOrderBaseReq =
                new RequestSpecBuilder()
                        .setBaseUri("https://www.rahulshettyacademy.com")
                        .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        OrderDetail orderDetail=new OrderDetail("Turkey",productId);
        List<OrderDetail> orderDetails=new ArrayList<>();
        orderDetails.add(orderDetail);

        Orders orders=new Orders();
                orders.setOrders(orderDetails);

       RequestSpecification createOrderReq= given().log().all().spec(createOrderBaseReq).body(orders);

        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();


        //Delete Product
        RequestSpecification deleteProductBaseReq =
                new RequestSpecBuilder()
                        .setBaseUri("https://www.rahulshettyacademy.com")
                        .addHeader("Authorization", token).build();

        RequestSpecification deleteProdReq= given().log().all().spec(deleteProductBaseReq).pathParam("productId",productId);

        String deleteProductResponse = deleteProdReq.when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        JsonPath jsonPath1=new JsonPath(deleteProductResponse);

        String message = jsonPath1.getString("message");
        Assert.assertEquals(message,"Product Deleted Successfully");


    }
}
