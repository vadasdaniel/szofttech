package hu.office;

import src.front.Frontend;
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
        Frontend frontend = new Frontend();

        frontend.start();
    }

    public Main(
            JobAdManager jobAdManager,
            CompanyManager companyManager,
            HandledSubscriptionsManager handledSubscriptionsManager,
            SubscribeManager subscribeManager,
            UserManager userManager
    ) {
        this.jobAdManager = jobAdManager;
        this.companyManager = companyManager;
        this.handledSubscriptionsManager = handledSubscriptionsManager;
        this.subscribeManager = subscribeManager;
        this.userManager = userManager;
    }
}
