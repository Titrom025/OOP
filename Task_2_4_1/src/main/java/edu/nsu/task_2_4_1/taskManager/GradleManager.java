package edu.nsu.task_2_4_1.taskManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GradleManager {

    private static final int TESTS_OFFSET = 7;
    private static final int FAILURES_OFFSET = 10;
    private static final String GIT_DIRECTORY = "gitDirectory/";

    private GradleManager() {
    }

    public static boolean build(final String groupName, final String userName,
                                final String taskName) throws IOException {
        String taskDir = GIT_DIRECTORY + groupName + "/" + userName + "/" + taskName;
        String command =  "./gradlew build";

        if (!Files.exists(Paths.get(taskDir))) {
            return false;
        }

        Process process = Runtime.getRuntime().exec(command, null, Paths.get(taskDir).toFile());

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdIn.readLine()) != null) {
            if (s.contains("SUCCESSFUL")) {
                return true;
            }
        }

        return false;
    }


    public static int[] getTestInfo(final String groupName, final String userName,
                                    final String taskName) throws IOException {
        int[] result = new int[] {0, 0, 0};

        String testDir = GIT_DIRECTORY + groupName + "/" + userName + "/" + taskName + "/build/test-results/test/";
        if (!Files.exists(Paths.get(testDir))) {
            return result;
        }

        Set<String> files = listFilesUsingJavaIO(testDir);

        String testFile = "";

        for (String file : files) {
            if (file.contains("TEST")) {
                testFile = file;
                break;
            }
        }

        if (testFile.equals("")) {
            return result;
        }

        File file = new File(testDir + testFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("tests=")) {
                int testNumPos = line.indexOf("tests=");
                String testNumStringBegin = line.substring(testNumPos + TESTS_OFFSET);
                int testNumEnd = testNumStringBegin.indexOf("\"");
                String testNumString = testNumStringBegin.substring(0, testNumEnd);
                int testNum = Integer.parseInt(testNumString);
                result[0] = testNum;

                testNumPos = line.indexOf("failures=");
                testNumStringBegin = line.substring(testNumPos + FAILURES_OFFSET);
                testNumEnd = testNumStringBegin.indexOf("\"");
                testNumString = testNumStringBegin.substring(0, testNumEnd);
                testNum = Integer.parseInt(testNumString);
                result[2] = testNum;
                break;
            }
        }
        br.close();

        result[1] = result[0] - result[2];
        return result;
    }

    private static Set<String> listFilesUsingJavaIO(final String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
