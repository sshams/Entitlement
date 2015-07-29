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
                case "/SignInWithCredentials":
                    serviceRequest.resultData = entitlementProxy.signInWithCredentials(request, response);
                    break;
                case "/RenewAuthToken":
                    serviceRequest.resultData = entitlementProxy.renewAuthToken(request, response);
                    break;
                case "/entitlements":
                    serviceRequest.resultData = entitlementProxy.entitlements(request, response);
                    break;
                case "/verifyEntitlement":
                    serviceRequest.resultData = entitlementProxy.verifyEntitlement(request, response);
                    break;
            }

            getFacade().sendNotification(ApplicationFacade.ENTITLEMENT_RESULT, serviceRequest);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            entitlementVO.errorMessage = exception.getMessage();
            serviceRequest.resultData = entitlementVO;
            getFacade().sendNotification(ApplicationFacade.ENTITLEMENT_FAULT, serviceRequest);
        }
    }
}
