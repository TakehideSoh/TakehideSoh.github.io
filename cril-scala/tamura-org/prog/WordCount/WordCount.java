import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WordCount {

    public static void main(String[] args) {
        String fileName = "hamlet.txt";
        if (args.length > 0) fileName = args[0];
        try {
            CountTable1 table = new CountTable1();
            // CountTable2 table = new CountTable2();
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                line = line.toUpperCase();
                line = line.replaceAll("[^A-Z]", " ");
                for (String s : line.split("\\s+")) {
                    if (! s.equals("")) {
                        table.add(s);
                    }
                }
            }
            in.close();
            for (String s : table.getKeysByCount()) {
                int count = table.get(s);
                System.out.println(count + "\t" + s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
