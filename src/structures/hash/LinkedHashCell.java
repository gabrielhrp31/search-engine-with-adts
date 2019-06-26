package structures.hash;

import structures.list.List;

public class LinkedHashCell {

    String keyWord;
    List lines;
    int deleted;
    LinkedHashCell next;


    public LinkedHashCell(String keyWord, List lines) {
        this.keyWord = keyWord;
        this.lines = lines;
        this.next = null;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List getLines() {
        return lines;
    }

    public void setLines(int i) {
        this.lines.add(i);
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public LinkedHashCell getNext() {
        return next;
    }

    public void setNext(LinkedHashCell next) {
        this.next = next;
    }
}
