package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, arb.peek());
        arb.dequeue();
        arb.dequeue();
        assertFalse(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertEquals(3, arb.peek());
        assertTrue(arb.isFull());

        try {
            arb.enqueue(5);
        } catch (Exception e) {
            System.out.println("occur err: " + e);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.dequeue();
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(3);
        arb.enqueue(4);

        Iterator<Integer> it = arb.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
} 
