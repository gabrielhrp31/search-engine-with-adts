package com.company;
import java.util.List;
import java.util.Vector;

public class Cell {
    Cell next, prev;
    List  lines;
    String word;

    Cell(){
        this.next=null;
        this.prev=null;
        lines = new Vector();
    }
}
