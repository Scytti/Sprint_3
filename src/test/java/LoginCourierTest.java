import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

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

    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void testMessageWhenCourierTryToLoginWithoutLoginOrPassword(){
        String messageWithoutLogin = courierClient.unsuccessfulLoginWithoutLoginOrPassword(CourierCredentials.getCourierCredentialsWithoutLogin(courier));
        String messageWithoutPassword  = courierClient.unsuccessfulLoginWithoutLoginOrPassword(CourierCredentials.getCourierCredentialsWithoutPassword(courier));

        assertThat("Incorrect message if no login",messageWithoutLogin, is("Недостаточно данных для входа"));
        assertThat("Incorrect message if no password",messageWithoutPassword, is("Недостаточно данных для входа"));
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
