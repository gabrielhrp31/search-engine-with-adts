import structures.list.SearchList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String path = args[0];
        String keywordPath = args[1];
        SearchList lista = null;
        SearchList keywords = null;
        System.out.println("Opening " + path + " for lecture");
        try {
            keywords = keywordReader(keywordPath);
            lista = textReader(path, keywords);
            lista.show();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
        System.out.println();
    }

    public static SearchList keywordReader(String path) throws IOException{

        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        SearchList list = new SearchList();

        while (buffRead.ready()) {
            string = buffRead.readLine();
            string = (String) string.replace(".", "");
            string = (String) string.replace(",", "");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for (String word : words) {
                list.add(word, index);
            }
            index++;
        }

        return  list;

    }

    public static SearchList textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        SearchList list = new SearchList();
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
}
