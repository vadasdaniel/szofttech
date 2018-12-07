package office;

import front.Frontend;
import storage.company.CompanyManager;
import storage.handledSubscribtions.HandledSubscriptionsManager;
import storage.jobAd.JobAdManager;
import storage.subscribe.SubscribeManager;
import storage.user.UserManager;


public class Main {

    private JobAdManager jobAdManager;
    private CompanyManager companyManager;
    private HandledSubscriptionsManager handledSubscriptionsManager;
    private SubscribeManager subscribeManager;
    private UserManager userManager;

    public static void main(String[] args) {
        Frontend frontend;
        frontend = new Frontend();

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
