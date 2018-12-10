package front;

import back.Backend;
import common.JobAd;
import common.Subscription;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Frontend {

    private Boolean exitProgram = false;
    private Scanner scanner;
    private Backend backend;

    public Frontend(Backend backend) {
        this.scanner = new Scanner(System.in);
        this.backend = backend;
    }

    public void start() {
        while (!exitProgram) {
            FrontendUtil.writeWindowHeader("Üdvözöljük a Munkaközvetítő cégnél");
            mainWindow();
        }
    }

    private void setExitProgram(Boolean exitProgram) {
        this.exitProgram = exitProgram;
    }

    private void mainWindow() {
        FrontendUtil.writeWindowHeader("Kérjük válasszon az alábbi menüpontokból");
        System.out.println("(1) Belépés");
        System.out.println("(2) Regisztráció");
        System.out.println("(3) Belépés vendégként");
        System.out.println("(4) Kilépés");

        int menuItem = scanner.nextInt();
        switch (menuItem) {
            case 1:
                loginWindow();
                break;
            case 2:
                registrationWindow();
                break;
            case 3:
                guestWindow();
                break;
            case 4:
                setExitProgram(true);
                break;
        }
    }

    private void loginWindow() {
        FrontendUtil.writeWindowHeader("Bejelentkezés");
        System.out.print("Adja meg a felhasználónevét: ");
        String username = scanner.next();
        System.out.print("Adja meg a jelszavát: ");
        String password = scanner.next();

        if (backend.loginUser(username, password)) {
            switch (backend.getLoggedInUser().getUserType()) {
                case USER:
                    userMenu();
                    break;
                case PARTNER_COMPANY:
                    partnerCompanyMenu();
                    break;
                case ADMIN:
                    adminMenu();
                    break;
            }
        } else {
            FrontendUtil.writeErrorMessage("A bejelentkezés nem sikerült. Kérjük próbálja meg újra.");
            loginWindow();
        }
    }

    private void guestWindow() {
        FrontendUtil.writeWindowHeader("Vendég nézet");
        System.out.println("(1) Hirdetés listázás");
        System.out.println("(2) Főoldal");

        int menuOption = scanner.nextInt();

        switch (menuOption) {
            case 1:
                listJobAds();
                guestWindow();
                break;
            case 2:
                mainWindow();
                break;
        }
    }

    private void registrationWindow() {
        FrontendUtil.writeWindowHeader("Regisztráció");

        System.out.print("Adja mega a nevét: ");
        String name = scanner.next();
        System.out.print("Adjon meg egy felhasználónevet: ");
        String username;
        while (true) {
            username = scanner.next();
            if (username != null) break;
        }
        System.out.print("Adja meg a jelszavát: ");
        String password = scanner.next();
        System.out.print("Felhasználó típus(Partnercég 1, Ügyfél 2)");
        Integer userType = scanner.nextInt();

        if (backend.registration(username, password, name, userType)) {
            mainWindow();
        } else {
            FrontendUtil.writeErrorMessage("Nem sikerült a regisztrációt.");
        }
    }

    private void unsubscribeWindow() {
        FrontendUtil.writeWindowHeader("Lejelentkezés hirdetésről");
        Map<JobAd, Subscription> subscriptions = backend.getUserSubscriptions();
        System.out.println("Munka neve | Leírás");
        subscriptions.forEach((job, subscriptionId) -> System.out.println(job.getName() + job.getJobDescription()));
        System.out.print("Írja be a megfelelő sorszámot, hogy lejelentkezzen:");
        Integer index = scanner.nextInt();
        Subscription subscription = new ArrayList<>(subscriptions.values()).get(index);
        backend.unsubscribe(subscription);
    }

    private void userSearchWindow() {
        FrontendUtil.writeWindowHeader("Keresés");

        System.out.println("(1) Hirdetések között");
        System.out.println("(2) Saját jelentkezések");
        Integer menuItem = scanner.nextInt();

        switch (menuItem) {
            case 1:
                searchInJobAds();
                break;
            case 2:

                break;
        }
    }

    private void userMenu() {
        FrontendUtil.writeWindowHeader("Ügyfél menü");
        System.out.println("(1) Keresés\n" +
                "(2) Lejelentkezés hírdetéstről");
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                userSearchWindow();
                break;
            case 2:
                unsubscribeWindow();
                break;
        }
    }

    private void partnerCompanyMenu() {
        FrontendUtil.writeWindowHeader("Partnercég menü");
        System.out.println("(1) Hírdetések keresése\n" +
                "(2) Hírdetés létrehozása\n" +
                "(3) Jelentkező elbírálása\n" +
                "(4) Hírdetés törlése");
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                searchInJobAds();
                break;
            case 2:
                createJobAd();
                break;
            case 3:
                applicantConfiscation();
                break;
            case 4:
                deleteJobAd();
                break;
        }
    }

    private void adminMenu() {
        FrontendUtil.writeWindowHeader("Admin menü");
        System.out.println("(1) Keresés\n" +
                "(2) Statisztika\n" +
                "(3) Hírdetés tölés\n" +
                "(4) Felhasználó törlés\n");
        int choose = scanner.nextInt();
        switch (choose) {
            case 1:
                search();
                break;
            case 2:
                statistics();
                break;
            case 3:
                deleteJobAd();
                break;
            case 4:
                deleteUser();
                break;

        }
    }

    private void listJobAds() {
        System.out.println("\n Hírdetések listázása!\n");
        Boolean isEmpty = backend.listJobAds();

        if (isEmpty) {
            System.out.println("Nincsenek hirdetések.");
        }
    }

    private void deleteUser() {

    }

    private void statistics() {

    }

    private void search() {

    }

    private void applicantConfiscation() {

    }

    private void createJobAd() {

    }

    private void searchInJobAds() {

    }

    private void deleteJobAd() {

    }
}
