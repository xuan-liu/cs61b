import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentArray() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        String msg = "\n";

        for (int i = 0; i < 10; i++) {
            double randomNum = StdRandom.uniform();

            if (randomNum < 0.5) {
                sad.addFirst(i);
                ad.addFirst(i);
                msg = msg + "addFirst(" + i + ")\n";
            } else {
                sad.addLast(i);
                ad.addLast(i);
                msg = msg + "addLast(" + i + ")\n";
            }
        }

        for (int i = 0; i < 10; i++) {
            msg = msg + "removeFirst()\n";
            assertEquals(msg, ad.removeFirst(), sad.removeFirst());
            msg = msg + "removeLast()\n";
            assertEquals(msg, ad.removeLast(), sad.removeLast());
        }
    }
}
