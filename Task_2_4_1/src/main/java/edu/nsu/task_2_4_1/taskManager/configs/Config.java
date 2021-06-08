package edu.nsu.task_2_4_1.taskManager.configs;

import groovy.lang.Closure;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public final class Config {
    private final List<GroupConfig> groups = new ArrayList<>();

    private void group(final Closure closure) {
        GroupConfig config = new GroupConfig();
        closure.setDelegate(config);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        groups.add(config);
    }

    public void postProcess() {
        for (int i = 0; i < groups.size(); i++) {
            GroupConfig groupConfig = groups.get(i);
            List<StudentConfig> students = new ArrayList<>();
            List<TaskConfig> tasks = new ArrayList<>();

            for (Object studentConfig : groupConfig.getStudents()) {
                StudentConfig item = new StudentConfig();
                ((Closure) studentConfig).setDelegate(item);
                ((Closure) studentConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
                ((Closure) studentConfig).call();
                students.add(item);
            }

            for (Object taskConfig : groupConfig.getTasks()) {
                TaskConfig item = new TaskConfig();
                ((Closure) taskConfig).setDelegate(item);
                ((Closure) taskConfig).setResolveStrategy(Closure.DELEGATE_FIRST);
                ((Closure) taskConfig).call();

                tasks.add(item);
            }

            GroupConfig groupConfig1 = new GroupConfig();
            groupConfig1.setName(groupConfig.getName());
            groupConfig1.setStudents(students);
            groupConfig1.setTasks(tasks);
            groups.set(i, groupConfig1);
        }
    }
}
