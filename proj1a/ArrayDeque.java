public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private void checkResize(){
        if (size == items.length) {
            resize(size * 2);
        }
        if ((double) size / items.length < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        if (nextFirst + size + 1 > items.length) {
            System.arraycopy(items, plusOne(nextFirst), newItems, 0, size - nextFirst - 1);
            System.arraycopy(items, 0, newItems, size - nextFirst - 1, nextLast);
        } else {
            System.arraycopy(items, plusOne(nextFirst), newItems, 0, size);
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        checkResize();
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size = size + 1;
    }

    public void addLast(T item) {
        checkResize();
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()){
            return null;
        }

        size = size - 1;

        int toRemoveIndex = plusOne(nextFirst);
        T toRemove = items[toRemoveIndex];
        nextFirst = toRemoveIndex;
        items[toRemoveIndex] = null;
        checkResize();
        return toRemove;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        size = size - 1;

        int toRemoveIndex = minusOne(nextLast);
        T toRemove = items[toRemoveIndex];
        nextLast = toRemoveIndex;
        items[toRemoveIndex] = null;
        checkResize();
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

//        private void resize(int capacity) {
//        int[] newItems = new int[capacity];
//        System.arraycopy(items, plusOne(nextFirst), newItems, 0, size - nextFirst - 1);
//        System.arraycopy(items, 0, newItems, size - nextFirst - 1, nextLast);
//        items = newItems;
//        nextFirst = capacity - 1;
//        nextLast = size;
//    }

//    public static void main(String[] args){
//        ArrayDeque L = new ArrayDeque();
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