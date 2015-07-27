package modules.entitlement.view;

import common.PipeAwareModule;
import modules.entitlement.view.components.Router;
import modules.entitlement.view.interfaces.IRouter;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class RouterMediator extends Mediator implements IRouter {

    public static final String NAME = "RouterMediator";

    public RouterMediator() {
        super(NAME, null);
    }

    @Override
    public void onRegister() {
        System.out.println("Register*******************");
        //System.out.println(this.getViewComponent());
        //((Router)this.getViewComponent()).setDelegate(this);
    }

    @Override
    public void hello() {
        System.out.println("HELLO**************************");
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[] {
                PipeAwareModule.ACCEPT_ROUTER
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        switch (notification.getName()) {
            case PipeAwareModule.ACCEPT_ROUTER:
                this.setViewComponent(notification.getBody());
                break;
            default:
                break;
        }
    }
}
