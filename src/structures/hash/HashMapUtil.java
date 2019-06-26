package structures.hash;

import structures.list.SearchList;

import java.util.Random;

public class HashMapUtil {

    /**
     * Weight for each character from one key with
     * @param tableSize characters
     * @return
     */
    public static int[] generateWeights(int tableSize){
        int[] w = new int[tableSize];
        Random random = new Random();
        for (int i = 0; i < tableSize; i++) {
            w[i] = random.nextInt(tableSize) +1;
        }
        return w;
    }

    /**
     * Hash function Nivio Ziviane
     * @param key
     * @param weight
     * @param tableSize
     * @return
     */
    public static int h (String key, int[] weight, int tableSize){
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum = sum + ((int) key.charAt(i)) * weight[i];
        }
        return sum % tableSize;
    }

    /**
     * @param key
     * @param weight
     * @param tableSize
     * @return
     */
    public static int h2 (String key, int[] weight, int tableSize){
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum = sum + ((int) key.charAt(i)) * weight[i];
        }
        return tableSize - 2 - sum % (tableSize - 2);
    }



}
