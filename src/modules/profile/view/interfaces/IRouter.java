package modules.profile.view.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRouter {
    void profile(HttpServletRequest request, HttpServletResponse response);
}
