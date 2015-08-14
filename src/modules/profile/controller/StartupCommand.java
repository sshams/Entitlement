package modules.profile.controller;

import modules.profile.model.UserProxy;
import modules.profile.view.ProfileJunctionMediator;
import modules.profile.view.RouterMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class StartupCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {

        getFacade().registerProxy(new UserProxy());

        getFacade().registerMediator(new ProfileJunctionMediator());
        getFacade().registerMediator(new RouterMediator());
    }
}
