package structures.btree;

import structures.list.SearchList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tree {
    public Node root;
    private String path;
    private SearchList keywords;

    public Tree(String path, SearchList keywords){
        this.path=path;
        this.keywords = keywords;
        this.root=null;
    }

    public void run(){
        Tree tree = null;
        try {
            System.out.println("Opening " + path + " for lecture of text");
            tree = textReader(path, keywords);
            tree.print();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
    }






    public Tree textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        Tree tree = new Tree(null, null);


        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = (String) string.replace(".", "");
            string = (String) string.replace(",", "");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for (String word : words) {
                if(keywords.exists(word)!=null)
                    tree.add(word, index);
            }
            index++;
        }

        buffRead.close();
        return tree;
    }


    public void add(String value,int line)
    {
        if ( root == null )
        {
            root = new Node(value, line);
        }
        else
        {
            root.put(value, line);
        }
    }

    public Node  search( String key )
    {
        return root == null ? null : root.get( key );
    }


    public void print(){
        this.print_recursive(this.root);
    }

    private void print_recursive(Node node) {
        if(node == null) {
            return;
        }

        this.print_recursive(node.left);

        System.out.print(node.word +"\t");
        node.lines.show();

        this.print_recursive(node.right);
    }

}
