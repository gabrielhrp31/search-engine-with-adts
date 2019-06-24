package structures.list;

public class ListCell {

    ListCell next, prev;
    Object value;

    ListCell(){
        this.next=null;
        this.prev=null;
        this.value = null;
    }
}
