import com.company.Structures.SearchList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String path = args[0];
        SearchList lista = null;
        System.out.println("Opening " + path + " for lecture");
        try {
            lista = reader(path);
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }

        lista.show();
        System.out.println();
    }

    public static SearchList reader(String path) throws IOException {
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
                // System.out.println(string+ ":" + word);
                list.add(word, index);
            }
            index++;
        }

        buffRead.close();
        return list;
    }
}
