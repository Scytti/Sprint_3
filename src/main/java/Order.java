import java.util.List;

public class Order {
    private static final String FIRST_NAME = "Naruto";
    private static final String LAST_NAME = "Uchiha";
    private static final String ADDRESS = "Konoha, 142 apt.";
    private static final String METRO_STATION = "4";
    private static final String PHONE = "+7 800 355 35 35";
    private static final Number RENT_TIME = 5;
    private static final String DELIVERY_DATE = "2020-06-06";
    private static final String COMMENT = "Saske, come back to Konoha";
    public final List<String> color;

    public Order(List<String> color) {
       this.color = color;
    }

    public static Order createOrder(List<String> color) {
        return new Order(color);
    }
}
