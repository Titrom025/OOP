import java.util.Arrays;

public class MyStack<T> {
    static int DEFAULT_STACK_SIZE = 10;
    private T[] stackArray;
    private int stackSize;

    /**
     * The function initialize new stack of type "T" with capacity of "DEFAULT_STACK_SIZE"
     */
    public MyStack() {
        @SuppressWarnings("unchecked")
        T[] newStack = (T[]) new Object[DEFAULT_STACK_SIZE];
        this.stackArray = newStack;
        this.stackSize = 0;
    }

    /**
     * The function increase the stack capacity
     */
    private void resizeArray() {
        this.stackArray = Arrays.copyOf(stackArray, stackArray.length * 2 + 1);
    }

    /**
     * The function remove from top of the stack and return last added element
     * @return last added element from the top of the stack
     */
    T pop() {
        return stackSize > 0 ? stackArray[--stackSize] : null;
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
}
