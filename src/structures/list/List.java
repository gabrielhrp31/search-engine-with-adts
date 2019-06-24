package structures.list;

public class List {

    ListCell first, last;
    int length;

    List(){
        length=0;
        last = null;
        first = null;
    }

    public Boolean empty(){
        return length==0;
    }

    public void add(Object insert){
        ListCell item = new ListCell();
        if(empty()){
            item.value = insert;
            first=item;
            last=item;
            length++;
        }else{
            item.value=insert;
            last.next=item;
            item.prev=last;
            last=item;
            length++;
        }
    }

}