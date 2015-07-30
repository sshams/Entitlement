import shell.ApplicationFacade;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Application implements ServletContextListener {

    public static final String NAME = "Application";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationFacade.getInstance(NAME).startup(servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ContextDestroyed");
    }
}
