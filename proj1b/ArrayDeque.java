public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Returns the index immediately “before” a given index. */
    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    /** Returns the index immediately "after" a given index. */
    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }

    /** Resizes the array. */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int beginning = plusOne(nextFirst);
        int end = minusOne(nextLast);
        // When the the beginning of the Deque is actually "behind" the end of it.
        // Then we need to cut the old array into 2 pieces.
        if (beginning > end) {
            int sizeOfFirstHalf = items.length - beginning;
            int sizeOfSecondHalf = size - sizeOfFirstHalf;
            System.arraycopy(items, beginning, newItems, 0, sizeOfFirstHalf);
            System.arraycopy(items, 0, newItems, sizeOfFirstHalf, sizeOfSecondHalf);
        } else {
            System.arraycopy(items, beginning, newItems, 0, size);
        }
        nextFirst = newItems.length - 1;
        nextLast = size;
        items = newItems;
    }

    /** Check whether it should be resize */
    private void checkResize() {
        if (size == items.length) {
            resize(size * 2);
        }
        if ((double) size / items.length < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
    }

    /** Creates an empty Deque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

//    /** Create a Deque. */
//    public ArrayDeque(T x) {
//        items = (T[]) new Object[8];
//        items[0] = x;
//        size = 1;
//        nextFirst = 7;
//        nextLast = 1;
//    }

    /** Adds an item to the front of the Deque. */
    @Override
    public void addFirst(T x) {
        checkResize();
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size = size + 1;

    }

    /** Adds an item to the back of the Deque. */
    @Override
    public void addLast(T x) {
        checkResize();
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size = size + 1;
    }

    /** Check whether the Deque is empty */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the Deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the Deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Removes and returns the first item in the Deque, if no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        checkResize();
        int toRemoveIndex = plusOne(nextFirst);
        T toRemove = items[toRemoveIndex];
        items[toRemoveIndex] = null;
        size = size - 1;
        nextFirst = toRemoveIndex;
        return toRemove;
    }

    /** Removes and returns the last item in the Deque, if no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        checkResize();
        int toRemoveIndex = minusOne(nextLast);
        T toRemove = items[toRemoveIndex];
        items[toRemoveIndex] = null;
        size = size - 1;
        nextLast = toRemoveIndex;
        return toRemove;
    }

    /** Gets the item at the given index */
    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        } else if (nextFirst + index + 1 < items.length) {
            return items[nextFirst + index + 1];
        } else {
            return items[nextFirst + index + 1 - items.length];
        }
    }
}
