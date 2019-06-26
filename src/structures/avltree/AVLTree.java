package structures.avltree;

import java.io.*;
import structures.list.SearchList;
import java.util.ArrayList;
import tools.printtools.PrintTools;

public class AVLTree implements Runnable{

    Node root=null;
    private String path;
    private SearchList keywords;

    public AVLTree(String path, SearchList keywords){
        this.path = path;
        this.keywords = keywords;
    }

    public void run() {
        AVLTree avltree = null;
        long beginTime, endTime;
        ArrayList<Long> times = new ArrayList<>();
        PrintTools pTools = new PrintTools();

        try {
            System.out.println("-------------------------------------");
            System.out.println("AVLTree");
            System.out.println();
            for (int i = 0; i < 10; i++) {
                beginTime = System.nanoTime();
                avltree = textReader(path, keywords);
                avltree.printInFile();
                endTime = System.nanoTime();
                times.add(endTime - beginTime);
            }
            avltree.show();
            pTools.printTimeInFiles(times, "AVLTree");
        } catch (IOException e) {
            System.err.printf("Error while opening the file: %s.\n", e.getMessage());
        }
    }

    public AVLTree textReader(String path, SearchList keywords) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String string = "";
        int index = 1;

        AVLTree avltree = new AVLTree(null, null);

        while (buffRead.ready()) {

            string = buffRead.readLine();
            string = (String) string.replace(".", "");
            string = (String) string.replace(",", "");
            String[] words = string.split("\\s");
            words = string.trim().split("\\s+");
            for (String word : words) {
                if (keywords.exists(word) != null)
                    avltree.insert(word, index);
            }
            index++;
        }

        buffRead.close();
        return avltree;
    }

    // function to get the height of tree
    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // function to simpe get max of two int numbers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // rotate a subtree in right
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // make a rotation
        x.right = y;
        y.left = T2;

        // update ancestor heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // return a new root node
        return x;
    }

    // rotate a subtree in left.
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // make a rotation
        y.left = x;
        x.right = T2;

        // update ancestor heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // return a new root node
        return y;
    }

    // verify if N node is balanced
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    public void insert(String value, int line){
        this.root = this.insert_recursive(root, value, line);
    }

    private Node insert_recursive(Node node, String value, int line) {

        /* Execute the default insertion */
        if (node == null)
            return (new Node(value, line));

        if (value.compareToIgnoreCase(node.word)<0)//case the value is less
            node.left = insert_recursive(node.left, value, line);
        else if (value.compareToIgnoreCase(node.word)>0)//case the value is greater
            node.right = insert_recursive(node.right, value, line);
        else{ // case the value is equal
            node.lines.add(line);
            return node;
        }
        //update ancestor height
        node.height = 1 + max(height(node.left), height(node.right));

        /*
         * verify if this turned to a unbalanced node
         */
        int balance = getBalance(node);

        // if the previous case is true
        if (balance > 1 && value.compareToIgnoreCase(node.left.word) < 0)
            return rightRotate(node);

        if (balance < -1 && value.compareToIgnoreCase(node.right.word) > 0)
            return leftRotate(node);

        if (balance > 1 && value.compareToIgnoreCase(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value.compareToIgnoreCase(node.right.word)< 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /*return a pointer of inaltered node*/
        return node;
    }

    public void show(){
        inOrder(root);
    }

    public void printInFile() throws IOException {
        FileWriter write = new FileWriter("../results/AVLTree.txt", false);
        PrintWriter print_line = new PrintWriter(write);
        printInFileRecursive(root, write, print_line);
        print_line.close();
    }

    private void printInFileRecursive(Node node,FileWriter write, PrintWriter pWriter) {
        if (node != null) {
            printInFileRecursive(node.left, write, pWriter);
            pWriter.print(node.word + " ");
            node.lines.showInFile(write);
            printInFileRecursive(node.right, write, pWriter);
        }
    }

    //print in transversal order recursively 
    private void inOrder(Node node) { 
        if (node != null) { 
            inOrder(node.left); 
            System.out.print(node.word + " ");
            node.lines.show();
            inOrder(node.right); 
        } 
    }

}