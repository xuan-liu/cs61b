public class LinkedListDeque<T> implements Deque<T> {

    private class TNode {
        private T item;
        private TNode next;
        private TNode prev;

        public TNode(TNode p, T i, TNode n) {
            this.prev = p;
            this.item = i;
            this.next = n;
        }
    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

//    public LinkedListDeque(T item) {
//        sentinel = new TNode(null, null, null);
//        TNode first = new TNode(sentinel, item, sentinel);
//        sentinel.next = first;
//        sentinel.prev = first;
//        size = 1;
//    }

    @Override
    public void addFirst(T item) {
        /** Adds an item of type T to the front of the deque. */

        TNode first = new TNode(sentinel, item, sentinel.next);
        sentinel.next = first;
        first.next.prev = first;
        size = size + 1;
    }

    @Override
    public void addLast(T item) {
        /** Adds an item of type T to the back of the deque. */

        TNode last = new TNode(sentinel.prev, item, sentinel);
        sentinel.prev = last;
        last.prev.next = last;
        size = size + 1;
    }

    @Override
    public boolean isEmpty() {
        /** Returns true if deque is empty, false otherwise. */

        return size == 0;
    }

    @Override
    public int size() {
        /** Returns the number of items in the deque. */

        return size;
    }

    @Override
    public void printDeque() {
        /** Prints the items in the deque from first to
         * last, separated by a space. */

        TNode pointer = sentinel;
        while (pointer.next != sentinel) {
            System.out.print(pointer.next.item + " ");
            pointer = pointer.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        /** Removes and returns the item at the front of
         * the deque. If no such item exists, returns null. */

        if (isEmpty()) {
            return null;
        }

        size = size - 1;

        TNode first = sentinel.next;
        T toRemove = first.item;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        return toRemove;
    }

    @Override
    public T removeLast() {
        /** Removes and returns the item at the back of
         * the deque. If no such item exists, returns null. */

        if (isEmpty()) {
            return null;
        }

        size = size - 1;

        TNode last = sentinel.prev;
        T toRemove = last.item;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return toRemove;
    }

    @Override
    public T get(int index) {
        /** Gets the item at the given index, where 0 is the front,
         * 1 is the next item, and so forth. If no such item exists,
         * returns null. Must not alter the deque! */

        if (index < 0 || index > size - 1) {
            return null;
        }
        TNode pointer = sentinel;
        while (index >= 0) {
            index -= 1;
            pointer = pointer.next;
        }
        return pointer.item;
    }

    private T getRecursHelper(int index, TNode pointer) {
        if (index == 0) {
            return pointer.next.item;
        } else {
            return getRecursHelper(index - 1, pointer.next);
        }
    }

    public T getRecursive(int index) {
        /** Same as get, but uses recursion. */

        if (index < 0 || index > size - 1) {
            return null;
        } else {
            return getRecursHelper(index, sentinel);
        }
    }

//        public T get(int index){
//        TNode pointer = sentinel;
//        if (index <= size/2 && index >= 0){
//            for (int i = 0; i <= index; i++){
//                pointer = pointer.next;
//            }
//            return pointer.item;
//        } else if (index > size/2 && index <= size - 1) {
//            for (int i = size; i > index; i--){
//                pointer = pointer.prev;
//            }
//            return pointer.item;
//        } else {
//            return null;
//        }
//    }
}
