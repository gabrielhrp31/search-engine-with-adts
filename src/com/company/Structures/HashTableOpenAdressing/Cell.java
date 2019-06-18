package com.company.Structures.HashTableOpenAdressing;

/*
    Represents one cell from the table
 */
public class Cell {

    String key;
    Object item;
    boolean removed;

    // initialize
    public Cell(String key, Object item) {
        this.key = key;
        this.item = item;
        this.removed = false;
    }

    public boolean equal (Object object) {
        return key.equals(((Cell)object).key);
    }


}
