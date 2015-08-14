package modules.entitlement.controller;

import common.ServiceRequest;
import modules.entitlement.ApplicationFacade;
import modules.entitlement.model.UserProxy;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class AuthenticateCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        ServiceRequest serviceRequest = (ServiceRequest) notification.getBody();
        HttpServletRequest request = serviceRequest.request;
        HttpServletResponse response = serviceRequest.response;

        UserProxy userProxy = (UserProxy) getFacade().retrieveProxy(UserProxy.NAME);
        try {
            serviceRequest.result = userProxy.getId(request.getParameter("authToken"));
            getFacade().sendNotification(ApplicationFacade.AUTHENTICATE_RESULT, serviceRequest);
        } catch (Exception exception) {
            serviceRequest.result = exception;
            getFacade().sendNotification(ApplicationFacade.AUTHENTICATE_FAULT, serviceRequest);
        }
    }
}
