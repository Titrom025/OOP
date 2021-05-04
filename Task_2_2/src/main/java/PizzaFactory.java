//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PizzaFactory {
    private static final String JSON_NOTES_FILE = "factory.txt";

    private int ordersNumber;
    private int stockNumber;

    private volatile transient List<Order> orderList;
    private volatile transient Stock stock;
    private List<CookInfo> cookersInfo = new ArrayList<>();
    private List<DeliverymanInfo> deliverymenInfo = new ArrayList<>();

    private transient List<Cook> cookers = new ArrayList<>();
    private transient List<Deliveryman> deliverymen = new ArrayList<>();

    void initThreads() {
        stock = new Stock(stockNumber);
        orderList = new ArrayList<>();

        for (int i = 1; i <= ordersNumber; i++) {
            orderList.add(new Order(i));
            orderList.get(i - 1).setStartTime(new Date().getTime());
        }

        for (CookInfo cooker: cookersInfo) {
            Cook newCooker = new Cook(cooker.getId(), cooker.getWorkingTime(), orderList, stock);
            cookers.add(newCooker);
            newCooker.start();
        }

        for (DeliverymanInfo deliverymanInfo: deliverymenInfo) {
            Deliveryman deliveryman = new Deliveryman(deliverymanInfo.getId(), deliverymanInfo.getWorkingTime(),
                    deliverymanInfo.getStorageSize(), stock);
            deliverymen.add(deliveryman);
            deliveryman.start();
        }

    }

    private static PizzaFactory readJsonFactory() throws IOException {
        Gson gson = new Gson();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(JSON_NOTES_FILE))) {
            String fileContent = bufferedReader.readLine();
            return gson.fromJson(fileContent, PizzaFactory.class);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No json configuration");
        }
    }

    private static void saveFactoryToJson(final PizzaFactory factory) {
        Gson gson = new Gson();
        String JSON = gson.toJson(factory);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(JSON_NOTES_FILE))) {
            bufferedWriter.write(JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws IOException {
        PizzaFactory ps = new PizzaFactory();

        ps.stockNumber = 5;
        ps.ordersNumber = 20;

        List<CookInfo> cookers = new ArrayList<>();
        cookers.add(new CookInfo(1, 7000));
        cookers.add(new CookInfo(2, 8000));
        cookers.add(new CookInfo(3, 6500));
        ps.cookersInfo = cookers;

        List<DeliverymanInfo> deliverymen = new ArrayList<>();
        deliverymen.add(new DeliverymanInfo(1, 10000, 2));
        ps.deliverymenInfo = deliverymen;

        saveFactoryToJson(ps);



        PizzaFactory ls = readJsonFactory();
        ls.initThreads();
    }

}


