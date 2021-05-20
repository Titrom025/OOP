import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;

public class SortingTest extends TestCase {

    public void testSortArray1() {
        int[] arr = new int[]{13,15,9,-5,8,6,0,3,53,63,7,2,1,5,7,3};
        int[] answer = arr.clone();

        Arrays.sort(answer);
        Sorting.sortArray(arr);

        Assert.assertArrayEquals(arr, answer);
    }

    public void testSortArray2() {
        int[] arr = new int[]{-1123,-13,23,-131,-213,-4343,-3422,13,1231,42};
        int[] answer = arr.clone();

        Arrays.sort(answer);
        Sorting.sortArray(arr);

        Assert.assertArrayEquals(arr, answer);
    }

    public void testSortArrayRandom() {

        Random random = new Random();

        int numberOfTest = 1000;
        int maxArrayLength = 2000;
        int maxElementValue = 1000000;

        for (int i = 0; i < numberOfTest; i++) {

            int arrayLength = random.nextInt(maxArrayLength);

            int[] arrayToSort = new int[arrayLength];

            for (int j = 0; j < arrayLength; j++) {
                arrayToSort[j] = random.nextInt(maxElementValue) - maxElementValue / 2;
            }

            int[] arraySorted = arrayToSort.clone();

            Arrays.sort(arraySorted);
            Sorting.sortArray(arrayToSort);

            Assert.assertArrayEquals(arraySorted, arrayToSort);
        }
    }
}