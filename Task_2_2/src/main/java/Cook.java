import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;

public class Cook extends Thread {
    @Getter private final int ID;
    @Getter private final int workingTime;

    private final List<Order> orders;
    private final Stock stock;

    Cook(final int id, final int workingTime, final List<Order> orders, final Stock stock) {
        this.ID = id;
        this.workingTime = workingTime;
        this.orders = orders;
        this.stock = stock;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Order order = null;
            synchronized (orders) {
                if (!orders.isEmpty()) {
                    order = orders.get(0);
                    orders.remove(order);
                    System.out.println("Order " + order.getId() + " - is cooking, time: " + (new Date().getTime() - order.getStartTime()) / 100.0);
                    order.setStatus(OrderStatus.COOKING);
                }
            }

            if (order != null) {
                sleep(workingTime);
                System.out.println("Order " + order.getId() + " - waiting for storage, time: " + (new Date().getTime() - order.getStartTime()) / 100.0);
                order.setStatus(OrderStatus.WAITING_FOR_STOCK);
                while (true) {
                    synchronized (stock) {
                        if (stock.putOrder(order)) {
                            order.setStatus(OrderStatus.IN_STOCK);
                            System.out.println("Order " + order.getId() + " - placed in storage, time: " + (new Date().getTime() - order.getStartTime()) / 100.0);
                            break;
                        }
                    }
                }
            } else {
                break;
            }

        }
    }
}
