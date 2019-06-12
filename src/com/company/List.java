package com.company;

public class List {
    Cell first, last;
    int length;

    List(){
        length=0;
        last = null;
        first = null;
    }

    public Boolean vazia(){
        return last==null && first==null;
    }

    public void add(String word, int line){
        Cell item = new Cell();
        if(vazia()){
            item.word = word;
            item.lines.add(line);
            first=item;
            last=item;
        }else{
            Cell wordFound= exists(word);
            if(wordFound!=null){
                wordFound.lines.add(line);
            }else{
                item.word=word;
                item.lines.add(line);
                last.next=item;
                item.prev=last;
                last=item;
            }
        }
    }

    public Cell exists(String word) {
        Cell atual = this.first;
        while (atual != null) {
            if (atual.word.equals(word))
                return atual;
            atual = atual.next;
        }
        return null;
    }
}