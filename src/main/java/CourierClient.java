import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient{
    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step
     public boolean successfulCreate(Courier courier){
         return given()
                 .spec(getBaseSpec())
                 .body(courier)
                 .when()
                 .post(COURIER_PATH)
                 .then()
                 .statusCode(201)
                 .extract()
                 .path("ok");
     }

    @Step
    public String unSuccessfulCreateDueToAlreadyExistLogin(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step
    public String unSuccessfulCreateWithoutLoginOrPassword(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step
     public int successfulLogin(CourierCredentials courierCredentials){
         return given()
                 .spec(getBaseSpec())
                 .body(courierCredentials)
                 .when()
                 .post(COURIER_PATH + "login")
                 .then()
                 .statusCode(200)
                 .extract()
                 .path("id");
     }

    @Step
    public String unsuccessfulLoginWithEmptyLoginOrPassword(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step
    public int unsuccessfulLoginWithoutLoginOrPassword(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .extract()
                .statusCode();
    }

    @Step
    public String unsuccessfulLoginWithNonExistentCourier(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step
    public boolean delete(int courierId){
        return given()
                 .spec(getBaseSpec())
                 .when()
                 .delete(COURIER_PATH + courierId)
                 .then()
                 .assertThat()
                 .statusCode(200)
                 .extract()
                 .path("ok");
     }
}
