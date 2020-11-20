package Task_3_3.OrderedSet;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderedSetTest {

    @Test
    public void OrderedSetTest1() {
        OrderedSet<String> set = new OrderedSet<>(new String[] {"Мария", "Вероника", "Василий", "Татьяна", "Дмитрий"});

        set.addRelation("Мария", "Василий");
        set.addRelation("Вероника", "Василий");
        set.addRelation("Василий", "Татьяна");

        List<String> answer = new ArrayList<>();
        answer.add("Мария");
        answer.add("Вероника");
        answer.add("Дмитрий");
        Assert.assertEquals(answer, set.findMax());
    }

    @Test
    public void OrderedSetTest2() {
        OrderedSet<String> set = new OrderedSet<>(new String[] {"Мария", "Вероника", "Василий", "Татьяна", "Дмитрий"});

        set.addRelation(new String[] {"Мария", "Вероника", "Василий"}, new String[]{"Василий", "Василий", "Татьяна"});

        List<String> answer = new ArrayList<>();
        answer.add("Мария");
        answer.add("Вероника");
        answer.add("Дмитрий");
        Assert.assertEquals(answer, set.findMax());
    }

    @Test
    public void OrderedSetTest3() {
        OrderedSet<Integer> set = new OrderedSet<>(new Integer[] {1, 2, 3, 4, 5, 6, 7});

        set.addRelation(1, 2);
        set.addRelation(2, 3);
        set.addRelation(3, 7);
        set.addRelation(4, 5);
        set.addRelation(5, 3);
        set.addRelation(6, 7);

        Integer[] answer = new Integer[]{6, 4, 5, 1, 2, 3, 7};
        Assert.assertArrayEquals(answer, set.getToposortOrder());
    }

    @Test
    public void OrderedSetTest4() {
        OrderedSet<Integer> set = new OrderedSet<>(new Integer[] {1, 2, 3, 4, 5, 6, 7});

        set.addRelation(1, 2);
        set.addRelation(2, 3);
        set.addRelation(3, 4);
        set.addRelation(4, 5);
        set.addRelation(5, 6);
        set.addRelation(6, 7);

        try {
            set.addRelation(7, 3);
        } catch (IllegalStateException error) {
            System.out.println("The transitivity error occurred");
        }
    }

    @Test
    public void OrderedSetTest5() {
        OrderedSet<Integer> set = new OrderedSet<>(new Integer[] {1, 2, 3, 4, 5, 6, 7});

        set.addRelation(3, 2);
        set.addRelation(2, 1);
        set.addRelation(4, 3);
        set.addRelation(5, 4);
        set.addRelation(6, 5);
        set.addRelation(7, 6);

        Integer[] answer = new Integer[]{7};
        Assert.assertArrayEquals(answer, set.findMax().toArray());
    }
}