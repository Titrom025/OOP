import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class PizzaFactory {
    private static final String JSON_NOTES_FILE = "factory.txt";
    private static final int TIMEOUT = 100;

    int ordersNumber;
    int stockNumber;

    private static transient volatile List<Order> orderList;
    private final List<CookInfo> cookersInfo = new ArrayList<>();
    private final List<DeliverymanInfo> deliverymenInfo = new ArrayList<>();

    private transient Stock stock = new Stock(stockNumber);
    private static final transient List<Cook> COOKERS = new ArrayList<>();
    private static final transient List<Deliveryman> DELIVERYMEN = new ArrayList<>();
    private static final transient FactoryStatus FACTORY_STATUS = new FactoryStatus();

    void initThreads() {
        orderList = new ArrayList<>();

        for (int i = 1; i <= ordersNumber; i++) {
            orderList.add(new Order(i));
            orderList.get(i - 1).setStartTime(new Date().getTime());
        }

        for (CookInfo cooker: cookersInfo) {
            Cook newCooker = new Cook(cooker.getId(), cooker.getWorkingTime(), orderList, stock, FACTORY_STATUS);
            COOKERS.add(newCooker);
            newCooker.start();
        }

        for (DeliverymanInfo deliverymanInfo: deliverymenInfo) {
            Deliveryman deliveryman = new Deliveryman(deliverymanInfo.getId(), deliverymanInfo.getWorkingTime(),
                    deliverymanInfo.getStorageSize(), stock, FACTORY_STATUS);
            DELIVERYMEN.add(deliveryman);
            deliveryman.start();
        }

    }

    void checkForEnd() {
        while (true) {
            boolean cookersHasOrders = false;
            boolean deliveryHasOrders = false;

            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Cook cook: COOKERS) {
                if (cook.isHasActiveOrder()) {
                    cookersHasOrders = true;
                    break;
                }
            }

            for (Deliveryman deliveryman: DELIVERYMEN) {
                if (deliveryman.hasActiveOrders) {
                    deliveryHasOrders = true;
                    break;
                }
            }

            if (orderList.size() == 0 && stock.isEmpty() && !cookersHasOrders && !deliveryHasOrders) {
                FACTORY_STATUS.setActive(false);
                break;
            }
        }
    }

    static PizzaFactory readJsonFactory() throws IOException {
        Gson gson = new Gson();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(JSON_NOTES_FILE))) {
            String fileContent = bufferedReader.readLine();
            PizzaFactory pf = gson.fromJson(fileContent, PizzaFactory.class);
            pf.stock = new Stock(pf.stockNumber);
            return pf;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No json configuration");
        }
    }
}


