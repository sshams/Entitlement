package modules.entitlement;

import modules.entitlement.controller.AuthenticateCommand;
import modules.entitlement.controller.StartupCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

public class ApplicationFacade extends Facade {

    public static final String STARTUP = "startup";

    public static final String ENTITLEMENT = "entitlement";
    public static final String ENTITLEMENT_RESULT = "entitlement/result";
    public static final String ENTITLEMENT_FAULT = "entititlement/fault";

    public static final String AUTHENTICATE = "entitlement/authenticate";
    public static final String AUTHENTICATE_RESULT = "entitlement/authenticate/result";
    public static final String AUTHENTICATE_FAULT = "entitlement/authenticate/fault";

    public ApplicationFacade(String key) {
        super(key);
    }

    @Override
    protected void initializeController() {
        super.initializeController();
        registerCommand(STARTUP, new StartupCommand());
        registerCommand(AUTHENTICATE, new AuthenticateCommand());
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
