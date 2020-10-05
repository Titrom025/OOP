import Task_1_2.SubstringsFinder.SubstringsFinder;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SubstringsFinderTest extends TestCase {

    private static final int RANDOM_STRINGS_COUNT = 100000;
    private static final int RANDOM_STRINGS_LENGTH = 100000;
    private static final Random random = new Random();

    @Test
    public void testSubstringFinder1() {
        try {
            long[] answer = SubstringsFinder.findSubstringInFile("Test1.txt", "","пирог");
            long[] correctAnswer = new long[]{7};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubstringFinder2() {
        try {
            long[] answer = SubstringsFinder.findSubstringInFile("Test2.txt", "","пирог");
            long[] correctAnswer = new long[]{};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubstringFinder3() {
        try {
            long[] answer = SubstringsFinder.findSubstringInFile("Test3.txt", "", "findfi");
            long[] correctAnswer = new long[]{1, 5, 25};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubstringFinder4() {
        try {
            long[] answer = SubstringsFinder.findSubstringInFile("Test4.txt", "", "Pet");
            long[] correctAnswer = new long[]{576, 588, 598, 607, 637, 652, 665, 677, 687, 719, 731, 753, 770, 780, 826, 842, 857, 912};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    /**
     * This test checks SubstringFinder on big file.
     * Warning! This is a long process.
     */
    @Test
    public void testSubstringFinderForBigFiles() {
        if (!(new File("file10gb.txt").canRead())) {
            fileGenerator();
        }

        try {
            long[] answer = SubstringsFinder.findSubstringInFile("","file10gb.txt", "StringToFind");
            System.out.println(answer.length);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void fileGenerator() {
        FileOutputStream outputFile;
        try {
            outputFile = new FileOutputStream("file10gb.txt");

            for (int i = 0; i < RANDOM_STRINGS_COUNT; i++) {
                outputFile.write(getRandowStringOfLengthN(RANDOM_STRINGS_LENGTH).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandowStringOfLengthN(int n) {

        int number = random.nextInt(100);
        if (number > 95) {
            return "StringToFind";
        }

        String stringAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (stringAlphabet.length() * Math.random());
            sb.append(stringAlphabet.charAt(index));
        }

        return sb.toString();
    }
}




