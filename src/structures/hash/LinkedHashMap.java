package structures.hash;

import structures.list.List;

import java.util.Arrays;

/**
 *  Linked Linear List for each address of the table,
 *  that way all keys with the same address are linked in
 *  a linear list
 */
public class LinkedHashMap {

    private int tableSize;
    private LinkedHashCell[] hmLinkedTable;
    private int[] weights;


    public LinkedHashMap(int tableSize) {
        this.tableSize = tableSize;
        this.hmLinkedTable = new LinkedHashCell[tableSize];
        for (int i = 0; i < tableSize; i++)
            this.hmLinkedTable[i] = null;

        this.weights = HashMapUtil.generateWeights(tableSize);
    }



    public void add(String keyword, int line) {
        int h = HashMapUtil.h(keyword,this.weights,this.tableSize);
        if(hmLinkedTable[h] == null){
            List list = new List();
            list.add(line);
            hmLinkedTable[h] = new LinkedHashCell(keyword,list);
        } else {
            LinkedHashCell hashCell = hmLinkedTable[h];
            while (hashCell.getNext() != null && hashCell.getKeyWord().compareToIgnoreCase(keyword) != 0){
                hashCell = hashCell.getNext();
            }
            if(hashCell.getKeyWord().compareToIgnoreCase(keyword) == 0){
                hashCell.setLines(line);
            } else {
                List list2 = new List();
                list2.add(line);
                hashCell.setNext(new LinkedHashCell(keyword,list2));
            }
        }

    }



    @Override
    public String toString() {
        return "LinkedHashMap{" +
                "tableSize=" + tableSize +
                ", hmLinkedTable=" + Arrays.toString(hmLinkedTable) +
                ", weights=" + Arrays.toString(weights) +
                '}';
    }
}
