package structures.list;

public class List {

    ListCell first, last;
    int length;

    public List(){
        length=0;
        last = null;
        first = null;
    }

    public Boolean empty(){
        return (first==null && last==null);
    }

    public void add(Object insert){
        ListCell item = new ListCell();
        if(this.empty()){
            item.value = insert;
            first=item;
            last=item;
        }else{
            if(!last.value.equals(insert)){
                last.next=item;
                item.value=insert;
                last=item;
                length++;
            }
        }
    }



    public void show() {
        ListCell current = this.first;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

}