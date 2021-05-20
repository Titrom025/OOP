import Task_2_2.PriorityQueue.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PriorityQueueTest {

    private static final int MAX_RANDOM_COUNT = 100000;
    private static final int MAX_RANDOM_KEY = 10000;
    private static final int MAX_RANDOM_VALUE = 1000000;

    @Test
    public void testPriorityQueue1() {
        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "человек");

        Pair<Integer, String> answer = queue.extract_max();
        Assert.assertEquals(200, (long)answer.key);
        Assert.assertEquals("собака", answer.value);

        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");

        answer = queue.extract_max();
        Assert.assertEquals(500, (long)answer.key);
        Assert.assertEquals("попугай", answer.value);
    }

    @Test
    public void testPriorityQueue2() {

        PriorityQueue<Integer, String> queue = new PriorityQueue<>();

        queue.insert(200, "собака");
        queue.insert(10, "человек");
        queue.insert(5, "пингвин");
        queue.insert(500, "попугай");

        for (Pair<Integer, String> pair : queue) {
            Pair<Integer, String> max = queue.extract_max();
            Assert.assertEquals(pair.key, max.key);
            Assert.assertEquals(pair.value, max.value);
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

    @Test
    public void testPriorityQueue4() {
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();

        Random random = new Random();

        int elemensCount = random.nextInt(MAX_RANDOM_COUNT);

        for (int i = 0; i < elemensCount; i++) {
            int key = random.nextInt(MAX_RANDOM_KEY);
            int value = random.nextInt(MAX_RANDOM_VALUE);
            queue.insert(key, value);
        }

        int lastIndex = queue.extract_max().key;
        for (int i = 0; i < elemensCount - 1; i++) {
            Pair<Integer, Integer> elem = queue.extract_max();
            Assert.assertTrue(lastIndex >= elem.key);
        }
    }

    @Test
    public void testPriorityQueue5() {
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();

        Random random = new Random();

        int elemensCount = random.nextInt(MAX_RANDOM_COUNT);

        for (int i = 0; i < elemensCount; i++) {
            int key = random.nextInt(MAX_RANDOM_KEY);
            int value = random.nextInt(MAX_RANDOM_VALUE);
            queue.insert(key, value);
        }

        List<Pair<Integer, Integer>> filetered =
                StreamSupport.stream(queue.spliterator(), false)
                .filter(a -> a.key > 10 && a.key < 100)
                .collect(Collectors.toList());

        for (Pair<Integer, Integer> pair : filetered) {
            Assert.assertTrue(pair.key > 10 && pair.key < 100);
        }
    }

}

