package modules.profile.view;

import modules.entitlement.EntitlementModule;
import modules.profile.ApplicationFacade;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.utilities.pipes.interfaces.IPipeMessage;
import org.puremvc.java.multicore.utilities.pipes.messages.Message;
import org.puremvc.java.multicore.utilities.pipes.plumbing.Junction;
import org.puremvc.java.multicore.utilities.pipes.plumbing.JunctionMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileJunctionMediator extends JunctionMediator {

    public static final String NAME = "ProfileJunctionMediator";

    public ProfileJunctionMediator() {
        super(NAME, new Junction());
    }

    @Override
    public String[] listNotificationInterests() {
        ArrayList<String> interests = new ArrayList<String>(Arrays.asList(super.listNotificationInterests()));
        interests.add(ApplicationFacade.AUTHENTICATE);
        return interests.toArray(new String[interests.size()]);
    }

    @Override
    public void handleNotification(INotification note) {
        switch (note.getName()) {
            case ApplicationFacade.AUTHENTICATE:
                this.getJunction().sendMessage(EntitlementModule.NAME, new Message(EntitlementModule.AUTHENTICATE, null, note.getBody(), 1));
                break;

            default:
                super.handleNotification(note);
        }
    }

    @Override
    public void handlePipeMessage(IPipeMessage message) {
        switch (message.getType()) {
            case EntitlementModule.AUTHENTICATE_RESULT:
                getFacade().sendNotification(ApplicationFacade.PROFILE, message.getBody());
                break;
            case EntitlementModule.AUTHENTICATE_FAULT:
                getFacade().sendNotification(ApplicationFacade.PROFILE_FAULT, message.getBody());
                break;
        }
    }
}
