package front;

import back.Backend;
import common.Company;
import common.JobAd;
import common.Subscription;
import common.User;
import common.enums.SubscriptionStateType;
import common.enums.UserType;

import java.util.*;

public class Frontend {

    private Boolean exitProgram = false;
    private Scanner scanner;
    private Backend backend;
    private Scanner menuScanner;

    public Frontend(Backend backend) {
        this.scanner = new Scanner(System.in);
        this.menuScanner = new Scanner(System.in);
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
        System.out.println("(9) Kilépés");

        int menuItem = menuScanner.nextInt();
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
            case 9:
                setExitProgram(true);
                break;
        }
    }

    private void loginWindow() {
        FrontendUtil.writeWindowHeader("Bejelentkezés");
        System.out.println("Adja meg a felhasználónevét: ");
        String username = scanner.next();
        System.out.println("Adja meg a jelszavát: ");
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

        int menuOption = menuScanner.nextInt();

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

        System.out.println("Adja mega a nevét: ");
        String name = scanner.next();
        System.out.println("Adjon meg egy felhasználónevet: ");
        String username = nullSafeIn();
        System.out.println("Adja meg a jelszavát: ");
        String password = scanner.next();
        System.out.println("Felhasználó típus(Partnercég 1, Ügyfél 2)");
        Integer userType = menuScanner.nextInt();

        if (backend.registration(username, password, name, userType)) {
            mainWindow();
        } else {
            FrontendUtil.writeErrorMessage("Nem sikerült a regisztrációt.");
        }
    }

    private void userMenu() {
        FrontendUtil.writeWindowHeader("Ügyfél menü");
        System.out.println("(1) Keresés");
        System.out.println("(2) Lejelentkezés hírdetéstről");
        System.out.println("(9) Kijelentkezés");
        int choose = menuScanner.nextInt();
        switch (choose) {
            case 1:
                break;
            case 2:
                unsubscribe();
                userMenu();
                break;
            case 9:
                mainWindow();
                break;
        }
    }

    private void partnerCompanyMenu() {
        FrontendUtil.writeWindowHeader("Partnercég menü");
        System.out.println("(1) Keresés");
        System.out.println("(2) Hírdetés létrehozása");
        System.out.println("(3) Jelentkező elbírálása");
        System.out.println("(4) Hírdetés törlése");
        System.out.println("(9) Kijelentkezés");
        int choose = menuScanner.nextInt();
        switch (choose) {
            case 1:
                searchPartnerCompany();
                break;
            case 2:
                createJobAd();
                break;
            case 3:
                applicantConfiscationWindow();
                break;
            case 4:
                deleteJobAd();
                break;
            case 9:
                mainWindow();
                break;
        }
    }

    private void adminMenu() {
        FrontendUtil.writeWindowHeader("Admin menü");
        System.out.println("(1) Keresés");
        System.out.println("(2) Statisztika");
        System.out.println("(3) Hírdetés törlés");
        System.out.println("(4) Felhasználó törlés");
        System.out.println("(9) Kijelentkezés");
        int choose = menuScanner.nextInt();
        switch (choose) {
            case 1:
                searchAdmin();
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
            case 9:
                mainWindow();
                break;
        }
    }

    private void listJobAds() {
        FrontendUtil.writeWindowHeader("Hirdetések listázása");
        Boolean isEmpty = backend.listJobAds();

        if (isEmpty) {
            System.out.println("Nincsenek hirdetések.");
        }
    }

    private void deleteUser() {
        FrontendUtil.writeWindowHeader("Felhasználó törlés");

        List<User> users = backend.getUsers();

        if ( users.isEmpty() ) {
            FrontendUtil.writeWarningMessage("Nem található felhasználó.");
        } else {
            System.out.println("Index | Név | Felhasználónév");
            for ( int i = 0; i < users.size(); i++ ) {
                User user = users.get(i);
                System.out.println(i + "  " + user.getName() + "  " + user.getUsername());
            }

            System.out.println("Felhasználó index: ");
            Integer index = scanner.nextInt();
            User userToDelete = null;
            for ( int i = 0; i < users.size(); i++ ) {
                userToDelete = users.get(index);
            }
            backend.deleteUser(userToDelete);
            adminMenu();
        }
    }

    private void unsubscribe() {
        FrontendUtil.writeWindowHeader("Lejelentkezés hirdetésről");
        Map<JobAd, Subscription> subscriptions = backend.getUserSubscriptions();
        System.out.println("Index | Cég neve | Munka neve | Leírás");
        Integer itemIndex = 0;

        for ( Map.Entry<JobAd, Subscription> entry: subscriptions.entrySet() ) {
            JobAd jobAd = entry.getKey();
            Company company = backend.getCompany(jobAd.getCompanyId());
            System.out.println(itemIndex + company.getName() + jobAd.getName() + jobAd.getJobDescription());
            itemIndex++;
        }

        System.out.print("Írja be a megfelelő sorszámot(kat), hogy lejelentkezzen(vesszővel elválasztva):");
        String[] indexes = scanner.next().split(",");
        List<Subscription> subscriptionList = new ArrayList<>();
        for ( int i = 0; i < subscriptions.values().size(); i++) {
            for ( int k = 0; k < indexes.length; k++ ) {
                Integer index = Integer.valueOf(indexes[k]);
                subscriptionList.add(subscriptions.get(index));
            }
        }
        backend.unsubscribe(subscriptionList);
    }

    private void statistics() {
        FrontendUtil.writeWindowHeader("Statisztika");

        System.out.println("Felhasználók száma: " + backend.getUsers().size());
        System.out.println("Cégek száma: " + backend.getCompanies().size());
        System.out.println("Hirdetések száma: " + backend.getJobAds().size());
        adminMenu();
    }

    private void searchAdmin() {
        FrontendUtil.writeWindowHeader("Admin keresés");
        System.out.println("(1) Felhasználók között");
        System.out.println("(2) Cégek között");
        System.out.println("(3) Hírdetés között");
        Integer menuOption = scanner.nextInt();

        switch (menuOption) {
            case 1:
                FrontendUtil.writeWindowHeader("Felhasználő keresés");
                System.out.print("Felhasználó neve: ");
                String username = scanner.next();
                User user = backend.getUserByUsername(username);

                if ( user.getId() != null ) {
                    System.out.println("\n--- FELHASZNÁLÓ ---");
                    System.out.println("Felhasználónév: " + user.getUsername());
                    System.out.println("Név: " + user.getName());
                    System.out.println("Típus: " + user.getUserType().toString());
                } else {
                    System.out.println("A keresett felhasználó nem található.");
                    adminMenu();
                }
                break;
            case 2:
                FrontendUtil.writeWindowHeader("Cég keresés");
                System.out.print("Cég neve: ");
                String companyName = scanner.next();
                Company company = backend.getCompanyBaName(companyName);

                if ( company.getId() != null ) {
                    System.out.println("\n--- CÉG ---");
                    System.out.println("Név: " + company.getName());
                } else {
                    System.out.println("A keresett cég nem található.");
                    adminMenu();
                }
                break;
            case 3:
                FrontendUtil.writeWindowHeader("Hírdetés keresés");
                System.out.print("Hírdetés neve: ");
                String adName = scanner.next();
                JobAd jobAd = backend.getJobAdByName(adName);

                if ( jobAd.getId() != null ) {
                    Company creator = backend.getCompany(jobAd.getCompanyId());
                    System.out.println("\n--- HÍRDETÉS ---");
                    System.out.println("Név: " + jobAd.getName());
                    System.out.println("Leírás: " + jobAd.getJobDescription());
                    System.out.println("Feladó: " + creator.getName());
                } else {
                    System.out.println("A keresett hírdetés nem található.");
                    adminMenu();
                }
                break;
        }

    }

    private void applicantConfiscationWindow() {
        FrontendUtil.writeWindowHeader("Jelentkezés elbírálás");

        String userId = backend.getLoggedInUser().getId();
        String companyId = backend.getCompanyIdByUserId(userId);
        List<JobAd> jobAds = backend.getJobAdsByCompanyId(companyId);

        System.out.println("Index | Hírdetés neve | Jelentkezők száma");

        for ( int i = 0; i < jobAds.size(); i++ ) {
            JobAd jobAd = jobAds.get(i);
            List<Subscription> subscriptions = backend.getSubscriptionsByJobAdId(jobAd.getId());
            System.out.println(i + " | " + jobAd.getName() + " | " + subscriptions.size());
        }

        System.out.println("Hírdetés indexe: ");
        Integer index = scanner.nextInt();

        applicantConfiscation(jobAds.get(index));
        partnerCompanyMenu();
    }

    private void applicantConfiscation(JobAd jobAd) {
        List<Subscription> subscriptions = backend.getSubscriptionsByJobAdId(jobAd.getId());

        System.out.println("Index | Jelentkező");
        for ( int i = 0; i < subscriptions.size(); i++ ) {
            Subscription subscription = subscriptions.get(i);
            User user = backend.getUserByUserId(subscription.getUserId());
            System.out.println(i + " | " + user.getName());
        }

        System.out.println("Jelentkező indexe: ");
        Integer index = scanner.nextInt();
        System.out.println("1 - Elfogadás | 2 - Elutasítás");
        Integer choice = scanner.nextInt();

        SubscriptionStateType state = null;

        switch (choice) {
            case 1:
                state = SubscriptionStateType.ACCEPTED;
                break;
            case 2:
                state = SubscriptionStateType.DENIED;
                break;
        }

        backend.moveSubscriptionToHandled(subscriptions.get(index), state);
    }

    private void createJobAd() {
        System.out.println("Kérem adja meg a következő adatokat!\nHírdetés neve:");
        String adName = nullSafeIn();
        System.out.println("Hírdetés leírása:");
        String adDesc = nullSafeIn();

        JobAd jobAd = new JobAd(UUID.randomUUID().toString(),
                                backend.getCompanyIdByUserId(backend.getLoggedInUser().getId()),
                                adName,
                                adDesc);

        backend.saveJobAd(jobAd);
        partnerCompanyMenu();
    }

    private void searchPartnerCompany() {
        FrontendUtil.writeWindowHeader("Kereső");
        System.out.println("(1) Saját hírdetés");
        System.out.println("(2) Jelentkezők");
        Integer menuOption = scanner.nextInt();

        switch (menuOption) {
            case 1:
                System.out.print("Hírdetés neve: ");
                String adName = scanner.next();
                String companyId = backend.getCompanyIdByUserId(backend.getLoggedInUser().getId());
                JobAd jobAd = backend.getJobAdByNameAndCompanyId(adName, companyId);

                if ( jobAd.getId() != null ) {
                    System.out.println("\n--- HÍRDETÉS ---");
                    System.out.println("Név: " + jobAd.getName());
                    System.out.println("Leírás: " + jobAd.getJobDescription());
                } else {
                    System.out.println("A keresett hírdetés nem található.");
                    partnerCompanyMenu();
                }
                break;
            case 2:
                System.out.print("Jelentkező neve: ");
                String applicant = scanner.next();

                User user = backend.getApplicantByUsername(applicant);
                if (user != null) {
                    System.out.println("\n--- FELHASZNÁLÓ ---");
                    System.out.println("Név: " + user.getName());
                    System.out.println("Felhasználónév: " + user.getUsername());
                } else {
                    System.out.println("A keresett jelentkező nem található");
                    partnerCompanyMenu();
                }
                break;
        }
    }

    private void deleteJobAd() {
        if(backend.listJobAds()){
            System.out.println("Nincs hírdetés.");
            if (backend.getLoggedInUser().getUserType() == UserType.PARTNER_COMPANY){
                partnerCompanyMenu();
            } else {
                adminMenu();
            }
        }
        else{
            System.out.println("Kérem a törölni kívánt hírdetés sorszámait ( ','-vel elválasztva)");
            String serialNumbers = scanner.next();
            if (serialNumbers != null) {
                String[] serialNumbersArr = serialNumbers.split(",");
                backend.deleteJobAd(serialNumbersArr);
                if (backend.getLoggedInUser().getUserType() == UserType.PARTNER_COMPANY){
                    partnerCompanyMenu();
                } else {
                    adminMenu();
                }
            } else {
                if (backend.getLoggedInUser().getUserType() == UserType.PARTNER_COMPANY){
                    partnerCompanyMenu();
                } else {
                    adminMenu();
                }
            }
        }
    }

    private String nullSafeIn(){
        String tmp;
        while (true) {
            tmp = scanner.next();
            if (tmp != null) break;
        }
        return tmp;
    }
}
