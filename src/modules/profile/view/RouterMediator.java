package modules.profile.view;

import common.PipeAwareModule;
import common.ServiceRequest;
import modules.profile.ApplicationFacade;
import modules.profile.model.vo.UserVO;
import modules.profile.view.components.Router;
import modules.profile.view.interfaces.IRouter;
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
    public void profile(HttpServletRequest request, HttpServletResponse response) {
        getFacade().sendNotification(ApplicationFacade.AUTHENTICATE, new ServiceRequest(request, response));
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[] {
                PipeAwareModule.ACCEPT_ROUTER,
                ApplicationFacade.PROFILE_RESULT,
                ApplicationFacade.PROFILE_FAULT
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
            case ApplicationFacade.PROFILE_RESULT:
                serviceRequest = (ServiceRequest) notification.getBody();
                ((Router) this.getViewComponent()).result(serviceRequest.request, serviceRequest.response, (UserVO) serviceRequest.result);
                break;
            case ApplicationFacade.PROFILE_FAULT:
                serviceRequest = (ServiceRequest) notification.getBody();
                ((Router) this.getViewComponent()).fault(serviceRequest.request, serviceRequest.response, (Exception) serviceRequest.result);
                break;
        }
    }
}
