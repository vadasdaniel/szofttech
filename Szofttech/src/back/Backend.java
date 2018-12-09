package back;

import common.Company;
import common.JobAd;
import common.LoginSession;
import common.User;
import common.enums.UserType;
import storage.company.CompanyManager;
import storage.handledSubscribtions.HandledSubscriptionsManager;
import storage.jobAd.JobAdManager;
import storage.subscribe.SubscribeManager;
import storage.user.UserManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Backend {

    private JobAdManager jobAdManager;
    private CompanyManager companyManager;
    private HandledSubscriptionsManager handledSubscriptionsManager;
    private SubscribeManager subscribeManager;
    private UserManager userManager;
    private LoginSession loginSession;

    public Backend() {
        FileManager fileManager = new FileManager();

        this.jobAdManager = new JobAdManager(fileManager);
        this.companyManager = new CompanyManager(fileManager);
        this.handledSubscriptionsManager = new HandledSubscriptionsManager(fileManager);
        this.subscribeManager = new SubscribeManager(fileManager);
        this.userManager = new UserManager(fileManager);
    }

    public boolean loginUser(String username, String password) {
        User user = userManager.getByUsernameAndPassword(username, password);
        if ( user != null ) {
            loginSession = new LoginSession(UUID.randomUUID().toString(), user);
        }

        return loginSession.isUserLoggedIn();
    }

    public User getLoggedInUser() {
        return loginSession.getUser();
    }

    public Boolean registration(String username, String password, String name, Integer userTypeNumber) {
        UserType userType = null;

        switch (userTypeNumber) {
            case 1:
                userType = UserType.PARTNER_COMPANY;
                break;
            case 2:
                userType = UserType.USER;
                break;
        }

        userManager.add(new User(UUID.randomUUID().toString(), name, username, password, userType));

        return true;
    }

    public void listJobAds(){
        System.out.println("Hirdetés neve | Cég neve | Munka leírás");
        jobAdManager.list()
                .forEach(jobAd -> {
                    Company adCreator = companyManager.get(jobAd.getCompanyId());
                    System.out.println(jobAd.getName() + "|" + adCreator.getName() + "|" + jobAd.getJobDescription());
                });
    }

    public Map<JobAd, String> getUserSubscriptions() {
        String userId = loginSession.getUser().getId();
        Map<JobAd, String> jobs = new LinkedHashMap<>();

        subscribeManager.list()
                .stream()
                .filter(subscription -> subscription.getUserId().equals(userId))
                .forEach(subscription -> jobs.put(jobAdManager.get(subscription.getJobAdId()), subscription.getId()));
        return jobs;
    }

    public void unsubscribe(String id) {
        subscribeManager.delete(id);
    }
}
