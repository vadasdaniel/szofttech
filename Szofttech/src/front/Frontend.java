package front;

import sun.swing.StringUIClientPropertyKey;

import java.io.*;
import java.util.Scanner;

public class Frontend {

    Scanner sc = new Scanner(System.in);
    BufferedReader br = null;

    public void start()  {
        boolean exit=false;
        while(!exit) {
            System.out.println("Üdvözlünk a Munkaközvetítő cégnél.\n" +
                    "Kérlek válassz az alábbi listából:\n" +
                    "(1) Belépés\n" +
                    "(2) Regisztráció\n" +
                    "(3) Belépés vendégként\n" +
                    "(4) Kilépés");
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                    getUsernameandPw();
                    break;
                case 2:
                    addUsernameandPw();
                    break;
                case 3:
                    menu02();
                    break;
                case 4:
                    exit=true;
                    break;
            }
        }
    }

    private void getUsernameandPw() {
        System.out.println("Adja meg a felhasználónevét!");
        String LoginUsername = sc.next();
        System.out.println("Adja meg a jelszót!");
        String LoginPassword = sc.next();
        System.out.println("1.Felhasználó,2.Partnercég,3.Admin");
        int choose = sc.nextInt();
        switch (choose) {
            case 1: userMenu();
                break;
            case 2: companyMenu();
                break;
            case 3: adminMenu();
                break;
        }
    }

    private void userMenu() {
        System.out.println("(1) Hírdetések keresése\n" +
                           "(2) Lejelentkezés hírdetéstről");
        int choose = sc.nextInt();
        switch (choose) {
            case 1: ;
                break;
            case 2: ;
                break;
        }
    }

    private void companyMenu() {
        System.out.println("(1) Hírdetések keresése\n" +
                "(2) Hírdetés létrehozása\n" +
                "(3) Jelentkező elbírálása\n" +
                "(4) Hírdetés törlése");
        int choose = sc.nextInt();
        switch (choose) {
            case 1: ;
                break;
            case 2: ;
                break;
            case 3: ;
                break;
            case 4: ;
                break;
        }
    }

    private void adminMenu() {
            System.out.println("(1) Keresés\n" +
                                "(2) Statisztika\n" +
                                "(3) Hírdetés tölés\n" +
                                "(4) Felhasználó törlés\n");
            int choose = sc.nextInt();
            switch (choose) {
                case 1: ;
                    break;
                case 2: ;
                    break;
                case 3: ;
                    break;
                case 4: ;
                    break;

        }
    }

    private void addUsernameandPw() {
        System.out.println("Típus");

        System.out.println("Adja meg a nevét!");
        String RegName = sc.next();
        System.out.println("Adjon meg egy felhasználónevet!");
        String RegUsername = sc.next();
        System.out.println("Adja meg a jelszót!");
        String RegPassword = sc.next();
    }

    private void menu02() {
        System.out.println("\n  Hírdetések listázása!\n");
        try {
            File file = new File("src\\storage\\resources\\ads.dat");
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
                System.out.println(st);
            } catch (Exception e) {
                e.printStackTrace();
        }
        System.out.println("\nÖn vendégként a hírdetéseket csak megtekinteni tudja!\n" +
                           "Ahhoz,hogy fel tudjon jelentkezni egy hírdetésre regisztálnia kell!");
    }

}
