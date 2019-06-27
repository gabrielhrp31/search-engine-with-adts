package structures.hash;

import structures.list.List;

/*
    Represents one cell from the table
 */
public class HashMapCell {

    public String keyWord;
    public List lines;
    //control for removal operations, instead of erase the key
    boolean deleted;


    public HashMapCell(String keyWord, List list, boolean deleted) {
        this.keyWord = keyWord;
        this.lines = list;
        this.deleted = deleted;
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



    public static class Deleted extends HashMapCell {

        private static Deleted entry = null;



        private Deleted() {

            super("", new List(), true);

        }



        public static Deleted getUniqueDeleted() {

            if (entry == null)

                entry = new Deleted();

            return entry;

        }

    }

    @Override
    public String toString() {
        return "HashMapCell{" +
                "keyWord='" + keyWord + '\'' +
                ", lines=" + lines +
                ", deleted=" + deleted +
                '}';
    }
}
