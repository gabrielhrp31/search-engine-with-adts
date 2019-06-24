package structures.hash;

public class HashMapTable {

    private int tableSize;
    private HashMapCell tableCell[];
    private int weight[];


    public HashMapTable(int tableSize, int maxKeySize) {
        this.tableSize = tableSize;
        this.tableCell = new HashMapCell[this.tableSize];
        //initializing
        for (int i = 0; i < this.tableSize; i++) {
            this.tableCell[i] = null;
        }
        this.weight = HashMapUtil.generateWeights(maxKeySize, tableSize);
    }




    /**
     * First Open Hash strategy  : HASHING LINEAR
     * HASHING LINEAR position Hj in the table
     *  -> Hj= (h(x) +j) % M, para 1≤ j ≤ M−1.
     *
     * Transformation function
     * @param key
     * @param weight
     * @return
     */
    private int h (String key, int[] weight){
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum = sum + ((int) key.charAt(i)) * weight[i];
        }
        return sum % this.tableSize;
    }

    public Object search(String key){
        int index = this.searchIndex(key);
        // founded the key
        if(index < this.tableSize) {
            return this.tableCell[index].item;
        } else {
            // didnt found the key
            return null;
        }
    }


    public void add (String key, Object item) {

        //didnt found the key, so can be inserted
        if(this.search(key) ==  null){
            int initial = this.h(key, this.weight);
            int index = initial;
            int aux = 0;

            while (this.tableCell[index] != null
                && !this.tableCell[index].removed
                && aux < this.tableSize){
                index = (initial + (++aux)) % this.tableSize;
            }

            if(aux < this.tableSize){
                this.tableCell[index] = new HashMapCell(key, item);
            } else {
                System.out.printf("ERROR! FULL TABLE.");
            }
        } else {
            System.out.printf("Register already exists!");
        }
    }

    private int searchIndex(String key){
        int initial = this.h(key, this.weight);
        int index = initial;
        int aux = 0;

        while (this.tableCell[index] != null
                && !key.equals(this.tableCell[index].key)
                && aux < this.tableSize) {
            index =( initial +(++aux)) % this.tableSize;
        }

        //founded
        if(this.tableCell[index] != null
            && key.equals(this.tableCell[index].key)){
            return index;
        } else {
            // didnt found
            return this.tableSize;
        }
    }

}
