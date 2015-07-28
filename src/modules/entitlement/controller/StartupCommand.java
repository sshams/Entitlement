package modules.entitlement.controller;

import modules.entitlement.ApplicationFacade;
import modules.entitlement.model.EntitlementProxy;
import modules.entitlement.view.RouterMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        getFacade().registerCommand(ApplicationFacade.ENTITLEMENT, new EntitlementCommand());

        getFacade().registerProxy(new EntitlementProxy());
        getFacade().registerMediator(new RouterMediator());
    }
}
