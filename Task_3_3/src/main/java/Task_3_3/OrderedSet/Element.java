package Task_3_3.OrderedSet;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Element<ElementType> {
    @Getter private final ElementType name;
    @Getter private final List<Integer> relations = new ArrayList<>();

    @Getter @Setter private VertexColor color;
    @Getter @Setter private int time;
    @Getter @Setter private int parentVertex;

    Element(ElementType name) {
        this.name = name;
    }

    public void addRelation(int id) {
        this.relations.add(id);
    }
}

