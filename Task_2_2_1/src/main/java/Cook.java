import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;

public final class Cook extends Thread {
    private static final double TIME_NORMALIZE = 1000.0;
    @Getter private final int workerId;
    @Getter private final int workingTime;
    @Getter boolean hasActiveOrder = false;
    private final FactoryStatus isFactoryActive;


    private final List<Order> orders;
    private final Stock stock;

    Cook(final int workerId, final int workingTime, final List<Order> orders,
         final Stock stock, final FactoryStatus isFactoryActive) {
        this.workerId = workerId;
        this.workingTime = workingTime;
        this.orders = orders;
        this.stock = stock;
        this.isFactoryActive = isFactoryActive;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (isFactoryActive.isActive()) {
            Order order = null;
            synchronized (orders) {
                if (!orders.isEmpty()) {
                    hasActiveOrder = true;
                    order = orders.get(0);
                    orders.remove(order);
                    System.out.println("Order " + order.getId() + " - is cooking, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                    order.setStatus(OrderStatus.COOKING);
                }
            }

            if (order != null) {
                sleep(workingTime);
                System.out.println("Order " + order.getId() + " - waiting for storage, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                order.setStatus(OrderStatus.WAITING_FOR_STOCK);
                while (true) {
                    synchronized (stock) {
                        if (stock.putOrder(order)) {
                            order.setStatus(OrderStatus.IN_STOCK);
                            System.out.println("Order " + order.getId() + " - placed in storage, time: " + (new Date().getTime() - order.getStartTime()) / TIME_NORMALIZE);
                            break;
                        }
                    }
                }
                hasActiveOrder = false;
            } else {
                break;
            }
        }
    }
}
