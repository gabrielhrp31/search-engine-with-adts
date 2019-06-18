package com.company;


public class SearchList{
    SearchCell first, last;
    int length = 0;

    public Boolean empty(){
        return length==0;
    }

    public void add(String word,int line) {
        SearchCell item = new SearchCell();
        if(empty()){
            item.value = word;
            item.lines.add(line);
            first=item;
            last=item;
            length++;
        }else{
            SearchCell wordFound = exists(word);
            if(wordFound!=null){
                wordFound.lines.add(line);
            }else{
                if(last.value.toString().compareToIgnoreCase(word)<0){
                    item.value=word;
                    item.lines.add(line);
                    item.prev=last;
                    last.next=item;
                    last=item;
                    length++;
                }else if(first.value.toString().compareToIgnoreCase(word)>0){
                    item.value=word;
                    item.lines.add(line);
                    item.next=first;
                    first.prev=item;
                    first = item;
                    length++;
                }else{
                    SearchCell current = this.first;

                    while (current!=null){
                        if(current.value.toString().compareToIgnoreCase(word)>0){
                            item.value=word;
                            item.lines.add(line);
                            item.prev=current;
                            item.next=current.next;
                            current.next=item;
                            current = item;
                            length++;
                        }
                        current = current.next;
                    }
                }

            }
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

    public void show(){
        SearchCell current = this.first;
        while (current != null) {
            System.out.print(current.value+" ");
//            current.lines.forEach(
//                    line -> System.out.print(line+" ")
//            );
            System.out.println();
            current = current.next;
        }
    }
}
