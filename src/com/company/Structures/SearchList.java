package com.company.Structures;


public class SearchList {

    SearchCell first, last;
    int length = 0;

    public Boolean empty() {
        return length == 0;
    }

    public void add(String word, int line) {
        SearchCell item = new SearchCell();
        if (empty()) {
            item.value = word;
            item.lines.add(line);
            first = item;
            last = item;
            length++;
        } else {
            item.value = word;
            item.lines.add(line);
            item.prev = last;
            last.next = item;
            last = item;
            length++;

        }
    }

    public SearchCell exists(String word) {
        SearchCell current = this.first;
        while (current != null) {
            if (current.value.equals(word))
                return current;
            current = current.next;
        }
        return null;
    }

    public void show() {
        SearchCell current = this.first;
        while (current != null) {
            System.out.print(current.value + " ");
//            current.lines.forEach(
//                    line -> System.out.print(line+" ")
//            );
            System.out.println();
            current = current.next;
        }
    }
}
