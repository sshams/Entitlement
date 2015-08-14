package modules.profile.controller;

import common.ServiceRequest;
import modules.profile.ApplicationFacade;
import modules.profile.model.UserProxy;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {

        ServiceRequest serviceRequest = (ServiceRequest) notification.getBody();
        HttpServletRequest request = serviceRequest.request;
        HttpServletResponse response = serviceRequest.response;

        UserProxy userProxy = (UserProxy) getFacade().retrieveProxy(UserProxy.NAME);
        try {
            serviceRequest.result = userProxy.getUser((int) serviceRequest.result);
            getFacade().sendNotification(ApplicationFacade.PROFILE_RESULT, serviceRequest);
        } catch (Exception exception) {
            serviceRequest.result = exception;
            getFacade().sendNotification(ApplicationFacade.PROFILE_FAULT, serviceRequest);
        }
    }
}
