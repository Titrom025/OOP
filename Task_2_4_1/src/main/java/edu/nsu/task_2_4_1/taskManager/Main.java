package edu.nsu.task_2_4_1.taskManager;

import edu.nsu.task_2_4_1.taskManager.configs.Config;
import edu.nsu.task_2_4_1.taskManager.configs.GroupConfig;
import edu.nsu.task_2_4_1.taskManager.configs.TaskConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.apache.tools.ant.Task;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);

        DelegatingScript script = (DelegatingScript) sh.parse(new File("src/main/groovy/description.groovy"));

        Config config = new Config();
        script.setDelegate(config);
        script.run();
        config.postProcess();

        githubManager.downloadRepos(config);

        for (GroupConfig group : config.getGroups()) {
            checkTasks(group.getTasks(), group.getName(), "Roman");
        }

    }

    public static void checkTasks(List<TaskConfig> tasks, String groupName, String userName)
            throws IOException {
        for (TaskConfig task : tasks) {
            String taskName = task.getName();
            boolean isBuild = gradleManager.build(groupName, userName, taskName);

            int[] tests = new int[] {0, 0, 0};
//            if (isBuild) {
//                tests = gradleManager.run(groupName, userName, taskName);
//            }

            boolean passedDeadline = true;
//            parameters.setDaysPastDeadline(4);
//            if (checkDeadline(taskName, task.getDeadline())) {
//                passedDeadline = "+";
//                parameters.setDaysPastDeadline(0);
//            }
//            parameters.setPointsForTask(task.getPoints());
            int points = isBuild ? task.getPoints() : 0;
            System.out.println("-----------------------------------------------------------------");
            System.out.println(
                    "       | Lab " + taskName + "                                         |");
            System.out.println("       | Build | Tests | Passed | Up-to-date | Deadline | Total |");
            System.out.println(
                    userName
                            + "  | "
                            + (isBuild ? "+" : "-")
                            + "     | "
                            + tests[0]
                            + "     | "
                            + tests[1]
                            + "      | "
                            + tests[2]
                            + "          | "
                            + (passedDeadline ? "+" : "-")
                            + "        | "
                            + points
                            + "     |");
        }
    }



}
