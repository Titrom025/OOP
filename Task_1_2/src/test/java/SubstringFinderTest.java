import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class SubstringFinderTest extends TestCase {

    public void testSubstringFinder1() {

        try {
            long[] answer = SubStringsFinder.findSubstringInFile("Test1.txt", "пирог");
            long[] correctAnswer = new long[]{7};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testSubstringFinder2() {

        try {
            long[] answer = SubStringsFinder.findSubstringInFile("Test2.txt", "пирог");
            long[] correctAnswer = new long[]{};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void testSubstringFinder3() {

        try {
            long[] answer = SubStringsFinder.findSubstringInFile("Test3.txt", "findfi");
            long[] correctAnswer = new long[]{1, 5, 25};

            Assert.assertArrayEquals(correctAnswer, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testSubstringFinder4() {
        try {
            long[] answer = SubStringsFinder.findSubstringInFile("Test4.txt", "Pet");
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
    public void testSubstringFinderForBigFiles() {

        if (!(new File("file10gb.txt").canRead())) {
            fileGenerator();
        }

        try {
            long[] answer = SubStringsFinder.findSubstringInFile("file10gb.txt", "StringToFind");

            System.out.println(Arrays.toString(answer));
            System.out.println(answer.length);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }


    /**
     * The function generates a file ~ 10 GB
     */
    public void fileGenerator() {

        FileOutputStream outputFile;
        try {
            outputFile = new FileOutputStream("file10gb.txt");

            for (int i = 0; i < 100000; i++) {
                outputFile.write(getRandowStringOfLengthN(100000).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Random random = new Random();

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




