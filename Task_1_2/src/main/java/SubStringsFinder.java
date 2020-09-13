import java.io.*;
import java.util.Arrays;

public class SubStringsFinder {

    static long[] substringsPositions = new long[10];

    /**
     * The function finds all substrings of value "substring" in file with name "filename"
     * @param filename name of the file to check for substrings
     * @param substring value of substring to find in file
     * @return an array of position, where substrings are started
     * @throws IOException
     */
    public static long[] findSubStringInFile(String filename, String substring) throws IOException {
        File inputFile = new File(filename);

        if (!inputFile.canRead()) {
            System.out.print("Unable to open file with name: " + filename);
        }

        FileReader buffer = new FileReader(inputFile);

        int answersCount = 0;
        int letter;
        long position = 0;

        while ((letter = buffer.read()) != -1) {

            if (letter == (int) substring.charAt(0)) {
                long substringStartIndex = checkForSubstring(inputFile, position, substring);
                if (substringStartIndex >= 0) {
                    addAnswerToArray(answersCount, substringStartIndex);
                    answersCount++;
                }
            }

            position++;
        }
        return Arrays.copyOf(substringsPositions, answersCount);
    }

    /**
     * This function add element with value "answer" in array of substring position on place "onPlace".
     * Moreover the function checks if it is necessary to increase array length
     *
     * @param onPlace where to insert an element
     * @param answer  value of element to insert
     */
    private static void addAnswerToArray(int onPlace, long answer) {
        if (substringsPositions.length == onPlace) {
            substringsPositions = Arrays.copyOf(substringsPositions,
                    substringsPositions.length * 2 + 1);
        }
        substringsPositions[onPlace] = answer;
    }

    /**
     * Function checks if there is an occurrence of substring in file from position "offset"
     *
     * @param file      file to read
     * @param offset    position, from where can possible substring begins
     * @param substring text of substring to find
     * @return beginning position of founded substring or -1, if there is no substring from position offset
     * @throws IOException
     */
    private static long checkForSubstring(File file, long offset, String substring) throws IOException {

        FileReader buffer = new FileReader(file);
        buffer.skip(offset);

        int letter;

        for (int i = 0; i < substring.length(); i++) {
            letter = buffer.read();

            if (letter != (int) substring.charAt(i)) {
                return -1;
            }
        }

        return offset;
    }
}
