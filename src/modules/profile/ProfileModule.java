package modules.profile;

import common.PipeAwareModule;

public class ProfileModule extends PipeAwareModule {

    public static final String NAME = "ProfileModule";

    public ProfileModule() {
        super(ApplicationFacade.getInstance(NAME));
        ((ApplicationFacade) facade).startup();
    }
}
