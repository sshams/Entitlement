package modules.entitlement.controller;

import common.ServiceRequest;
import modules.entitlement.ApplicationFacade;
import modules.entitlement.model.EntitlementProxy;
import modules.entitlement.model.vo.EntitlementVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntitlementCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        ServiceRequest serviceRequest = (ServiceRequest) notification.getBody();
        HttpServletRequest request = serviceRequest.request;
        HttpServletResponse response = serviceRequest.response;

        EntitlementProxy entitlementProxy = (EntitlementProxy) getFacade().retrieveProxy(EntitlementProxy.NAME);

        try {
            switch (request.getServletPath()) {
                case ApplicationFacade.SIGN_IN_WITH_CREDENTIALS:
                    serviceRequest.result = entitlementProxy.signInWithCredentials(request, response);
                    break;
                case ApplicationFacade.RENEW_AUTH_TOKEN:
                    serviceRequest.result = entitlementProxy.renewAuthToken(request, response);
                    break;
                case ApplicationFacade.ENTITLEMENTS:
                    serviceRequest.result = entitlementProxy.entitlements(request, response);
                    break;
                case ApplicationFacade.VERIFY_ENTITLEMENT:
                    serviceRequest.result = entitlementProxy.verifyEntitlement(request, response);
                    break;
            }

            getFacade().sendNotification(ApplicationFacade.ENTITLEMENT_RESULT, serviceRequest);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            entitlementVO.errorMessage = exception.getMessage();
            serviceRequest.result = entitlementVO;
            getFacade().sendNotification(ApplicationFacade.ENTITLEMENT_FAULT, serviceRequest);
        }
    }
}
