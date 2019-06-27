import structures.avltree.AVLTree;
import structures.btree.Tree;
import structures.hash.HashMapTable;
import structures.hash.LinkedHashMap;
import structures.list.SearchList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static int hashSize =1007 ;

    public static void main(String[] args) {
        String path =args[0];
        String keywordPath = args[1];
        SearchList keywords = null;
        try {
            keywords = keywordReader(keywordPath);
        }catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }


        System.out.println("Quantidade de palavras chave: "+keywords.length);

        SearchList searchList = new SearchList(path,keywords);
        searchList.run();
        Tree tree = new Tree(path, keywords);
        tree.run();
        AVLTree avltree = new AVLTree(path, keywords);
        avltree.run();
        HashMapTable hashMapTable = new HashMapTable(hashSize, path, keywords);
        hashMapTable.run();
        LinkedHashMap linkedHashMap = new LinkedHashMap(hashSize,path,keywords);
        linkedHashMap.run();
    }

    public static SearchList keywordReader(String path) throws IOException {

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
                if(word.length()>3)
                    list.add(word, index);
            }
            index++;
        }

        return  list;

    }
}
