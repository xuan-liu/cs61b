public class ArrayDeque<T> {
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
        }
        else {
            System.arraycopy(items, beginning, newItems, 0, size);
        }
        nextFirst = newItems.length - 1;
        nextLast = size;
        items = newItems;
    }

    private void checkResize(){
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

    /** Create a Deque. */
    public ArrayDeque(T x) {
        items = (T[]) new Object[8];
        items[0] = x;
        size = 1;
        nextFirst = 7;
        nextLast = 1;
    }

    /** Adds an item to the front of the Deque. */
    public void addFirst(T x) {
        checkResize();
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size = size + 1;

    }

    /** Adds an item to the back of the Deque. */
    public void addLast(T x) {
        checkResize();
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size = size + 1;
    }

    /** Check whether the Deque is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the Deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the Deque from first to last, separated by a space. */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Removes and returns the first item in the Deque, if no such item exists, returns null. */
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

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        } else if (nextFirst + index + 1 < items.length) {
            return items[nextFirst + index + 1];
        } else {
            return items[nextFirst + index + 1 - items.length];
        }
    }

//        public static void main(String[] args){
//        ArrayDeque L = new ArrayDeque();
//        L.addFirst(1);
//        L.addFirst(2);
//        L.addFirst(3);
//        L.printDeque();
//        System.out.println(L.get(1));
//        L.addLast(0);
//        System.out.println(L.removeLast());
//        L.addLast(2);
//        System.out.println(L.removeLast());
//        L.addFirst(4);
//        System.out.println(L.removeLast());
//        L.addFirst(6);
//        L.addLast(7);
//        L.addLast(9);
//        L.addFirst(11);
//        L.addFirst(12);
//        L.addLast(13);
//        L.addLast(14);
//        System.out.println(L.removeFirst());
//        L.addFirst(16);
//        L.addLast(17);
//        L.addLast(19);
//        System.out.println(L.get(4));
////        L.removeFirst();
////        L.removeLast();
////        System.out.println(L.size());
//        L.printDeque();
////        System.out.println(L.get(3));
//    }
}