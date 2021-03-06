package shell;

import org.puremvc.java.multicore.patterns.facade.Facade;
import shell.controller.StartupCommand;

import javax.servlet.ServletContextEvent;

public class ApplicationFacade extends Facade {

    public static final String STARTUP = "startup";

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

    public void startup(ServletContextEvent servletContextEvent) {
        sendNotification(STARTUP, servletContextEvent);
    }
}
