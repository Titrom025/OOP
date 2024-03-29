package Task_1_2.SubstringsFinder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SubstringsFinder {

    private static final int SECTION_LENGTH = 100000;
    private static final int DEFAULT_ANSWER_ARRAY_SIZE = 10;

    /**
     * The function finds all occurrences of substring "substring"
     * in the file with name "filenameFromResources"
     *
     * @param file input file
     * @param substring             content of substring
     * @return array of position of occurrences
     * @throws IOException
     */
    public static long[] findSubstringInFile(File file, String substring) throws IOException {

        try (BufferedReader input = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            long[] answersArray = new long[DEFAULT_ANSWER_ARRAY_SIZE];
            int answerCount = 0;
            int substringLength = substring.length();
            int countOfcharactersRead;
            int stringNumber = 0;

            char[] chars = new char[SECTION_LENGTH + substringLength];

            for (int i = 0; i < substringLength; i++) {
                chars[i] = substring.charAt(i);
            }

            input.read(chars, 0, substringLength);
            while ((countOfcharactersRead = input.read(chars, substringLength, SECTION_LENGTH)) != -1) {

                String string = new String(chars).substring(0, countOfcharactersRead + substringLength);

                if (countOfcharactersRead != SECTION_LENGTH) {
                    string = string.substring(0, countOfcharactersRead + substringLength);
                }

                int indexOfSubstring = -1;
                while ((indexOfSubstring = string.indexOf(substring, indexOfSubstring + 1)) != -1) {
                    long answer = indexOfSubstring + stringNumber * SECTION_LENGTH;
                    if (answerCount == answersArray.length) {
                        answersArray = Arrays.copyOf(answersArray, answersArray.length * 2 + 1);
                    }
                    answersArray[answerCount++] = answer;

                }

                if (countOfcharactersRead == SECTION_LENGTH) {
                    String newString = string.substring(SECTION_LENGTH, SECTION_LENGTH + substringLength);
                    string = string.substring(0, SECTION_LENGTH);
                    newString = newString.concat(string);
                    chars = newString.toCharArray();
                }

                stringNumber++;
            }

            if (stringNumber == 0) {
                String stringFromChars = new String(chars).trim();
                if (substring.equals(stringFromChars)) {
                    answersArray[answerCount++] = 0;
                }
            }

            return Arrays.copyOf(answersArray, answerCount);
        }
    }
}
