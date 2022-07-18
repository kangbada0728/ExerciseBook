package modernJavaInAction.part3.part3_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        String oneLine = main.processFile(BufferedReader::readLine);
        System.out.println("oneLine = " + oneLine);
        String twoLine = main.processFile(b -> b.readLine() + b.readLine());
        System.out.println("twoLine = " + twoLine);
    }

    public String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }
}
