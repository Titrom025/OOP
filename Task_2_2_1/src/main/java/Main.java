import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
//        PizzaFactory pizzaFactory = new PizzaFactory();
        PizzaFactory pf = PizzaFactory.readJsonFactory();
        pf.initThreads();
        pf.checkForEnd();
    }
}
