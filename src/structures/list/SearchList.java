package structures.list;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchList implements Runnable {

    SearchCell first, last;
    int length = 0;
    private String path;
    private SearchList keywords;

    public SearchList(String path,SearchList keywords){
        this.path = path;
        this.keywords = keywords;
    }

    public Boolean empty() {
        return first==null && last==null;
    }


    public void run(){
        SearchList lista = null;
        try {
            System.out.println("Opening " + path + " for lecture of text");
            lista = textReader(path, keywords);
            lista.show();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
    }






    public SearchList textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        SearchList list = new SearchList(null, null);
        // SearchList hash = new HashMapTable();

        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = (String) string.replace(".", "");
            string = (String) string.replace(",", "");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for (String word : words) {
                if(keywords.exists(word)!=null)
                    list.add(word, index);
            }
            index++;
        }

        buffRead.close();
        return list;
    }

    public void add(String word, int line) {
        SearchCell item = new SearchCell();
        if (empty()) {
            item.value = word;
            item.lines.add(line);
            first = item;
            last = item;
        } else {
            SearchCell wordFound = exists(word);
            if(wordFound!=null){
                wordFound.lines.add(line);
            }else{
                item.value = word;
                item.lines.add(line);
                last.next = item;
                last = item;
            }
        }
        length++;
    }

    public SearchCell exists(String word) {
        SearchCell current = this.first;
        while (current != null) {
            String value =  (String)current.value;
            value = value.toLowerCase();
            if (value.equals(word.toLowerCase())) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void show() {
        SearchCell current = this.first;
        while (current != null) {
            System.out.print(current.value + "\t");
            current.lines.show();
            current = current.next;
        }
    }
}
