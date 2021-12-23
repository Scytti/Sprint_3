import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ListOfOrderClient extends RestAssuredClient{
    private static final String ORDER_PATH = "/api/v1/";

    @Step
    public List getListOfOrder(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "orders")
                .then()
                .statusCode(200)
                .extract()
                .path("orders");
    }
}
