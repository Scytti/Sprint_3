import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient{
    private static final String ORDER_PATH = "/api/v1/";

    @Step
    public int successfulOrder(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH + "orders")
                .then()
                .statusCode(201)
                .extract()
                .path("track");
    }
}
