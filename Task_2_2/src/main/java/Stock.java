import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class Stock {
    private final int size;
    private final List<Order> storage;

    Stock(int size) {
        this.size = size;
        storage = new ArrayList<>();
    }

    boolean isEmpty() {
        return storage.size() == 0;
    }

    boolean isNotFull() { return storage.size() < size; }

    Order getOrder() {
        if (!isEmpty()) {
            Order order = storage.get(0);
            storage.remove(0);
            return order;
        } else {
            throw new EmptyStackException();
        }

    }

    /**
     * @param order Order to put in the storage
     * @return true if the order can be put in the storage (storage is not full)
     *         false - otherwise
     */
    boolean putOrder(final Order order) {
        if (isNotFull()) {
            storage.add(order);
            return true;
        }

        return false;
    }
}
