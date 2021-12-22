import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderParametrizedTest {

    private List<String> color;
    private OrderClient orderClient;
    private Order order;


    public OrderParametrizedTest(List <String> color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getParams(){
        return new Object[][]{
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("")},
        };
    }

    @Before
    public void setup() {
        orderClient = new OrderClient();
        order = Order.createOrder(color);
    }

    @Test
    public void testCreateOrder(){
        int track = orderClient.successfulOrder(order);

        assertThat("Order is not created", track, notNullValue());
    }
}
