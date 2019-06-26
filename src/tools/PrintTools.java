package tools.printtools;

import java.util.Collections;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrintTools{


    public void printTimeInFiles(ArrayList<Long> times,String fileName) throws IOException {
        FileWriter write = new FileWriter("../results/"+fileName+".txt", true);
        PrintWriter print_line = new PrintWriter(write);
        long tempoMedio = 0;
        print_line.println();
        print_line.println("----Tempo Gasto em NanoSegundos----");
        for (int i = 0; i < times.size(); i++) {
            tempoMedio += times.get(i);
            print_line.println("Iteração " + (i + 1) + ": " + times.get(i));
        }
        tempoMedio = tempoMedio / times.size();
        Collections.sort(times);
        print_line.println();
        print_line.println("Menor Tempo: " + times.get(0));
        print_line.println("Maior Tempo: " + times.get((times.size() - 1)));
        print_line.println("Tempo Medio: " + tempoMedio);
        print_line.close();
    }
}