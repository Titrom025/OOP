import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class CheckingForPrimeTest extends TestCase {

    private void startTest(List<Integer> list) {
        try {
            CheckingForPrime.consistentProcess(list);
            CheckingForPrime.parallelProcess(list, 1);
            CheckingForPrime.parallelProcess(list, 2);
            CheckingForPrime.parallelProcess(list, 3);
            CheckingForPrime.parallelProcess(list, 4);
            CheckingForPrime.parallelProcess(list, 5);
            CheckingForPrime.parallelProcess(list, 6);
            CheckingForPrime.parallelProcess(list, 7);
            CheckingForPrime.parallelProcess(list, 8);
            CheckingForPrime.parallelProcess(list, 16);
            CheckingForPrime.parallelProcess(list, 32);
            CheckingForPrime.parallelProcess(list, 64);
            CheckingForPrime.parallelProcess(list, 128);
            CheckingForPrime.parallelProcess(list, 256);
            CheckingForPrime.parallelProcess(list, 512);
            CheckingForPrime.parallelProcess(list, 1024);
            CheckingForPrime.parallelStream(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testMain() {
        Random rd = new Random();
        rd.setSeed(1238901237);
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 150000; i++) {
            int number = abs(rd.nextInt());
            if (!CheckingForPrime.isNotPrime(number)) {
                list.add(number);
            }
        }

        System.out.println(list.size());
        startTest(list);
    }

    public void test2() {
        Random rd = new Random();
        rd.setSeed(1238901237);
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 2000; i++) {
            int number = abs(rd.nextInt());
                list.add(number);
        }

        System.out.println(list.size());
        startTest(list);
    }

    public void test3() {
        Random rd = new Random();
        rd.setSeed(1238923437);
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 200000; i++) {
            int number = abs(rd.nextInt());
            if (!CheckingForPrime.isNotPrime(number)) {
                list.add(number);
            }
        }

        list.add(0, 25);
        System.out.println(list.size());
        startTest(list);
    }
}
