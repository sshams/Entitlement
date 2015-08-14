package modules.profile;

import modules.profile.controller.ProfileCommand;
import modules.profile.controller.StartupCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

public class ApplicationFacade extends Facade {

    public static final String STARTUP = "startup";

    public static final String AUTHENTICATE = "authenticate";
    public static final String AUTHENTICATE_FAULT = "authenticate/fault";

    public static final String PROFILE = "profile";
    public static final String PROFILE_RESULT = "profile/result";
    public static final String PROFILE_FAULT = "profile/fault";

    public ApplicationFacade(String key) {
        super(key);
    }

    @Override
    protected void initializeController() {
        super.initializeController();
        registerCommand(STARTUP, new StartupCommand());
        registerCommand(PROFILE, new ProfileCommand());
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
