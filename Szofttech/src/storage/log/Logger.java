package storage.log;

import back.FileManager;
import common.Log;
import storage.ExceptionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Logger {

    public final String NOT_FOUND = "Nem található!";
    public final String DATABASE_NOT_FOUND = "Az adatbázis nem található!";
    public final String FAILED_DELETE = "Sikertelen törlés";
    public final String FAILED_ADD = "Sikertelen hozzáadás";
    public final String WRONG_INPUT = "Hibás adatok";

    public final String DELETED = "Sikeres törlés";
    public final String ADDED = "Sikeres hozzáadás";

    ExceptionManager exceptionManager = new ExceptionManager();

    private static final String fileName = "logs.dat";
    private FileManager fileManager;
    private List<Log> logs;
    private String className;

    public Logger(String className) {
        this.fileManager = new FileManager();
        this.className = className;
        readData();
    }

    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            logs = new ArrayList<>();
            for (String data : datas) {
                String[] dataColumn = data.split(",");
                Log log = new Log(
                        dataColumn[0], // id
                        dataColumn[1], // date
                        dataColumn[2], // type
                        dataColumn[3], // from
                        dataColumn[4]  // text
                );
                logs.add(log);
            }
        } catch (IOException e) {
            exceptionManager.error("Hiba a logolvasás közben.");
        }
    }

    public void delete(Log content) {
        logs.removeIf(next -> next.getId().equals(content.getId()));
        try {
            fileManager.remove(fileName, new String[]{content.toString()});
        } catch (IOException e) {
            exceptionManager.error("Hiba a log törlése közben.");
        }
    }

    public void add(Log content) {
        try {
            fileManager.add(fileName, content.toLogSave());
        } catch (IOException e) {
            exceptionManager.error("Hiba a log hozzáadása közben.");
        }
    }

    public Log get(String id) {
        try {
            return logs
                    .stream()
                    .filter(jobAd -> jobAd.getId().equals(id))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (Exception e) {
            exceptionManager.error("Hiba a log keresése közben.");
            return null;
        }

    }

    public List<Log> list() {
        return logs;
    }

    public void error(String errorMessage) {
        Log log = new Log(UUID.randomUUID().toString(), LocalDate.now().toString(), "Error", className, errorMessage);
        add(log);
        logs.add(log);
    }

    public void info(String errorMessage) {
        Log log = new Log(UUID.randomUUID().toString(), LocalDate.now().toString(), "Info", className, errorMessage);
        add(log);
    }
}
