package back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public void remove(String filePath, String id) {

    }

    public void add(String filePath, Object content) {

    }

    public List<String> read(String file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            while (input.readLine() != null) {
                lines.add(input.readLine());
            }
        }
        return lines;
    }

}
