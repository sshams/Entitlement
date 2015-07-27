package modules.entitlement;

import common.PipeAwareModule;

public class EntitlementModule extends PipeAwareModule {

    public static final String NAME = "EntitlementModule";

    public EntitlementModule() {
        super(ApplicationFacade.getInstance(NAME));
        ((ApplicationFacade) facade).startup();
    }

}
