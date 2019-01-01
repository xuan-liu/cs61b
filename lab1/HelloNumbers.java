/** Class that prints out the cumulative sum of the integers from 0 to 9.
 *  @Xuan
 */
public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int sum = 0;
        while (x < 10) {
            System.out.print(sum + " ");
            x = x + 1;
            sum += x;
        }
        System.out.println("");
    }
}