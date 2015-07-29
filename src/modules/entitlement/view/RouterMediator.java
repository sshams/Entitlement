package modules.entitlement.view;

import common.PipeAwareModule;
import common.ServiceRequest;
import modules.entitlement.ApplicationFacade;
import modules.entitlement.model.vo.EntitlementVO;
import modules.entitlement.view.components.Router;
import modules.entitlement.view.interfaces.IRouter;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RouterMediator extends Mediator implements IRouter {

    public static final String NAME = "RouterMediator";

    public RouterMediator() {
        super(NAME, null);
    }

    @Override
    public void onRegister() {
    }

    @Override
    public void entitlement(HttpServletRequest request, HttpServletResponse response) {
        getFacade().sendNotification(ApplicationFacade.ENTITLEMENT, new ServiceRequest(request, response));
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[] {
                PipeAwareModule.ACCEPT_ROUTER,
                ApplicationFacade.ENTITLEMENT_RESULT,
                ApplicationFacade.ENTITLEMENT_FAULT
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        ServiceRequest serviceRequest;
        switch (notification.getName()) {
            case PipeAwareModule.ACCEPT_ROUTER:
                this.setViewComponent(notification.getBody());
                ((Router) this.getViewComponent()).setDelegate(this);
                break;
            case ApplicationFacade.ENTITLEMENT_RESULT:
                serviceRequest = (ServiceRequest) notification.getBody();
                ((Router) this.getViewComponent()).result(serviceRequest.request, serviceRequest.response, serviceRequest.resultData);
                break;
            case ApplicationFacade.ENTITLEMENT_FAULT:
                serviceRequest = (ServiceRequest) notification.getBody();
                ((Router) this.getViewComponent()).fault(serviceRequest.request, serviceRequest.response, serviceRequest.resultData);
                break;
        }
    }
}
