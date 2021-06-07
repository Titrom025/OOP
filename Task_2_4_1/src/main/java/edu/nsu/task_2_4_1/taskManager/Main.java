package edu.nsu.task_2_4_1.taskManager;

import edu.nsu.task_2_4_1.taskManager.configs.Config;
import edu.nsu.task_2_4_1.taskManager.configs.GroupConfig;
import edu.nsu.task_2_4_1.taskManager.configs.StudentConfig;
import edu.nsu.task_2_4_1.taskManager.configs.TaskConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import java.io.File;
import java.io.IOException;

final class Main {

    private Main() {
    }

    public static void main(final String[] args) throws IOException, InterruptedException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);

        DelegatingScript script = (DelegatingScript) sh.parse(
                new File("src/main/groovy/description.groovy"));

        Config config = new Config();
        script.setDelegate(config);
        script.run();
        config.postProcess();

        GithubManager.downloadRepos(config);

            for (GroupConfig group : config.getGroups()) {
                for (TaskConfig task : group.getTasks()) {
                    System.out.println("------------------------------------------------------");
                    System.out.println("           | Lab " + task.getName() + "                          |");
                    System.out.println("           | Build | Tests | Passed | Failed | Total |");
                    for (StudentConfig student : group.getStudents()) {
                        checkTasks(task, group.getName(), student.getName());
                    }

                }
                System.out.println("------------------------------------------------------");
        }
    }

    public static void checkTasks(final TaskConfig task, final String groupName, final String userName)
            throws IOException {
        String taskName = task.getName();

        boolean isBuild = GradleManager.build(groupName, userName, taskName);

        int[] tests;
        tests = GradleManager.getTestInfo(groupName, userName, taskName);

        int points = isBuild ? task.getPoints() : 0;
        System.out.printf("%-10s |   %s   | %3d   |  %3d   | %3d    | %3d   |\n",
                userName, (isBuild ? "+" : "-"), tests[0], tests[1], tests[2], points);

    }
}
