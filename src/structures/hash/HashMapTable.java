package structures.hash;

import structures.list.List;
import structures.list.SearchList;
import tools.PrintTools;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HashMapTable implements Runnable{

    private int tableSize;
    private HashMapCell[] hashTable;
    private int[] weight;
    private String path;
    private SearchList keywords;

    public HashMapTable(int tableSize,String path, SearchList keywords){
        this.tableSize = tableSize;
        this.hashTable = new HashMapCell[this.tableSize];
        //initializing
        for (int i = 0; i < this.tableSize; i++) {
            this.hashTable[i] = null;
        }
        this.keywords = keywords;
        this.path = path;
        this.weight = HashMapUtil.generateWeights(tableSize);
    }


    @Override
    public void run() {
       printInfo(0);
       printInfo(1);
       printInfo(2);

    }

    private String collisionResolution(int opt){
        switch (opt){
            case 0 :
                return  "linear probing";
            case 1:
                 return "quadratic probing";
            default:
                return "double hashing";
        }
    }

    private void printInfo(int opt){
        HashMapTable hashMapTable = null;
        long beginTime, endTime;
        ArrayList<Long> times = new ArrayList<>();
        PrintTools pTools = new PrintTools();

        try {
            System.out.println("-------------------------------------");
            System.out.println("\tHashMap (OPEN HASH) -> "+collisionResolution(opt));
            System.out.println();
            for (int i = 0; i < 10; i++) {
                beginTime = System.nanoTime();
                hashMapTable = textReader(path, keywords,opt);
                hashMapTable.printInFile(opt);
                endTime = System.nanoTime();
                times.add(endTime - beginTime);
            }
            hashMapTable.print(opt);
            pTools.printTimeInFiles(times, "OpenHash-"+opt);
        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n", e.getMessage());
        }

    }



    private HashMapTable textReader(String path, SearchList keywords, int opt) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string;
        int index = 1;

        HashMapTable hashMapTable = new HashMapTable(tableSize, null, null);

        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = string.replace(".", "");
            string = string.replace(",", "");
            String[] words;
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




    private void print(int opt) {
        System.out.println("-------------------------------------");
        System.out.println("Hash using collision resolution: "+collisionResolution(opt));
        System.out.println();
        ordenate();
        for (HashMapCell hashMapCell : hashTable) {
            if (hashMapCell != null) {
                System.out.print(hashMapCell.getKeyWord() + " ");
                hashMapCell.getLines().show();
            }
        }

    }


    private void printInFile(int opt)throws IOException{
        FileWriter write = new FileWriter("../results/OpenHash-" +opt+ ".txt", false);
        PrintWriter print_line = new PrintWriter(write);
        ordenate();
        Arrays.stream(hashTable).filter(Objects::nonNull).forEach(hashMapCell -> {
            print_line.print(hashMapCell.getKeyWord() + " ");
            hashMapCell.getLines().showInFile(write);
        });
        print_line.close();

    }


    private void ordenate(){
        for(int i = 0; i < hashTable.length; i++){
            if(hashTable[i] != null) {
                for (int j = i; j < hashTable.length; j++) {
                    if (hashTable[j] != null) {
                        if (hashTable[i].getKeyWord().compareToIgnoreCase(hashTable[j].getKeyWord()) > 0) {
                            HashMapCell aux = hashTable[i];
                            hashTable[i] = hashTable[j];
                            hashTable[j] = aux;
                        }

                    }
                }
            }
        }
    }
}