package ru.nsu.fit.titkov.orderedset;

import java.util.*;

public class OrderedSet<ElementType> {
    private final Map<ElementType, Integer> nameToIdInList = new HashMap<>();
    private final List<Element<ElementType>> elements = new ArrayList<>();
    private int time;

    OrderedSet(ElementType[] elements) {
        for (ElementType elem : elements) {
            Element<ElementType> currentElement = new Element<>(elem);
            this.elements.add(currentElement);
            nameToIdInList.put(elem, this.elements.indexOf(currentElement));
        }
    }

    /**
     * The function sets relation "left > right"
     */
    public void addRelation(ElementType left, ElementType right) {
        int leftID = nameToIdInList.get(left);
        int rightID = nameToIdInList.get(right);

        Element<ElementType> firstElem = elements.get(leftID);

        firstElem.addRelation(rightID);

        checkForCycles();
    }

    /**
     * The function set relation "leftPart[i] > rightPart[i]"
     */
    public void addRelation(ElementType[] leftPart, ElementType[] rightPart) {
        if (leftPart.length != rightPart.length) {
            throw new IllegalArgumentException("Length doesn't match");
        }

        for (int i = 0; i < leftPart.length; i++) {
            addRelation(leftPart[i], rightPart[i]);
        }
    }

    /**
     * The function find maximum elements in the set
     * @return list of maximum elements
     */
    public List<ElementType> findMax() {
        List<ElementType> maximumElements = new ArrayList<>();

        for (Element<ElementType> elem : elements) {
            if (elem.getParentVertex() == -1) {
                maximumElements.add(elem.getName());
            }
        }

        return maximumElements;
    }

    /**
     * The function sorts elements by topological order
     * @return array of elements in topological order
     */
    public ElementType[] getToposortOrder() {
        int elementsCount = elements.size();

        @SuppressWarnings("unchecked")
        ElementType[] sortedElements = (ElementType[]) new Object[elementsCount];
        for (Element<ElementType> elem : elements) {
            sortedElements[elementsCount - elem.getTime() - 1] = elem.getName();
        }

        return sortedElements;
    }

    private void checkForCycles() {
        if (startDFS()) {
            throw new IllegalStateException("There is broken transitivity in relations");
        }
    }

    /**
     * The function make preparation for dfs and starts it
     * @return true, if there is a cycle in the set relations
     */
    private boolean startDFS() {
        time = 0;
        for (Element<ElementType> elem : elements) {
            elem.setColor(VertexColor.WHITE);
            elem.setParentVertex(-1);
        }

        for (Element<ElementType> elem : elements) {
            if (elem.getColor() == VertexColor.WHITE) {
                if (dfs(elements.indexOf(elem), -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Make a dfs iteration
     * @param vertex - current vertex
     * @param parent - parent of this vertex
     * @return true, if there is a cycle in the set relations
     */
    private boolean dfs(int vertex, int parent) {
        Element<ElementType> currElem = elements.get(vertex);
        currElem.setColor(VertexColor.GRAY);
        currElem.setParentVertex(parent);

        for (int childId : currElem.getRelations()) {
            Element<ElementType> childElem = elements.get(childId);
            if (childElem.getColor() == VertexColor.BLACK) {
                childElem.setParentVertex(vertex);
            }
            if ((childElem.getColor() == VertexColor.GRAY) || (childElem.getColor() == VertexColor.WHITE && dfs(childId, vertex))) {
                return true;
            }
        }

        currElem.setColor(VertexColor.BLACK);
        currElem.setTime(time++);
        return false;
    }
}
