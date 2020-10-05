import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

public class PriorityQueue<K extends Comparable<K>, V>{

    private Pair<K, V>[] queue = new Pair[2];
    private int elementsCount = 0;

    /**
     * This function swap two elements with indexes firstIndex and secondIndex in array
     *
     * @param array       array with elements to swap
     * @param firstIndex  index of first element
     * @param secondIndex index of second element
     */
    private void swap(Pair<K, V>[] array, int firstIndex, int secondIndex) {
        Pair<K, V> temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Sifts the element down with index X in the heap
     *
     * @param index index of element to sift down
     */
    private void siftdown(Pair<K, V>[] array, int index, int size) {
        int largest = index;
        int leftSon = index * 2 + 1;
        int rightSon = index * 2 + 2;

        if (leftSon < size && array[leftSon].key.compareTo(array[largest].key) < 0) {
            largest = leftSon;
        }

        if (rightSon < size && array[rightSon].key.compareTo(array[largest].key) < 0) {
            largest = rightSon;
        }

        if (largest != index) {
            swap(array, index, largest);
            siftdown(array, largest, size);
        }
    }

    private void siftup(int index) {
        if (index == 0) {
            return;
        }

        int parent = (index - 1) / 2;

        if (queue[index].key.compareTo(queue[parent].key) > 0) {
            swap(queue, index, parent);
            siftup(parent);
        }
    }

    public void insert(K key, V value) {
        Pair<K, V> element = new Pair<>(key, value);

        if (queue.length == elementsCount) {
            queue = Arrays.copyOf(queue, queue.length * 2);
        }

        queue[elementsCount] = element;
        siftup(elementsCount++);
    }

    public V extract_max() {

        return queue[0].value;
    }
}

