import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Random;
import java.util.Stack;

public class StackTest extends TestCase {

    public void testMyStack1() {
        MyStack<Integer> stackInt = new MyStack<Integer>();

        stackInt.push(111);
        stackInt.push(222);
        stackInt.push(333);

        Assert.assertEquals(3, stackInt.size());
        Assert.assertEquals(333, (long) stackInt.pop());
        Assert.assertEquals(2, stackInt.size());

        stackInt.push(444);
        stackInt.push(555);

        Assert.assertEquals(4, stackInt.size());
        Assert.assertEquals(555, (long) stackInt.pop());

        for (int i = 0; i < 10; i++) {
            stackInt.pop();
        }
        Assert.assertEquals(0, stackInt.size());
    }

    public void testMyStack2() {
        MyStack<String> myStackString = new MyStack<String>();
        Stack<String> stackString = new Stack<String>();

        myStackString.push("String1");
        myStackString.push("String2");
        myStackString.push("String3");

        stackString.push("String1");
        stackString.push("String2");
        stackString.push("String3");

        Assert.assertEquals(stackString.size(), myStackString.size());
        Assert.assertEquals(stackString.pop(), myStackString.pop());
        Assert.assertEquals(stackString.size(), myStackString.size());

        stackString.pop();
        myStackString.pop();
        stackString.pop();
        myStackString.pop();

        Assert.assertEquals(stackString.size(), myStackString.size());
    }

    public void testMyStackRandom() {
        int RANDOM_TESTS_COUNT = 5000;
        int MINIMUM_CAPACITY = 100;
        int MAXIMUM_CAPACITY = 50000;
        Random random = new Random();

        for (int i = 0; i < RANDOM_TESTS_COUNT; i++) {
            MyStack<Long> myStackLong = new MyStack<Long>();
            Stack<Long> stackLong = new Stack<Long>();

            int capacity = random.nextInt(MAXIMUM_CAPACITY - MINIMUM_CAPACITY) + MINIMUM_CAPACITY;

            for (int j = 0; j < capacity; j++) {
                long value = random.nextLong();
                stackLong.push(value);
                myStackLong.push(value);
            }

            Assert.assertEquals(stackLong.size(), myStackLong.size());

            int countOfElementsToPop = random.nextInt(capacity);

            for (int j = 0; j < countOfElementsToPop; j++) {
                long stackValue = stackLong.pop();
                long myStackValue = myStackLong.pop();
                Assert.assertEquals(stackValue, myStackValue);
            }
            Assert.assertEquals(stackLong.size(), myStackLong.size());
        }
    }

}