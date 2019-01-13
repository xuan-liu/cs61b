import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentArray() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        String msg = "\n";

        for (int i = 0; i < 10; i++) {
            Integer randomNum = StdRandom.uniform(100);
            ad.addFirst(randomNum);
            sad.addFirst(randomNum);
            msg = msg + "addFirst(" + randomNum + ")\n";
        }

        for (int i = 0; i < 10; i++) {
            msg = msg + "removeFirst()\n";
            assertEquals(msg, ad.removeFirst(), sad.removeFirst());
            msg = msg + "removeLast()\n";
            assertEquals(msg, ad.removeLast(), sad.removeLast());
        }
    }
}
