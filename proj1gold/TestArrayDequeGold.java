import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test(timeout = 1000)
    public void testStudentArray() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();
        String msg = "\n";
        int i = 0;

        while (true) {
            double n = StdRandom.uniform();
            if (n < 0.3) {
                sad.addFirst(i);
                ad.addFirst(i);
                msg += "addFirst(" + i + ")\n";
                assertEquals(msg, ad.get(0), sad.get(0));
            } else if (n < 0.6) {
                sad.addLast(i);
                ad.addLast(i);
                msg += "addLast(" + i + ")\n";
                assertEquals(msg, ad.get(ad.size() - 1), sad.get(ad.size() - 1));
            } else {
                if (n < 0.8 && ad.size() != 0) {
                    msg += "removeFirst()\n";
                    assertEquals(msg, ad.removeFirst(), sad.removeFirst());
                } else if (n >= 0.8 && ad.size() != 0) {
                    msg += "removeLast()\n";
                    assertEquals(msg, ad.removeLast(), sad.removeLast());
                }
            }
            i += 1;
        }
    }
}
