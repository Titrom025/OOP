import lombok.Getter;
import lombok.Setter;

public class Order {
    @Getter private final int id;
    @Getter @Setter private OrderStatus status = OrderStatus.ACCEPTED;
    @Getter @Setter private long startTime;

    Order(int id) {
        this.id = id;
    }


}
