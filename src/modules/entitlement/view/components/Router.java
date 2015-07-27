package modules.entitlement.view.components;

import modules.entitlement.EntitlementModule;
import modules.entitlement.view.interfaces.IRouter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value= "/", name = "HelloWorld")
public class Router extends HttpServlet {

    private IRouter delegate;

    @Override
    public void init(ServletConfig config) throws ServletException {
        EntitlementModule entitlementModule = (EntitlementModule) config.getServletContext().getAttribute("EntitlementModule");
        System.out.println("Router********************");
        entitlementModule.acceptRouter(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HELLO WORLD********************");
        response.getWriter().write("Hello World");
        //this.delegate.hello();
    }

    public void setDelegate(IRouter delegate) {
        System.out.println("SET DELEGATE********************");
        //this.delegate = delegate;
    }
}
