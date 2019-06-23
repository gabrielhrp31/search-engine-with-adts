package com.company.Structures;

/**
 *  Linked Linear List for each address of the table,
 *  that way all keys with the same address are linked in
 *  a linear list
 */
public class LinkedHashMap {

    private int tableSize;
    private SelfReferenceList[] hmLinkedTable;
    private int weights[];


    public LinkedHashMap(int tableSize, int maxKeySize) {
        this.tableSize = tableSize;
        this.hmLinkedTable = new SelfReferenceList[tableSize];
        for (int i = 0; i < tableSize; i++)
            this.hmLinkedTable[i] = new SelfReferenceList();

        this.weights = HashMapUtil.generateWeights(maxKeySize, tableSize);


    }


    public Object search(String key) {
        int i = HashMapUtil.h(key, this.weights, tableSize);
        if (this.hmLinkedTable[i].empty()){
            //didnt found
            return null;
        } else {
            HashMapCell cell = (HashMapCell) this.hmLinkedTable[i].search(new HashMapCell(key,null));
            if(cell == null){
                // didnt found
                return null;
            } else {
                // founded
                return cell.item;
            }
        }

    }



    public void add(String key, Object item) {

        if(this.search(key) == null){
            int i = HashMapUtil.h(key,this.weights,this.tableSize);
            this.hmLinkedTable[i].add(new HashMapCell(key, item));
        } else {
            System.out.println("Register already exists!");
        }

    }



    public void remove(String key) throws Exception {

        int i = HashMapUtil.h(key,weights,this.tableSize);
        HashMapCell cell = (HashMapCell) this.hmLinkedTable[i].remove(new HashMapCell(key,null));
        if(cell == null) {
            System.out.println("Register not found!");
        }
    }
}
