package back;

import storage.log.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String ABSOLUTE_PATH = "Szofttech/src/storage/resources/";

    public void remove(String originalFileName, String lineToDelete) throws IOException {
        File temporaryFile = new File(ABSOLUTE_PATH + "temporaryFile.dat").getAbsoluteFile();
        File originalFile = new File( ABSOLUTE_PATH + originalFileName).getAbsoluteFile();

        try (
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile, true))
        ) {
            String line;
            while ( (line = reader.readLine()) != null ) {
                if ( !line.equals(lineToDelete) ) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        if ( originalFile.delete() ) {
            if (  !temporaryFile.renameTo(originalFile) ) {
                throw new IOException("Could not rename temporaryFile To" + originalFileName);
            }
        } else {
            throw new IOException("Could not delete original input file" + originalFileName);
        }
    }

    public void add(String fileName, String content) throws IOException {
        File file = new File(ABSOLUTE_PATH + fileName).getAbsoluteFile();
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
            output.write(content);
            output.newLine();
        } catch (Exception e) {

        }
    }

    public List<String> read(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(ABSOLUTE_PATH + fileName))) {
            String line;
            while ((line = input.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
