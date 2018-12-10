package back;

import common.*;
import common.enums.UserType;
import storage.company.CompanyManager;
import storage.handledSubscribtions.HandledSubscriptionsManager;
import storage.jobAd.JobAdManager;
import storage.subscribe.SubscribeManager;
import storage.user.UserManager;

import java.util.*;

import static java.lang.Integer.parseInt;

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
        if (user != null) {
            loginSession = new LoginSession(UUID.randomUUID().toString(), user);
        } else {
            loginSession = new LoginSession(null, user);
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

        String userId = UUID.randomUUID().toString();
        User user = new User(userId, name, username, password, userType);

        if (userType == UserType.PARTNER_COMPANY) {
            Company company = new Company(UUID.randomUUID().toString(), name, userId);
            companyManager.add(company);
        }

        userManager.add(user);
        return true;
    }

    public Boolean listJobAds() {
        if (jobAdManager.list().isEmpty()) {
            return true;
        } else {
            System.out.println("Hirdetés neve | Cég neve | Munka leírás");
            List<JobAd> list = jobAdManager.list();
            for (int i =0; i<list.size(); i++){
                Company adCreator = companyManager.get(list.get(i).getCompanyId());
                System.out.println("(" + i + ")" + list.get(i).getName() + "|" + adCreator.getName() + "|" + list.get(i).getJobDescription());
            }
            return false;
        }
    }

    public Map<JobAd, Subscription> getUserSubscriptions() {
        String userId = loginSession.getUser().getId();
        Map<JobAd, Subscription> jobs = new LinkedHashMap<>();

        subscribeManager.list()
                .stream()
                .filter(subscription -> subscription.getUserId().equals(userId))
                .forEach(subscription -> jobs.put(jobAdManager.get(subscription.getJobAdId()), subscription));
        return jobs;
    }

    public void unsubscribe(List<Subscription> subscription) {
        subscribeManager.delete(subscription);
    }

    public String getCompanyIdByUserId(String userId) {
        return companyManager.getByUserId(userId).getId();
    }

    public void saveJobAd(JobAd jobAd) {
        jobAdManager.add(jobAd);
    }

    public JobAd getJobAd(String id) {
        return jobAdManager.get(id);
    }

    public Company getCompany(String id) {
        return companyManager.get(id);
    }

    public void deleteJobAd(String[] serialNumbers) {
        List<JobAd> jobsToRemove = new ArrayList<>();
        for (int i = 0; i < serialNumbers.length; i++) {
            jobsToRemove.add(jobAdManager.list().get(parseInt(serialNumbers[i])));
        }
        jobAdManager.delete(jobsToRemove);
    }
}
