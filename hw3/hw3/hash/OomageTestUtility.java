package hw3.hash;

import java.util.List;
import java.util.HashMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < M; i++) {
            hashMap.put(i, 0);
        }

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            int s = hashMap.get(bucketNum);
            hashMap.put(bucketNum, s + 1);
        }

        for (int i = 0; i < M; i++) {
            int value = hashMap.get(i);
            if (value < (oomages.size() / 50.0) || value > (oomages.size() / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
