package front;

public class FrontendUtil {

    public static void writeErrorMessage(String message) {
        System.out.println("\n -- Error -- \n "+ message +" \n");
    }

    public static void writeWarningMessage(String message) {
        System.out.println("\n -- Warning -- \n "+ message +" \n");
    }

    public static void writeWindowHeader(String header) {
        System.out.println("\n ---- " + header + " ----");
    }
}
