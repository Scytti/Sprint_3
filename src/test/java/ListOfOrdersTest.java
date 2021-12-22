import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {
    private OrderClient orderClient;
    private ListOfOrderClient listOfOrder;

    @Before
    public void setup(){
        orderClient = new OrderClient();
        listOfOrder = new ListOfOrderClient();
        orderClient.successfulOrder(Order.createOrder(Arrays.asList("BLACK")));
    }

    //Проверь, что в тело ответа возвращаются заказы.
    @Test
    public void testCourierIsCreatedSuccessful(){
        List listOfOrders = listOfOrder.getListOfOrder();

        assertThat("List is empty", listOfOrders, notNullValue());
    }
}
