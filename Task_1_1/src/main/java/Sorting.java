public class Sorting {

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
     * Sifts the element down with index X in the heap
     *
     * @param index index of element to sift down
     */
    private static void siftdown(int[] array, int index, int size) {
        int largest = index;
        int leftSon = index * 2 + 1;
        int rightSon = index * 2 + 2;

        if (leftSon < size && array[leftSon] > array[largest]) {
            largest = leftSon;
        }

        if (rightSon < size && array[rightSon] > array[largest]) {
            largest = rightSon;
        }

        if (largest != index) {
            swap(array, index, largest);
            siftdown(array, largest, size);
        }
    }

    public static void sortArray(int[] array) {

        int elementsCount = array.length;

        for (int index = elementsCount / 2 - 1; index >= 0 ; index--) {
            siftdown(array, index, elementsCount);
        }

        for (int index = elementsCount - 1; index > 0; index--) {
            swap(array, 0, index);
            siftdown(array, 0, index);
        }
    }
}
