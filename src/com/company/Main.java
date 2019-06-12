package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = args[0];
        System.out.println("Abrindo "+ path + " para leitura");
        List leitor = leitor(path);
        System.out.println();
    }

    public static List leitor(String path) throws IOException {


        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String linha = "";
        int indice =1;

        List list = new List();

        while (buffRead.ready()) {

            linha = buffRead.readLine();
            String[] words = linha.split(  "\\s");
            words = linha.trim().split("\\s+");
            for(String word: words){
                System.out.println(indice+":"+word);
                list.add(word,indice);
            }
            indice++;
        }

        buffRead.close();
        return list;
    }
}
