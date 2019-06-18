import com.company.Structures.SearchList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = args[0];
        System.out.println("Opening "+ path + " for lecture");
        SearchList lista = reader(path);
        lista.show();
        System.out.println();
    }

    public static SearchList reader(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index =1;

        SearchList list = new SearchList();

        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = (String) string.replace(".","");
            string = (String) string.replace(",","");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for(String word: words){
//                System.out.println(string+ ":" + word);
                list.add(word,index);
            }
            index++;
        }

        buffRead.close();
        return list;
    }
}
