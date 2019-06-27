package structures.btree;

import structures.list.SearchList;
import tools.PrintTools;

import java.io.*;
import java.util.ArrayList;

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
        long beginTime, endTime;
        ArrayList<Long> times = new ArrayList<>();
        PrintTools pTools = new PrintTools();
        try {
            System.out.println("-------------------------------------");
            System.out.println("\tAvore Binária");
            System.out.println();
            for(int i=0;i<10;i++){
                beginTime = System.nanoTime();
                tree = textReader(path, keywords);
                tree.print_in_file();
                endTime = System.nanoTime();
                times.add(endTime-beginTime);
            }
            pTools.printTimeInFiles(times, "Tree");
            tree.print();

        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n",e.getMessage());
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


    private void print_in_file() throws IOException{
        FileWriter write = new FileWriter("../results/Tree.txt", false);
        PrintWriter print_line = new PrintWriter(write);
        print_in_file_recursive(this.root,write, print_line);
        print_line.close();
    }

    private void print_in_file_recursive(Node node,FileWriter writer, PrintWriter pWriter){
        if (node == null) {
            return;
        }

        this.print_in_file_recursive(node.left, writer, pWriter);

        pWriter.print(node.word + "\t");
        node.lines.showInFile(writer);

        this.print_in_file_recursive(node.right, writer, pWriter);
    }


    public void print(){
        this.print_recursive(this.root);
        System.out.println();
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
