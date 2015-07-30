package modules.entitlement.controller;

import modules.entitlement.ApplicationFacade;
import modules.entitlement.model.EntitlementProxy;
import modules.entitlement.model.ProductProxy;
import modules.entitlement.model.UserProxy;
import modules.entitlement.view.RouterMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        getFacade().registerCommand(ApplicationFacade.ENTITLEMENT, new EntitlementCommand());

        ProductProxy productProxy = new ProductProxy();
        UserProxy userProxy = new UserProxy();
        getFacade().registerProxy(productProxy);
        getFacade().registerProxy(userProxy);

        getFacade().registerProxy(new EntitlementProxy(productProxy, userProxy));
        getFacade().registerMediator(new RouterMediator());
    }
}
