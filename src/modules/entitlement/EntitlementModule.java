package modules.entitlement;

import common.PipeAwareModule;

public class EntitlementModule extends PipeAwareModule {

    public static final String NAME = "EntitlementModule";

    public static final String AUTHENTICATE = "authenticate";
    public static final String AUTHENTICATE_RESULT = "authenticate/result";
    public static final String AUTHENTICATE_FAULT = "authenticate/fault";

    public EntitlementModule() {
        super(ApplicationFacade.getInstance(NAME));
        ((ApplicationFacade) facade).startup();
    }

}
