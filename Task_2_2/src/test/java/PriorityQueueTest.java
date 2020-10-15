import org.junit.Test;

import java.util.Arrays;
import java.util.stream.StreamSupport;

public class PriorityQueueTest {

    @Test
    public void testPriorityQueue1() {
        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "человек");

        System.out.println(queue.extract_max());

        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");

        System.out.println(queue.extract_max());
    }

    @Test
    public void testPriorityQueue2() {

        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "человек");
        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");

        for (Pair<Integer, String> pair : queue) {
            System.out.printf("Key: %d, value: %s\n", pair.key, pair.value);
        }
    }

    @Test
    public void testPriorityQueue3() {

        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "человек");
        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");

        Object[] values = StreamSupport.stream(queue.spliterator(), false).map((a) -> a.value).toArray();

        System.out.println(Arrays.toString(values));
    }

}

