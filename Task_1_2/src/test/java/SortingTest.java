import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;


public class SortingTest extends TestCase {

    public void testSubstringFinder1() {

        try {
            long[] answer = SubStringsFinder.findSubStringInFile("Test1.txt", "пирог");
            long[] correctAnswer = new long[]{7};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void testSubstringFinder2() {

        try {
            long[] answer = SubStringsFinder.findSubStringInFile("Test2.txt", "пирог");
            long[] correctAnswer = new long[]{};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void testSubstringFinder3() {

        try {
            long[] answer = SubStringsFinder.findSubStringInFile("Test3.txt", "find");
            long[] correctAnswer = new long[]{1, 19, 27, 34};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testSubstringFinder4() {
        try {
            long[] answer = SubStringsFinder.findSubStringInFile("Test4.txt", "Pet");
            long[] correctAnswer = new long[]{577, 589, 599, 608, 638, 653, 666, 678, 688,
                                              720, 732, 754, 771, 781, 827, 843, 858, 913};
            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}