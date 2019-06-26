package structures.hash;

import structures.list.List;

import java.util.Arrays;

import java.io.*;
import structures.list.SearchList;
import java.util.ArrayList;
import tools.printtools.PrintTools;

public class HashMapTable implements Runnable{

    private int tableSize;
    private HashMapCell[] hashTable;
    private int[] weight;
    private String path;
    private SearchList keywords;


    public void run() {
        HashMapTable hashMapTable = null;
        long beginTime, endTime;
        ArrayList<Long> times = new ArrayList<>();
        PrintTools pTools = new PrintTools();

        try {
            for (int i = 0; i < 10; i++) {
                beginTime = System.nanoTime();
                hashMapTable = textReader(path, keywords,0);
                hashMapTable.print(0);
                hashMapTable.printInFile(0);
                endTime = System.nanoTime();
                times.add(endTime - beginTime);
            }
            pTools.printTimeInFiles(times, "AVLTree");
        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n", e.getMessage());
        }
    }

    public HashMapTable(int tableSize,String path, SearchList keywords){
        this.tableSize = tableSize;
        this.hashTable = new HashMapCell[this.tableSize];
        this.keywords = keywords;
        this.path = path;
        //initializing
        for (int i = 0; i < this.tableSize; i++) {
            this.hashTable[i] = null;
        }
        this.weight = HashMapUtil.generateWeights(tableSize);
    }

    public HashMapTable textReader(String path, SearchList keywords, int opt ) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        HashMapTable hashMapTable = new HashMapTable(tableSize, null, null);

        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = (String) string.replace(".", "");
            string = (String) string.replace(",", "");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for (String word : words) {
                if (keywords.exists(word) != null)
                    hashMapTable.add(word, index,opt);
            }
            index++;
        }

        buffRead.close();
        return hashMapTable;
    }


    /**
     *  Insertion should reuse deleted slots when possible
     * @param key
     * @param line
     * @param option
     */
    public void add(String key, int line, int option) {

        int h = HashMapUtil.h(key.toLowerCase(), this.weight, this.tableSize);
        int h2 = HashMapUtil.h2(key.toLowerCase(), this.weight, this.tableSize);
        int initialHash = -1;
        int indexDeletedCell = -1;
        int constant = 0;

        while (h != initialHash
                && hashTable[h] == HashMapCell.Deleted.getUniqueDeleted()
                || hashTable[h] != null && hashTable[h].getKeyWord().compareToIgnoreCase(key) !=0) {

            if (initialHash == -1) {
                initialHash = h;
            }

            if (hashTable[h] == HashMapCell.Deleted.getUniqueDeleted()) {
                indexDeletedCell = h;
            }

            switch (option) {
                // OPEN HASH LINEAR PROBING
                // distance between probes is constant
                case 0:
                    h = (h + 1) % this.tableSize;
                    break;
                // OPEN HASH QUADRATIC PROBING
                // distance between probes increases by certain constant at each step
                case 1:
                    h = (h + (++constant)) % this.tableSize;
                    break;
                // OPEN HASH DOUBLE HASHING
                // distance between probes is calculated using another hash function
                case 2:
                    h = (h + h2) % this.tableSize;
            }

        }

        if ((hashTable[h] == null ||h == initialHash ) && indexDeletedCell != -1) {
            List list = new List();
            list.add(line);
            hashTable[indexDeletedCell] = new HashMapCell(key, list, false);
        } else if (initialHash != h) {
            if (hashTable[h] != HashMapCell.Deleted.getUniqueDeleted()

                    && hashTable[h] != null && hashTable[h].getKeyWord().compareToIgnoreCase(key) == 0)

                //update the line number in the keyword
                hashTable[h].setLines(line);

            else {

                List list2 = new List();
                list2.add(line);
                hashTable[h] = new HashMapCell(key, list2, false);

            }
        }
    }




    public void print(int opt) {
        System.out.println("-------------------------------------");
        System.out.println("Hash using option:"+opt);
        System.out.println();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                System.out.print(hashTable[i].getKeyWord());
                hashTable[i].getLines().show();
            }
        }

    }


    public void printInFile(int opt)throws IOException{
        FileWriter write = new FileWriter("../results/OpenHash-" +opt+ ".txt", false);
        PrintWriter print_line = new PrintWriter(write);
        for(int i = 0; i < hashTable.length; i++){

           if(hashTable[i] != null){
               print_line.print(hashTable[i].getKeyWord());
               hashTable[i].getLines().showInFile(write);
           }
        }
        print_line.close();

    }
}