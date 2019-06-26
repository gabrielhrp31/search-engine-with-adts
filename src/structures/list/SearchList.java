package structures.list;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import tools.printtools.PrintTools;

public class SearchList implements Runnable {

    public SearchCell first, last;
    public int length = 0;
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
        SearchList list = null;
        long beginTime, endTime;
        PrintTools pTools = new PrintTools();
        ArrayList<Long> times = new ArrayList<>();
        
        try {
            System.out.println("-------------------------------------");
            System.out.println("\tList");
            System.out.println();
            for(int i=0;i<10;i++){
                beginTime = System.nanoTime();
                list = textReader(path, keywords);
                list.sort();
                list.printInFile();
                endTime =  System.nanoTime();
                times.add(endTime - beginTime);
            }
            list.show();
            pTools.printTimeInFiles(times, "List");
        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n",e.getMessage());
        }
    }

    public SearchList textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        SearchList list = new SearchList(null, null);

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

    public void sort() {
        if (length > 1) {
            boolean wasChanged;

            do {
                SearchCell current = first;
                SearchCell previous = null;
                SearchCell next = first.next;
                wasChanged = false;

                while (next != null) {
                    String currentValue = (String)current.value;
                    String nextValue = (String)next.value;
                    if (currentValue.compareToIgnoreCase(nextValue)>1) {
                        wasChanged = true;

                        if (previous != null) {
                            SearchCell sig = next.next;

                            previous.next = next;
                            next.next = current;
                            current.next = sig;
                        } else {
                            SearchCell sig = next.next;

                            first = next;
                            next.next = current;
                            current.next = sig;
                        }

                        previous = next;
                        next = current.next;
                    } else {
                        previous = current;
                        current = next;
                        next = next.next;
                    }
                }
            } while (wasChanged);
        }
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
            SearchCell wordFound = exists(word);
            if(wordFound!=null){
                wordFound.lines.add(line);
            }else{
                item.value = word;
                item.lines.add(line);
                last.next = item;
                last = item;
                length++;
            }
        }
    }

    public SearchCell exists(String word) {
        SearchCell current = this.first;
        while (current != null) {
            String value =  (String)current.value;
            value = value.toLowerCase();
            if (value.compareToIgnoreCase(word)==0) {
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

        System.out.println();
    }

    public void printInFile() throws IOException {
        FileWriter write = new FileWriter("../results/List.txt" , false);
        PrintWriter print_line = new PrintWriter(write);
        SearchCell current = this.first;
        while (current != null) {
            print_line.print(current.value + "\t");
            current.lines.showInFile(write);
            current = current.next;
        }

        print_line.println();
        print_line.close();
    }
}
