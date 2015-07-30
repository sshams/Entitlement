package modules.entitlement;

import modules.entitlement.controller.StartupCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

public class ApplicationFacade extends Facade {

    public static final String STARTUP = "startup";

    public static final String SIGN_IN_WITH_CREDENTIALS = "/SignInWithCredentials";
    public static final String RENEW_AUTH_TOKEN = "/RenewAuthToken";
    public static final String ENTITLEMENTS = "/entitlements";
    public static final String VERIFY_ENTITLEMENT = "/verifyEntitlement";

    public static final String ENTITLEMENT = "entitlement";
    public static final String ENTITLEMENT_RESULT = "entitlement/result";
    public static final String ENTITLEMENT_FAULT = "entititlement/fault";

    public ApplicationFacade(String key) {
        super(key);
    }

    @Override
    protected void initializeController() {
        super.initializeController();
        registerCommand(STARTUP, new StartupCommand());
    }

    public static ApplicationFacade getInstance(String key) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, new ApplicationFacade(key));
        }
        return (ApplicationFacade) instanceMap.get(key);
    }

    public void startup() {
        sendNotification(STARTUP);
    }
}
