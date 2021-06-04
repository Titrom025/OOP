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

    private final FactoryStatus factoryStatus;
    private final Stock stock;
    private final List<Order> storage = new ArrayList<>();

    @Getter private long workingTimeSpent = 0;
    @Getter private long idleTimeSpent = 0;

    Deliveryman(final int workerId, final int workingTime, final int backSize,
                final Stock stock, final FactoryStatus factoryStatus) {
        this.workerId = workerId;
        this.workingTime = workingTime;
        this.storageSize = backSize;
        this.stock = stock;
        this.factoryStatus = factoryStatus;
    }

    @Getter boolean hasActiveOrders = false;

    @SneakyThrows
    @Override
    public void run() {
        long idleStartTime = 0;
        while (factoryStatus.isActive()) {
            if (idleStartTime == 0) {
                idleStartTime = new Date().getTime();
            }

            synchronized (stock) {
                while (!stock.isEmpty() && storage.size() < storageSize) {
                    hasActiveOrders = true;
                    if (idleStartTime != 0) {
                        idleTimeSpent = idleTimeSpent + (new Date().getTime()) - idleStartTime;
                        idleStartTime = 0;
                    }
                    Order order = stock.getOrder();
                    storage.add(order);
                    System.out.println("Order " + order.getId() + " - is delivering, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                    order.setStatus(OrderStatus.DELIVERING);
                }
            }

            if (storage.size() > 0) {
                long workingStartTime = new Date().getTime();
                sleep(workingTime);

                while (storage.size() > 0) {
                    Order order = storage.get(0);
                    storage.remove(order);
                    System.out.println("Order " + order.getId() + " - delivered, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                    order.setStatus(OrderStatus.COMPLETED);
                }
                workingTimeSpent = workingTimeSpent + new Date().getTime() - workingStartTime;

                if (stock.isEmpty()) {
                    hasActiveOrders = false;
                }
            }

        }
        System.out.printf("%d: %d/%d\n", workerId, idleTimeSpent, workingTimeSpent);
    }
}
