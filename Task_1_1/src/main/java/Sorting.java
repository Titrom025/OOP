import java.util.Arrays;

public class Sorting {

    private static int[] heap = new int[4];
    private static int k = 0;

    /**
     * This function swap two elements with indexes firstIndex and secondIndex in array
     *
     * @param array       array with elements to swap
     * @param firstIndex  index of first element
     * @param secondIndex index of second element
     */
    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Sifts the element up with index X in the heap
     *
     * @param x index of element to sift up
     */
    private static void siftup(int x) {
        if (x == 0) {
            return;
        }

        int parent = (x - 1) / 2;

        if (heap[x] < heap[parent]) {
            swap(heap, x, parent);
            siftup(parent);
        }
    }

    /**
     * Sifts the element down with index X in the heap
     *
     * @param x index of element to sift down
     */
    private static void siftdown(int x) {
        int a = x * 2 + 1;
        int b = x * 2 + 2;

        if (a < k && b < k) {
            if (heap[a] < heap[b]) {
                if (heap[a] < heap[x]) {
                    swap(heap, x, a);
                    siftdown(a);
                }
            } else {
                if (heap[b] < heap[x]) {
                    swap(heap, x, b);
                    siftdown(b);
                }
            }
        } else if (a < k) {
            if (heap[a] < heap[x]) {
                swap(heap, x, a);
                siftdown(a);
            }
        }
    }

    /**
     * The method used for adding new element with value x in the heap
     *
     * @param x the value to add in the heap
     */
    private static void addElement(int x) {
        if (k == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2 + 1);
        }
        heap[k++] = x;
        siftup(k - 1);
    }

    /**
     * Yhe metod returns minimum value
     * from heap and starts siftdown from zero element
     *
     * @return minimum value from heap
     */
    private static int extractMinimum() {
        int minimum = heap[0];
        swap(heap, 0, --k);
        siftdown(0);
        return minimum;
    }

    public static void sortArray(int[] array) {

        int elementCount = array.length;

        for (int number : array) {
            addElement(number);
        }

        for (int i = 0; i < elementCount; i++) {
            array[i] = extractMinimum();
        }
    }
}
