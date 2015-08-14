package shell.controller;

import common.PipeAwareModule;
import common.connections.Entitlement;
import modules.entitlement.EntitlementModule;
import modules.profile.ProfileModule;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.utilities.pipes.plumbing.Pipe;

import javax.servlet.ServletContextEvent;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        ServletContextEvent servletContextEvent = (ServletContextEvent) notification.getBody();

        EntitlementModule entitlementModule = new EntitlementModule();
        servletContextEvent.getServletContext().setAttribute("EntitlementModule", entitlementModule);

        ProfileModule profileModule = new ProfileModule();
        servletContextEvent.getServletContext().setAttribute("ProfileModule", profileModule);

        // ProfileModule -> EntitlementModule
        Pipe pipe = new Pipe();
        profileModule.acceptOutputPipe(EntitlementModule.NAME, pipe);
        entitlementModule.acceptInputPipe(PipeAwareModule.STDIN, pipe);

        // EntitlementModule -> ProfileModule
        pipe = new Pipe();
        entitlementModule.acceptOutputPipe(ProfileModule.NAME, pipe);
        profileModule.acceptInputPipe(PipeAwareModule.STDIN, pipe);
    }
}
