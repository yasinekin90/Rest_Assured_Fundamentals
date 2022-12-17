package files;

public class PayLoad {


    public static String AddPlace(){

        return "{\n" +
                "    \"location\": {\n" +
                "        \"lat\": -38.383456,\n" +
                "        \"lng\": 33.427360\n" +
                "    },\n" +
                "    \"accuracy\": 50,\n" +
                "    \"name\": \"Jaseen Baba\",\n" +
                "    \"phone_number\": \"(+90)5556445022\",\n" +
                "    \"address\": \"29, side layout, cohen 09\",\n" +
                "    \"types\": [\n" +
                "        \"shoe park\",\n" +
                "        \"shop\"\n" +
                "    ],\n" +
                "    \"website\": \"http://google.com\",\n" +
                "    \"language\": \"Turkish-TR\"\n" +
                "}";
    }

    public static String coursePrice(){
        return "{\n" +
                "\"dashboard\": {\n" +
                "\"purchaseAmount\": 1162,\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" +
                "},\n" +
                "\"courses\": [\n" +
                "{\n" +
                "\"title\": \"Selenium Python\",\n" +
                "\"price\": 50,\n" +
                "\"copies\": 6\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"Cypress\",\n" +
                "\"price\": 40,\n" +
                "\"copies\": 4\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"RPA\",\n" +
                "\"price\": 45,\n" +
                "\"copies\": 10\n" +
                "},\n" +
                "  {\n" +
                "    \"title\": \"Appium\",\n" +
                "\"price\": 36,\n" +
                "\"copies\": 7\n" +
                "}\n" +
                "  ]\n" +
                "}";
    }
}
