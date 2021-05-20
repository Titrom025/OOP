import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Deliveryman extends Thread {
    private static final double TIME_NORMALIZE = 1000.0;
    @Getter private final int workerId;
    @Getter private final int workingTime;
    @Getter private final int storageSize;

    private FactoryStatus factoryStatus;
    private final Stock stock;
    private List<Order> storage = new ArrayList<>();

    Deliveryman(final int workerId, final int workingTime, final int backSize,
                final Stock stock, final FactoryStatus factoryStatus) {
        this.workerId = workerId;
        this.workingTime = workingTime;
        this.storageSize = backSize;
        this.stock = stock;
        this.factoryStatus = factoryStatus;
    }

    boolean hasActiveOrders() {
        return storage.size() > 0;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (factoryStatus.isActive()) {
            synchronized (stock) {
                while (!stock.isEmpty() && storage.size() < storageSize) {
                    Order order = stock.getOrder();
                    storage.add(order);
                    System.out.println("Order " + order.getId() + " - is delivering, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                    order.setStatus(OrderStatus.DELIVERING);
                }
            }

            if (storage.size() > 0) {
                sleep(workingTime);

                while (storage.size() > 0) {
                    Order order = storage.get(0);
                    storage.remove(order);
                    System.out.println("Order " + order.getId() + " - delivered, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                    order.setStatus(OrderStatus.COMPLETED);
                }
            }

        }
    }
}
