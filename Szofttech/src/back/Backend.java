package back;

import common.*;
import common.enums.SubscriptionStateType;
import common.enums.UserType;
import storage.company.CompanyManager;
import storage.handledSubscribtions.HandledSubscriptionsManager;
import storage.jobAd.JobAdManager;
import storage.subscribe.SubscribeManager;
import storage.user.UserManager;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Company> getCompanies() {
        return companyManager.list();
    }

    public List<JobAd> getJobAds() {
        return jobAdManager.list();
    }

    public List<User> getUsers() {
        return userManager.list();
    }

    public void deleteUser(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        userManager.delete(users);
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

    public List<JobAd> getJobAdsByCompanyId(String id) {
        return jobAdManager.getByCompanyId(id);
    }

    public Company getCompany(String id) {
        return companyManager.get(id);
    }

    public User getUserByUsername(String username) {
        return userManager.getByUsername(username);
    }

    public Company getCompanyBaName(String name) {
        return companyManager.getByName(name);
    }

    public JobAd getJobAdByName(String name) {
        return jobAdManager.getByName(name);
    }

    public JobAd getJobAdByNameAndCompanyId(String name, String companyId) {
        return jobAdManager.getByNameAndCompanyId(name, companyId);
    }

    public User getApplicantByUsername(String username) {

        String userId = loginSession.getUser().getId();
        String companyId = getCompanyIdByUserId(userId);

        List<JobAd> jobAds = getJobAdsByCompanyId(companyId);
        User user = null;

        for (JobAd jobAd : jobAds) {
            List<Subscription> subscriptions = subscribeManager.getByJobId(jobAd.getId());

            for (Subscription subscription: subscriptions) {
                User find = userManager.get(subscription.getUserId());

                if ( find.getUsername().equals(username) ) {
                    user = find;
                }
            }
        }

        return user;
    }

    public void deleteJobAd(String[] serialNumbers) {
        List<JobAd> jobsToRemove = new ArrayList<>();
        for (int i = 0; i < serialNumbers.length; i++) {
            jobsToRemove.add(jobAdManager.list().get(parseInt(serialNumbers[i])));
        }
        jobAdManager.delete(jobsToRemove);
    }

    public List<Subscription> getSubscriptionsByJobAdId(String id) {
        return subscribeManager
                .list()
                .stream()
                .filter(subscription -> subscription.getJobAdId().equals(id))
                .collect(Collectors.toList());
    }

    public User getUserByUserId(String id) {
        return userManager.get(id);
    }

    public void moveSubscriptionToHandled(Subscription subscription, SubscriptionStateType state) {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        subscribeManager.delete(subscriptions);

        Subscription newSubscription = new Subscription(
                UUID.randomUUID().toString(),
                subscription.getJobAdId(),
                subscription.getUserId(),
                state
        );

        handledSubscriptionsManager.add(newSubscription);
    }

    public void subscribe(int serialNum){
        Subscription subscription = new Subscription(UUID.randomUUID().toString(),
                                                    jobAdManager.list().get(serialNum).getId(),
                                                    loginSession.getUser().getId(),
                                                    SubscriptionStateType.ACTIVE);
        subscribeManager.add(subscription);
    }
}
