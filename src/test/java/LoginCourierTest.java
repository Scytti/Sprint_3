import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private boolean isCourierCreated = true;

    @Before
    public void setup(){
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.successfulCreate(courier);
    }

    @After
    public void tearDown(){
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));
        if (isCourierCreated) {courierClient.delete(courierId);}
    }

    //курьер может авторизоваться, для авторизации нужно передать все обязательные поля, успешный запрос возвращает id.
    @Test
    public void testCourierIsCreatedSuccessful(){
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));

        assertThat("Cour ID is incorrect",courierId, is(not(0)));
    }

    //если не передать какое-то поле, вернется правильный кстатус код
    @Test
    public void testMessageWhenCourierTryToLoginWithoutLoginOrPassword(){
        int messageWithoutLogin = courierClient.unsuccessfulLoginWithoutLoginOrPassword(CourierCredentials.getCourierCredentialsWithoutLogin(courier));
        int messageWithoutPassword  = courierClient.unsuccessfulLoginWithoutLoginOrPassword(CourierCredentials.getCourierCredentialsWithoutPassword(courier));

        assertEquals("Incorrect message if login without login",400, messageWithoutLogin);
        assertEquals("Incorrect status code if login without password",400,messageWithoutPassword);
    }

    //если какое-то поле пустое, запрос возвращает ошибку;
    @Test
    public void testMessageWhenCourierTryToLoginWithEmptyLoginOrPassword(){
        String messageWithoutLogin = courierClient.unsuccessfulLoginWithEmptyLoginOrPassword(CourierCredentials.getCourierCredentialsWithEmptyLogin(courier));
        String messageWithoutPassword  = courierClient.unsuccessfulLoginWithEmptyLoginOrPassword(CourierCredentials.getCourierCredentialsWithEmptyPassword(courier));

        assertThat("Incorrect message if login is empty",messageWithoutLogin, is("Недостаточно данных для входа"));
        assertThat("Incorrect message if password is empty",messageWithoutPassword, is("Недостаточно данных для входа"));
    }

    //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void testMessageWhenCourierTryToLoginWithUncorrectedLoginOrPassword(){
        String messageWithChangedLogin = courierClient.unsuccessfulLoginWithNonExistentCourier(CourierCredentials.getChangedLoginCourierCredentials(courier));
        String messageWithChangedPassword  = courierClient.unsuccessfulLoginWithNonExistentCourier(CourierCredentials.getChangedPasswordCourierCredentials(courier));

        assertThat("Incorrect message if change login",messageWithChangedLogin, is("Учетная запись не найдена"));
        assertThat("Incorrect message if change password",messageWithChangedPassword, is("Учетная запись не найдена"));
    }

    // если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void testMessageWhenCourierTryToLoginWithNoneExistentCourier(){
        Courier noneExistentCourier= Courier.getRandom();
        String messageWithNonExistentCourier = courierClient.unsuccessfulLoginWithNonExistentCourier(CourierCredentials.getCourierCredentials(noneExistentCourier));

        assertThat("Incorrect message if none existent courier",messageWithNonExistentCourier, is("Учетная запись не найдена"));
    }
}
