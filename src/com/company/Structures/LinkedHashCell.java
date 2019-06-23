package com.company.Structures;

public class LinkedHashCell {

    String key;
    Object item;

    public LinkedHashCell(String key, Object item) {
        this.key = key;
        this.item = item;

    }

    public boolean equals (Object object){
        LinkedHashCell cell = (LinkedHashCell) object;
        return key.equals(cell.key);
    }

}
