package edu.nsu.task_2_4_1.taskManager.configs;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class GroupConfig extends GroovyObjectSupport {
    private String name;
    private List<StudentConfig> students;
    private List<TaskConfig> tasks;

    private void student(final Closure closure) {
        StudentConfig studentConfig = new StudentConfig();
        closure.setDelegate(studentConfig);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    private void task(final Closure closure) {
        TaskConfig taskConfig = new TaskConfig();
        closure.setDelegate(taskConfig);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
}
