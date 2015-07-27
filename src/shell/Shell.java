package shell;

public class Shell {

    public static final String NAME = "shell";

    public Shell() {
        ApplicationFacade.getInstance(NAME).startup();
    }
}
