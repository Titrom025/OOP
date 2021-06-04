package edu.nsu.task_2_4_1.taskManager.configs;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentConfig extends GroovyObjectSupport {
    private String name;
    private String url;
}
