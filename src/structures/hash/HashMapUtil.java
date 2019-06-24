package structures.hash;

import java.util.Random;

public class HashMapUtil {

    /**
     * Weight for each character from one key with
     * @param n characters
     * @return
     */
    public static int[] generateWeights(int n, int tableSize){
        int w[] = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            w[i] = random.nextInt(tableSize) +1;
        }
        return w;
    }

    public static int h (String key, int[] weight, int tableSize){
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum = sum + ((int) key.charAt(i)) * weight[i];
        }
        return sum % tableSize;
    }
}
