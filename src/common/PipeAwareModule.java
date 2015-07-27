package common;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.utilities.pipes.interfaces.IPipeAware;
import org.puremvc.java.multicore.utilities.pipes.interfaces.IPipeFitting;
import org.puremvc.java.multicore.utilities.pipes.plumbing.JunctionMediator;

import javax.servlet.http.HttpServlet;

public class PipeAwareModule implements IPipeAware {

    public static final String STDOUT = "standardOutput";

    public static final String STDIN = "standardInput";

    public static final String ACCEPT_ROUTER = "acceptRouter";

    protected IFacade facade;

    public PipeAwareModule(IFacade facade) {
        this.facade = facade;
    }

    @Override
    public void acceptInputPipe(String name, IPipeFitting pipe) {
        facade.sendNotification(JunctionMediator.ACCEPT_INPUT_PIPE, pipe, name);
    }

    @Override
    public void acceptOutputPipe(String name, IPipeFitting pipe) {
        facade.sendNotification(JunctionMediator.ACCEPT_OUTPUT_PIPE, pipe, name);
    }

    public void acceptRouter(HttpServlet router) {
        facade.sendNotification(PipeAwareModule.ACCEPT_ROUTER, router);
    }
}
