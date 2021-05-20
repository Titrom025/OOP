package ru.nsu.fit.titkov.orderedset;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Element<ElementType> {
    private final ElementType name;
    private final List<Integer> relations = new ArrayList<>();

    private VertexColor color;
    private int time;
    private int parentVertex;

    public void addRelation(int id) {
        this.relations.add(id);
    }
}

