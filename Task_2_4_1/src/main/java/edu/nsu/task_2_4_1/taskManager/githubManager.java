package edu.nsu.task_2_4_1.taskManager;

import edu.nsu.task_2_4_1.taskManager.configs.Config;
import edu.nsu.task_2_4_1.taskManager.configs.GroupConfig;
import edu.nsu.task_2_4_1.taskManager.configs.StudentConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class githubManager {

    private static final String GIT_DIRECTORY = "gitDirectory/";

    private static void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    public static void downloadRepos(Config config) throws IOException, InterruptedException {
        deleteDirectory(Paths.get(GIT_DIRECTORY).toFile());
        Files.createDirectories(Paths.get(GIT_DIRECTORY));

        for (GroupConfig groupConfig : config.getGroups()) {
            String groupName = groupConfig.getName();
            List<StudentConfig> studentConfigList = groupConfig.getStudents();

            for (StudentConfig studentConfig : studentConfigList) {
                String repository = GIT_DIRECTORY + "/" + groupName + "/" + studentConfig.getName();
                Process process = Runtime.getRuntime().exec(
                        "git clone " + studentConfig.getUrl() + " " + repository);
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    throw new ConnectException("Unable to download repo: " + repository);
                }
            }
        }
    }


}
