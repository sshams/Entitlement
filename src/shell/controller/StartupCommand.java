package shell.controller;

import modules.entitlement.EntitlementModule;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import javax.servlet.ServletContextEvent;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        ServletContextEvent servletContextEvent = (ServletContextEvent) notification.getBody();

        EntitlementModule entitlementModule = new EntitlementModule();
        servletContextEvent.getServletContext().setAttribute("EntitlementModule", entitlementModule);
    }
}
