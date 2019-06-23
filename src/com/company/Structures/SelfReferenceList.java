package com.company.Structures;

public class SelfReferenceList {

    private static class Cell {
        Object item;
        Cell next;
    }

    private Cell first, last, position;

    // empty list
    public SelfReferenceList(){
        this.first = new Cell();
        this.position = this.first;
        this.last = this.first;
        this.first.next = null;
    }

    public Object search (Object key){
        if(this.empty() || key == null){
            return null;
        }
        Cell aux = this.first;
        while (aux.next != null){
            if(aux.next.item.equals(key)){
                return aux.next.item;
            }
            aux = aux.next;
        }
        return null;
    }

    public void add(Object object){
        this.last.next = new Cell();
        this.last = this.last.next;
        this.last.item = object;
        this.last.next = null;
    }

    public Object remove(Object key) throws Exception {
        if(this.empty() || key == null){
            throw new Exception("ERROR! EMPTY LIST OR INVALID KEY.");
        }
        Cell aux = this.first;
        while (aux.next != null && !aux.next.item.equals(key)){
            aux = aux.next;
        }
        if(aux.next == null){
            return null;
        }
        Cell c = aux.next;
        Object item = c.item;
        aux.next = c.next;
        if(aux.next == null){
            this.last = aux;
        }
        return item;

    }

    public boolean empty(){
        return this.first == this.last;
    }
}
