package modules.entitlement.controller;

import modules.entitlement.view.RouterMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        System.out.println("Entitlement Startup*************");
        getFacade().registerMediator(new RouterMediator());
    }
}
