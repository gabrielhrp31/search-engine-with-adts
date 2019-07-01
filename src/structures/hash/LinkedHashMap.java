package structures.hash;

import structures.list.List;
import structures.list.SearchList;
import tools.PrintTools;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *  Linked Linear List for each address of the table,
 *  that way all keys with the same address are linked in
 *  a linear list
 */
public class LinkedHashMap implements Runnable{

    private int tableSize;
    private LinkedHashCell[] hmLinkedTable;
    private int[] weights;
    private String path;
    private SearchList keywords;


    public LinkedHashMap(int tableSize, String path, SearchList keywords) {
        this.tableSize = tableSize;
        this.hmLinkedTable = new LinkedHashCell[tableSize];
        for (int i = 0; i < tableSize; i++)
            this.hmLinkedTable[i] = null;

        this.keywords = keywords;
        this.path = path;

        this.weights = HashMapUtil.generateWeights(tableSize);
    }


    @Override
    public void run() {
        printInfo();

    }

    public void add(String keyword, int line) {
        int h = HashMapUtil.h(keyword.toLowerCase(),this.weights,this.tableSize);
        if(hmLinkedTable[h] == null){
            List list = new List();
            list.add(line);
            hmLinkedTable[h] = new LinkedHashCell(keyword,list);
        } else {
            LinkedHashCell hashCell = hmLinkedTable[h];
            while (hashCell.getNext() != null && hashCell.getKeyWord().compareToIgnoreCase(keyword) != 0){
                hashCell = hashCell.getNext();
            }
            if(hashCell.getKeyWord().compareToIgnoreCase(keyword) == 0){
                hashCell.setLines(line);
            } else {
                List list2 = new List();
                list2.add(line);
                hashCell.setNext(new LinkedHashCell(keyword,list2));
            }
        }

    }

    private void printInfo(){
        LinkedHashMap linkedHashMap=null;
        long beginTime, endTime;
        ArrayList<Long> times = new ArrayList<>();
        PrintTools pTools = new PrintTools();

        try {
            System.out.println("-------------------------------------");
            System.out.println("\tLinkedHashMap");
            System.out.println();
            for (int i = 0; i < 10; i++) {
                beginTime = System.nanoTime();
                linkedHashMap = textReader(path, keywords);
                linkedHashMap.printInFile();
                endTime = System.nanoTime();
                times.add(endTime - beginTime);
            }
            linkedHashMap.print();
            pTools.printTimeInFiles(times, "LinkedHashMap");
        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n", e.getMessage());
        }

    }

    private void printInFile()throws IOException{
        FileWriter write = new FileWriter("../results/LinkedHashMap.txt", false);
        PrintWriter print_line = new PrintWriter(write);
        ordenate();
        Arrays.stream(hmLinkedTable).filter(Objects::nonNull).forEach(hashMapCell -> {
            print_line.print(hashMapCell.getKeyWord() + " ");
            hashMapCell.getLines().showInFile(write);
        });
        print_line.close();

    }


    private LinkedHashMap textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string;
        int index = 1;

        LinkedHashMap linkedHashMap = new LinkedHashMap(tableSize,path,keywords);

        while (buffRead.ready()) {
            string = buffRead.readLine();
            string =  string.replace(".", "");
            string =  string.replace(",", "");
            String[] words;
            words = string.trim().split("\\s+");
            for (String word : words) {
                if (keywords.exists(word) != null)
                    linkedHashMap.add(word, index);
            }
            index++;
        }

        buffRead.close();
        return linkedHashMap;
    }

    private void print() {
        System.out.println("-------------------------------------");
        System.out.println("LinkedHashMap ");
        System.out.println();
        ordenate();
        for (LinkedHashCell linkedHashCell : hmLinkedTable) {
            if (linkedHashCell != null) {
                System.out.print(linkedHashCell.getKeyWord() + " ");
                linkedHashCell.getLines().show();
            }
        }

    }

    private void ordenate(){
        for(int i = 0; i < hmLinkedTable.length; i++){
            if(hmLinkedTable[i] != null) {
                for (int j = i; j < hmLinkedTable.length; j++) {
                    if (hmLinkedTable[j] != null) {
                        if (hmLinkedTable[i].getKeyWord().compareToIgnoreCase(hmLinkedTable[j].getKeyWord()) > 0) {
                            LinkedHashCell aux = hmLinkedTable[i];
                            hmLinkedTable[i] = hmLinkedTable[j];
                            hmLinkedTable[j] = aux;
                        }

                    }
                }
            }
        }
    }


}
