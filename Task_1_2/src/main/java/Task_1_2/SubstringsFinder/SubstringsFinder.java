package Task_1_2.SubstringsFinder;
import java.io.*;
import java.util.Arrays;

public class SubstringsFinder {

    private static final int SECTION_LENGTH = 100000;

    /**
     * The function finds all occurrences of substring "substring"
     * in the file with name "filename"
     *
     * @param filename  name of input file
     * @param substring content of substring
     * @return array of position of occurrences
     * @throws IOException
     */
    public static long[] findSubstringInFile(String filename, String substring) throws IOException {

        long[] answersArray = new long[2];
        int answerCount = 0;

        int substringLength = substring.length();
        int countOfcharactersRead;
        int stringNumber = 0;

        FileReader filereader = new FileReader(filename);
        BufferedReader input = new BufferedReader(filereader);

        char[] substringReplecement = substring.toCharArray();
        char[] chars = new char[SECTION_LENGTH + substringLength];

        for (int i = 0; i < substringLength; i++) {
            chars[i] = substring.charAt(i);
        }

        for (int i = 0; i < substringLength; i++) {
            char letter = substring.charAt(i);
            if (i != 0 && letter == substring.charAt(0)) {
                break;
            }
            substringReplecement[i] = (char) ((int) letter + 1);
        }

        input.read(chars, 0, substringLength);
        while ((countOfcharactersRead = input.read(chars, substringLength, SECTION_LENGTH)) != -1) {

            String string = new String(chars).substring(0, countOfcharactersRead + substringLength);

            if (countOfcharactersRead != SECTION_LENGTH) {
                string = string.substring(0, countOfcharactersRead + substringLength);
            }

            while (string.contains(substring)) {
                long answer = string.indexOf(substring) + stringNumber * SECTION_LENGTH;
                if (answerCount == answersArray.length) {
                    answersArray = Arrays.copyOf(answersArray, answersArray.length * 2 + 1);
                }
                answersArray[answerCount++] = answer;

                string = string.replaceFirst(substring, new String(substringReplecement));
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
