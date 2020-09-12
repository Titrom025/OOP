import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;

import static java.lang.System.out;

public class SortingTest extends TestCase {

    public void testSortArray1() {
        int[] arr = new int[]{13,15,9,-5,8,6,0,3,53,63,7,2,1,5,7,2};
        int[] answer = arr.clone();

        out.println("Input: " + Arrays.toString(arr));

        Arrays.sort(answer);
        Sorting.sortArray(arr);

        out.println("Output: " + Arrays.toString(arr));

        Assert.assertArrayEquals(arr, answer);
    }

    public void testSortArray2() {
        int[] arr = new int[]{-1123,-13,23,-131,-213,-4343,-3422,13,1231,42};
        int[] answer = arr.clone();

        out.println("Input: " + Arrays.toString(arr));

        Arrays.sort(answer);
        Sorting.sortArray(arr);

        out.println("Output: " + Arrays.toString(arr));

        Assert.assertArrayEquals(arr, answer);
    }

    public void testSortArrayRandom() {

        Random random = new Random();

        for (int i = 0; i < 500; i++) {

            int arrayLength = random.nextInt(490) + 10;

            int[] arrayToSort = new int[arrayLength];

            for (int j = 0; j < arrayLength; j++) {
                arrayToSort[j] = random.nextInt(200000) - 100000;
            }

            int[] arraySorted = arrayToSort.clone();

            out.println("Input: " + Arrays.toString(arrayToSort));

            Arrays.sort(arraySorted);
            Sorting.sortArray(arrayToSort);

            out.println("Output: " + Arrays.toString(arraySorted));

            Assert.assertArrayEquals(arraySorted, arrayToSort);

            System.gc();
        }
    }
}