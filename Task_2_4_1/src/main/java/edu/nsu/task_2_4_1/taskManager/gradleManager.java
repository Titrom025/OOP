package edu.nsu.task_2_4_1.taskManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class gradleManager {

    private static final String GIT_DIRECTORY = "gitDirectory/";

    public static boolean build(String groupName, String userName, String taskName) throws IOException {
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


    public static int[] getTestInfo(String groupName, String userName, String taskName) throws IOException {
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

        File file=new File(testDir + testFile);
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line;

        while((line = br.readLine()) != null)
        {
            if (line.contains("tests=")) {
                int testNumPos = line.indexOf("tests=");
                String testNumStringBegin = line.substring(testNumPos + 7);
                int testNumEnd = testNumStringBegin.indexOf("\"");
                String testNumString = testNumStringBegin.substring(0, testNumEnd);
                int testNum = Integer.parseInt(testNumString);
                result[0] = testNum;

                testNumPos = line.indexOf("failures=");
                testNumStringBegin = line.substring(testNumPos + 10);
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

    private static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
