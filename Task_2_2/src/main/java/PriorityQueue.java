import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class PriorityQueue<K extends Comparable<K>, V> implements Iterable<Pair<K, V>>{

    private Pair<K, V>[] queue = new Pair[2];
    private int elementsCount = 0;

    /**
     * This function swap two elements with indexes firstIndex and secondIndex in array
     *
     * @param firstIndex  index of first element
     * @param secondIndex index of second element
     */
    private void swap(int firstIndex, int secondIndex) {
        Pair<K, V> temp = queue[firstIndex];
        queue[firstIndex] = queue[secondIndex];
        queue[secondIndex] = temp;
    }

    /**
     * Sifts the element down with index X in the heap
     *
     * @param index index of element to sift down
     */
    private void siftdown(int index, int size) {
        int largest = index;
        int leftSon = index * 2 + 1;
        int rightSon = index * 2 + 2;

        if (leftSon < size && queue[leftSon].key.compareTo(queue[largest].key) > 0) {
            largest = leftSon;
        } else if (rightSon < size && queue[rightSon].key.compareTo(queue[largest].key) > 0) {
            largest = rightSon;
        }

        if (largest != index) {
            swap(index, largest);
            siftdown(largest, size);
        }
    }

    private void siftup(int index) {
        if (index == 0) {
            return;
        }

        int parent = (index - 1) / 2;

        if (queue[index].key.compareTo(queue[parent].key) > 0) {
            swap(index, parent);
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

        V maximum = queue[0].value;
        swap(0, --elementsCount);
        siftdown(0, elementsCount);

        return maximum;
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new PriorityQueueIterator();
    }

    @Override
    public void forEach(Consumer<? super Pair<K, V>> action) {

    }

    @Override
    public Spliterator<Pair<K, V>> spliterator() {
        return new PriorityQueueSpliterator<Pair<K, V>>(queue,0, elementsCount);
    }

    static class PriorityQueueSpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int maxPos; // current index, advanced on split or traversal
        private int currentPos = 0;

        PriorityQueueSpliterator(Object[] array, int currentPos, int maxPos) {
            this.array = array;
            this.currentPos = currentPos;
            this.maxPos = maxPos;
        }
        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (currentPos < maxPos) {
                action.accept((T) array[currentPos++]);
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            int middle = (maxPos + currentPos) / 2;
            if (currentPos < middle && middle < maxPos) {
                int newSpliteratorBottomLimit = currentPos;
                currentPos = middle;
                return new PriorityQueueSpliterator<T>(array, newSpliteratorBottomLimit, middle);
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return maxPos - currentPos;
        }

        @Override
        public int characteristics() {
            return SIZED | SUBSIZED | NONNULL;
        }
    }

    class PriorityQueueIterator implements Iterator<Pair<K, V>> {

        private int iteratorPosition = 0;

        @Override
        public boolean hasNext() {
            return !(iteratorPosition == elementsCount);
        }

        @Override
        public Pair<K, V> next() {
            return queue[iteratorPosition++];
        }
    }
}

