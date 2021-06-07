package edu.nsu.task_2_4_1.taskManager;

import edu.nsu.task_2_4_1.taskManager.configs.Config;
import edu.nsu.task_2_4_1.taskManager.configs.GroupConfig;
import edu.nsu.task_2_4_1.taskManager.configs.StudentConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class GithubManager {
    private static final int TIMEOUT = 10;
    private static final String GIT_DIRECTORY = "gitDirectory/";

    private GithubManager() {
    }

    private static void deleteDirectory(final File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    public static void downloadRepos(final Config config) throws IOException, InterruptedException {
        deleteDirectory(Paths.get(GIT_DIRECTORY).toFile());
        Files.createDirectories(Paths.get(GIT_DIRECTORY));

        for (GroupConfig groupConfig : config.getGroups()) {
            String groupName = groupConfig.getName();
            List<StudentConfig> studentConfigList = groupConfig.getStudents();

            for (StudentConfig studentConfig : studentConfigList) {
                String repository = GIT_DIRECTORY + "/" + groupName + "/" + studentConfig.getName();
                Process process = Runtime.getRuntime().exec(
                        "git clone " + studentConfig.getUrl() + " " + repository);
                if (!process.waitFor(TIMEOUT, TimeUnit.SECONDS)) {
                    throw new ConnectException("Unable to download repo: " + repository);
                }
            }
        }
    }
}
