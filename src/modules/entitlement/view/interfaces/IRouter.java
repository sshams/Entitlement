package modules.entitlement.view.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRouter {
    void entitlement(HttpServletRequest request, HttpServletResponse response);
}
