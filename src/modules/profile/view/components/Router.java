package modules.profile.view.components;

import com.google.gson.Gson;
import common.Routes;
import modules.profile.model.vo.UserVO;
import modules.profile.view.interfaces.IRouter;
import modules.profile.ProfileModule;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({ Routes.PROFILE})
public class Router extends HttpServlet {

    private IRouter delegate;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ProfileModule profileModule = (ProfileModule) config.getServletContext().getAttribute("ProfileModule");
        profileModule.acceptRouter(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.delegate.profile(request, response);
    }

    public void result(HttpServletRequest request, HttpServletResponse response, UserVO result) {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        try {
            response.setContentType("application/json");
            if(result.id == 0) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{}");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fault(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Gson gson = new Gson();
        try {
            response.getWriter().write(gson.toJson(exception.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDelegate(IRouter delegate) {
        this.delegate = delegate;
    }
}