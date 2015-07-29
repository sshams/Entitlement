package modules.entitlement.model;

import modules.entitlement.model.vo.EntitlementVO;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class EntitlementProxy extends Proxy {

    public static final String NAME = "EntitlementProxy";

    private User user;
    private Product product;

    public EntitlementProxy() {
        super(NAME, null);
        user = new User();
        product = new Product();
    }

    public EntitlementVO signInWithCredentials(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        if(user.authenticate(request.getParameter("emailAddress"), request.getParameter("password"), null)) {
            UUID authToken = user.setAuthToken(request.getParameter("emailAddress"));
            return new EntitlementVO(true, authToken);
        } else {
            return new EntitlementVO(false, null);
        }
    }

    public EntitlementVO renewAuthToken(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        if(user.authenticate(null, null, request.getParameter("authToken"))) {
            UUID authToken = user.renewAuthToken(request.getParameter("authToken"));
            return new EntitlementVO(true, authToken);
        } else {
            return new EntitlementVO(false, null);
        }
    }

    public EntitlementVO entitlements(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        if(request.getParameter("authToken") != null) {
            int id = user.getId(request.getParameter("authToken"));

            EntitlementVO entitlementVO = new EntitlementVO(true, null);
            entitlementVO.productIds = product.getProductIds(id);
            return entitlementVO;
        } else {
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            return entitlementVO;
        }
    }

    public EntitlementVO verifyEntitlement(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        if(request.getParameter("authToken") != null && request.getParameter("productId") != null) {
            EntitlementVO entitlementVO = new EntitlementVO(true, null);
            int id = user.getId(request.getParameter("authToken"));
            entitlementVO.entitled = Arrays.asList(product.getProductIds(id)).contains(request.getParameter("productId"));
            return entitlementVO;
        } else {
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            return entitlementVO;
        }
    }
}
