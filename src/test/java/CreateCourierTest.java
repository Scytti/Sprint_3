import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private boolean isCourierCreated = true;

    @Before
    public void setup(){
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown(){if (isCourierCreated) {courierClient.delete(courierId);}}

    //курьера можно создать, запрос возвращает правильный код ответа;
    @Test
    public void testCourierIsCreatedSuccessful(){
        courierClient.successfulCreate(courier);
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));
    }

    //успешный запрос возвращает ok: true
    @Test
    public void testMessageWhenCourierIsCreatedSuccessful(){
        boolean isCreated = courierClient.successfulCreate(courier);
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Courier is not created", isCreated);
    }

    //нельзя создать двух одинаковых курьеров;
    @Test
    public void testCannotCreateIdenticalCouriers(){
        courierClient.successfulCreate(courier);
        courierClient.unSuccessfulCreateDueToAlreadyExistLogin(courier);
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));
    }

    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    @Test
    public void testMessageWhenCanNotCreateIdenticalCouriers(){
        courierClient.successfulCreate(courier);
        String isNotCreated = courierClient.unSuccessfulCreateDueToAlreadyExistLogin(courier);
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courier));

        assertThat("Problem with message", isNotCreated, is ("Этот логин уже используется"));
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    public void testCourierIsCreatedUnsuccessfulWithoutLoginOrPassword(){
        Courier courierWithoutFirstName = Courier.getRandomWithoutFirstName();

        courierClient.successfulCreate(courierWithoutFirstName);
        courierId = courierClient.successfulLogin(CourierCredentials.getCourierCredentials(courierWithoutFirstName));
    }

    //если одного из полей нет, запрос возвращает ошибку;
    @Test
    public void testMessageWhenCourierIsCreatedUnsuccessfulWithoutLoginOrPassword(){
        Courier courierWithoutLogin = Courier.getRandomWithoutLogin();
        Courier courierWithoutPassword = Courier.getRandomWithoutPassword();

        String isNotCreatedWithoutLogin = courierClient.unSuccessfulCreateWithoutLoginOrPassword(courierWithoutLogin);
        String isNotCreatedWithoutPassword = courierClient.unSuccessfulCreateWithoutLoginOrPassword(courierWithoutPassword);
        isCourierCreated = false;

        assertThat("Courier is not created", isNotCreatedWithoutLogin, is ("Недостаточно данных для создания учетной записи"));
        assertThat("Courier is not created", isNotCreatedWithoutPassword, is ("Недостаточно данных для создания учетной записи"));
    }
}
