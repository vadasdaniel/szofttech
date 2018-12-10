package storage;

public class ExceptionManager {

    public void warn(String warnText) {
        System.out.println("Figyelmeztetés!\n" + warnText);
    }

    public void error(String errorText) {
        System.out.println("Hiba!\n" + errorText);
    }

}
