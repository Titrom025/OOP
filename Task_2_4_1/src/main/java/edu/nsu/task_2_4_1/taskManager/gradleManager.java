package edu.nsu.task_2_4_1.taskManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class gradleManager {

    private static final String GIT_DIRECTORY = "gitDirectory/";

    public static boolean build(String groupName, String userName, String taskName) throws IOException {
        String taskDir = GIT_DIRECTORY + groupName + "/" + userName + "/" + taskName;
        String command =  "./gradlew build";
        Process process = Runtime.getRuntime().exec(command, null, Paths.get(taskDir).toFile());

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;

        while ((s = stdIn.readLine()) != null) {
            if (!s.equals("") && Character.isDigit(s.charAt(0))) {
                String[] split = s.split(" ");
                for (String str : split) {
                    try {
                        int num = Integer.parseInt(str);
                        System.out.println(num);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

//        while ((s = stdIn.readLine()) != null) {
//            if (s.contains("SUCCESSFUL")) {
//                return true;
//            }
//        }
        return true;
    }


    public static int[] run(String groupName, String userName, String taskName) throws IOException {
        int[] result = new int[] {0, 0, 0};

        String taskDir = GIT_DIRECTORY + groupName + "/" + userName + "/" + taskName;
        String command =  "./gradlew run";
        Process process = Runtime.getRuntime().exec(command, null, Paths.get(taskDir).toFile());

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;

//        while ((s = stdInput.readLine()) != null) {
//            if (!s.equals("") && Character.isDigit(s.charAt(0))) {
//                String[] split = s.split(" ");
//                int idx = 0;
//                for (String str : split) {
//                    try {
//                        int num = Integer.parseInt(str);
//                        result[idx] = num;
//                        idx++;
//                    } catch (NumberFormatException ignored) {
//                    }
//                }
//            }
//        }
        return result;
    }
}
