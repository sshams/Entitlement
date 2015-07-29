package modules.entitlement.view.components;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.gson.Gson;
import modules.entitlement.EntitlementModule;
import modules.entitlement.model.vo.EntitlementVO;
import modules.entitlement.view.interfaces.IRouter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({ "/SignInWithCredentials", "/RenewAuthToken", "/entitlements", "/verifyEntitlement" })
public class Router extends HttpServlet {

    private IRouter delegate;
    private Mustache success;
    private Mustache error;
    private Mustache productIds;
    private Mustache verify;

    private boolean state = true;

    @Override
    public void init(ServletConfig config) throws ServletException {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        this.success = mustacheFactory.compile("modules/entitlement/view/templates/success.xml");
        this.error = mustacheFactory.compile("modules/entitlement/view/templates/error.xml");
        this.productIds = mustacheFactory.compile("modules/entitlement/view/templates/entitlements.xml");
        this.verify = mustacheFactory.compile("modules/entitlement/view/templates/verifyEntitlement.xml");

        EntitlementModule entitlementModule = (EntitlementModule) config.getServletContext().getAttribute("EntitlementModule");
        entitlementModule.acceptRouter(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.delegate.entitlement(request, response);
    }

    public void result(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        switch (request.getServletPath()) {
            case "/SignInWithCredentials":
                this.signInWithCredentials(request, response, (EntitlementVO) entitlementVO);
                break;
            case "/RenewAuthToken":
                this.renewAuthToken(request, response, (EntitlementVO) entitlementVO);
                break;
            case "/entitlements":
                this.entitlements(request, response, (EntitlementVO) entitlementVO);
                break;
            case "/verifyEntitlement":
                this.verifyEntitlement(request, response, (EntitlementVO) entitlementVO);
                break;
        }
    }

    public void fault(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            this.error.execute(response.getWriter(), entitlementVO).flush();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void signInWithCredentials(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        try {
            if(entitlementVO.authenticated) {
                this.authenticate(request, response, entitlementVO);

                if(request.getHeader("accept").equals("application/json")) {
                    Gson gson = new Gson();
                    String json = gson.toJson(entitlementVO);
                    response.getWriter().write(json);
                } else {
                    this.success.execute(response.getWriter(), entitlementVO).flush();
                }
            } else {
                this.deny(request, response, entitlementVO);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void renewAuthToken(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        try {
            if(entitlementVO.authenticated) {
                this.authenticate(request, response, entitlementVO);
                this.success.execute(response.getWriter(), entitlementVO).flush();
            } else {
                this.deny(request, response, entitlementVO);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void entitlements(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        try {
            if(entitlementVO.authenticated) {
                this.authenticate(request, response, entitlementVO);
                this.productIds.execute(response.getWriter(), entitlementVO).flush();
            } else {
                this.deny(request, response, entitlementVO);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void verifyEntitlement(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) {
        try {
            if(entitlementVO.authenticated) {
                this.authenticate(request, response, entitlementVO);
                this.verify.execute(response.getWriter(), entitlementVO).flush();
            } else {
                this.deny(request, response, entitlementVO);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void authenticate(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) throws IOException { //json + xml
        response.setStatus(HttpServletResponse.SC_OK);
        if(request.getHeader("accept").equals("application/json")) {
            response.setContentType("application/json");
        } else {
            response.setContentType("application/xml");
        }
    }

    public void deny(HttpServletRequest request, HttpServletResponse response, EntitlementVO entitlementVO) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/xml");
        this.error.execute(response.getWriter(), entitlementVO).flush();
    }

    public void setDelegate(IRouter delegate) {
        this.delegate = delegate;
    }
}
