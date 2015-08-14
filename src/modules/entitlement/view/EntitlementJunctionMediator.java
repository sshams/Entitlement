package modules.entitlement.view;

import modules.entitlement.ApplicationFacade;
import modules.entitlement.EntitlementModule;
import modules.profile.ProfileModule;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.utilities.pipes.interfaces.IPipeMessage;
import org.puremvc.java.multicore.utilities.pipes.messages.Message;
import org.puremvc.java.multicore.utilities.pipes.plumbing.Junction;
import org.puremvc.java.multicore.utilities.pipes.plumbing.JunctionMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class EntitlementJunctionMediator extends JunctionMediator {

    public static final String NAME = "EntitlementJunctionMediator";

    public EntitlementJunctionMediator() {
        super(NAME, new Junction());
    }

    @Override
    public String[] listNotificationInterests() {
        ArrayList<String> interests = new ArrayList<String>(Arrays.asList(super.listNotificationInterests()));
        interests.add(ApplicationFacade.AUTHENTICATE_RESULT);
        interests.add(ApplicationFacade.AUTHENTICATE_FAULT);
        return interests.toArray(new String[interests.size()]);
    }

    @Override
    public void handleNotification(INotification note) {
        switch (note.getName()) {
            case ApplicationFacade.AUTHENTICATE_RESULT:
                this.getJunction().sendMessage(ProfileModule.NAME, new Message(EntitlementModule.AUTHENTICATE_RESULT, null, note.getBody(), 1));
                break;

            case ApplicationFacade.AUTHENTICATE_FAULT:
                this.getJunction().sendMessage(ProfileModule.NAME, new Message(EntitlementModule.AUTHENTICATE_FAULT, null, note.getBody(), 1));
                break;

            default:
                super.handleNotification(note);
        }
    }

    @Override
    public void handlePipeMessage(IPipeMessage message) {
        getFacade().sendNotification(ApplicationFacade.AUTHENTICATE, message.getBody());
    }
}
