import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static java.lang.StrictMath.sqrt;

public class CheckingForPrime {

    static Boolean isNotPrime(int number) {
        for (int i = 2; i <= sqrt(number); ++i) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    static Boolean consistentProcess(List<Integer> list) {
        long startTime = System.nanoTime();

        boolean hasNonPrime = false;
        for (int j : list) {
            if (isNotPrime(j)) {
                hasNonPrime = true;
                break;
            }
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.println(hasNonPrime);
        System.out.print("Consistent: ");
        System.out.println(duration / 1000000 + " ms");
        return hasNonPrime;
    }

    static Boolean parallelProcess(List<Integer> list, int threads) throws Exception {
        long startTime = System.nanoTime();

        ExecutorService es = Executors.newFixedThreadPool(threads);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threads; i++) {
            int finalI = i;
            tasks.add(() -> {
                List<Integer> sublist = list.subList((list.size()/threads)* finalI, (list.size()/threads)*(finalI + 1) - 1);
                for (int num : sublist) {
                    if (latch.getCount() == 0) {
                        return false;
                    }

                   if (isNotPrime(num)) {
                       latch.countDown();
                       return true;
                   }
                }
                return false;
            });
        }
        List<Future<Boolean>> listResult = es.invokeAll(tasks);
        es.shutdown();

        boolean hasNonPrime = false;
        for (Future<Boolean> x : listResult) {
            if (x.get()) {
                hasNonPrime = true;
                break;
            }
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.print("Parallel with " + threads + " thread: ");
        System.out.println(duration / 1000000 + " ms");
        return hasNonPrime;
    }

    static Boolean parallelStream(List<Integer> array) {
        long startTime = System.nanoTime();

        Boolean hasNonPrime = array.parallelStream().anyMatch(CheckingForPrime::isNotPrime);

        long endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.print("ParallelStream: ");
        System.out.println(duration / 1000000 + " ms");
        return hasNonPrime;
    }
}