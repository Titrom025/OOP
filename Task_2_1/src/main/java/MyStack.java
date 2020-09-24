import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

public class MyStack<T> implements Iterable<T> {
    private static final int DEFAULT_STACK_SIZE = 10;
    private T[] stackArray;
    private int stackSize;

    /**
     * The function initialize new stack of type "T" with capacity of "DEFAULT_STACK_SIZE"
     */
    public MyStack() {
        @SuppressWarnings("unchecked")
        T[] newStack = (T[]) new Object[DEFAULT_STACK_SIZE];
        stackArray = newStack;
        stackSize = 0;
    }

    /**
     * The function increase the stack capacity
     */
    private void resizeArray() {
        stackArray = Arrays.copyOf(stackArray, stackArray.length * 2 + 1);
    }

    /**
     * The function remove from top of the stack and return last added element
     * @return last added element from the top of the stack
     */
    T pop() {
        if (stackSize == 0) {
            throw new EmptyStackException();
        }
        return stackArray[--stackSize];
    }

    /**
     * The function add new element with value "value" to the top of the stack
     * @param value value to add to the stack
     */
    void push(T value) {
        if (stackSize == stackArray.length) {
            resizeArray();
        }
        stackArray[stackSize++] = value;
    }

    /**
     * The function returns current size of the stack
     * @return size of the stack
     */
    int size() {
        return stackSize;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    class StackIterator implements Iterator<T> {

        private int iteratorPosition = 0;

        @Override
        public boolean hasNext() {
            return !(iteratorPosition == stackSize);
        }

        @Override
        public T next() {
            return stackArray[iteratorPosition++];
        }
    }
}
