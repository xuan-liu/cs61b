package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T>  extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private int plusOne(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index + 1;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = plusOne(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }

        T toRemove = rb[first];
        rb[first] = null;
        fillCount -= 1;
        first = plusOne(first);
        return toRemove;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

    private class QueueIterator implements Iterator<T> {
        private int index;
        public QueueIterator() {
            index = 0;
        }
        public boolean hasNext() {
            return index < fillCount();
        }

        public T next() {
            T returnItem = rb[index];
            index += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }
}
