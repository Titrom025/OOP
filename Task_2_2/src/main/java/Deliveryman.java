import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Deliveryman extends Thread {
    @Getter private final int ID;
    @Getter private final int workingTime;
    @Getter private final int storageSize;

    FactoryStatus isFactoryActive;
    private final Stock stock;
    private List<Order> storage = new ArrayList<>();

    Deliveryman(final int ID, final int workingTime, final int backSize,
                final Stock stock, final FactoryStatus isFactoryActive) {
        this.ID = ID;
        this.workingTime = workingTime;
        this.storageSize = backSize;
        this.stock = stock;
        this.isFactoryActive = isFactoryActive;
    }

    boolean hasActiveOrders() {
        return storage.size() > 0;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (isFactoryActive.isActive()) {
            synchronized (stock) {
                while (!stock.isEmpty() && storage.size() < storageSize) {
                    Order order = stock.getOrder();
                    storage.add(order);
                    System.out.println("Order " + order.getId() + " - is delivering, time: " + (new Date().getTime() - order.getStartTime()) / 1000.0);
                    order.setStatus(OrderStatus.DELIVERING);
                }
            }

            if (storage.size() > 0) {
                sleep(workingTime);

                while (storage.size() > 0) {
                    Order order = storage.get(0);
                    storage.remove(order);
                    System.out.println("Order " + order.getId() + " - delivered, time: " + (new Date().getTime() - order.getStartTime()) / 1000.0);
                    order.setStatus(OrderStatus.COMPLETED);
                }
            }

        }
    }
}
