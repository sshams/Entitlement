import modules.entitlement.EntitlementModule;
import shell.Shell;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Application implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ContextInitialized---------------------");
        servletContextEvent.getServletContext().setAttribute("EntitlementModule", new EntitlementModule());
        servletContextEvent.getServletContext().setAttribute("Shell", new Shell());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ContextDestroyed---------------------");
    }
}
