package src.hu.office;

import src.back.Backend;
import src.back.FileManager;
import src.common.LoginSession;
import src.storage.company.CompanyManager;
import src.storage.handledSubscribtions.HandledSubscriptionsManager;
import src.storage.jobAd.JobAdManager;
import src.storage.subscribe.SubscribeManager;
import src.storage.user.UserManager;

public class Main {

    private JobAdManager jobAdManager;
    private CompanyManager companyManager;
    private HandledSubscriptionsManager handledSubscriptionsManager;
    private SubscribeManager subscribeManager;
    private UserManager userManager;

    public static void main(String[] args) {
	// write your code here
    }

    public Main() {
        Backend backend = new Backend();
        FileManager fileManager = new FileManager();

        jobAdManager = new JobAdManager(fileManager);
        companyManager = new CompanyManager(fileManager);
        handledSubscriptionsManager = new HandledSubscriptionsManager(fileManager);
        subscribeManager = new SubscribeManager(fileManager);
        userManager = new UserManager(fileManager);
    }

    private void start() {
        jobAdManager.readData();
        companyManager.readData();
        handledSubscriptionsManager.readData();
        subscribeManager.readData();
        userManager.readData();
    }
}
