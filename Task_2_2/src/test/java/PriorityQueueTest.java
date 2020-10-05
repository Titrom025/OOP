import org.junit.Test;

public class PriorityQueueTest {

    @Test
    public void testPriorityQueue1() {

        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "111");
        System.out.println(queue.extract_max());
        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");
        System.out.println(queue.extract_max());
    }

}

