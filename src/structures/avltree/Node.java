package structures.avltree;

import structures.list.*;

class Node {
    String word;
    List lines;
    int height;
    Node left, right;

    Node(String value,int line) {
        this.word = value;
        this.lines = new List();
        this.lines.add(line);
        this.height = 1;
    }
}