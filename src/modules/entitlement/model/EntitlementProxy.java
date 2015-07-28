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

    public EntitlementProxy() {
        super(NAME, null);
    }

    public EntitlementVO signInWithCredentials(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        UserObject userObject = new UserObject();
        if(userObject.authenticate(request.getParameter("emailAddress"), request.getParameter("password"), null)) {
            UUID authToken = userObject.setAuthToken(request.getParameter("emailAddress"));
            return new EntitlementVO(true, authToken);
        } else {
            return new EntitlementVO(false, null);
        }
    }

    public EntitlementVO renewAuthToken(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException {
        UserObject userObject = new UserObject();
        if(userObject.authenticate(null, null, request.getParameter("authToken"))) {
            UUID authToken = userObject.renewAuthToken(request.getParameter("authToken"));
            return new EntitlementVO(true, authToken);
        } else {
            return new EntitlementVO(false, null);
        }
    }

    public EntitlementVO entitlements(HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter("authToken") != null) {
            EntitlementVO entitlementVO = new EntitlementVO(true, this.getUUID());
            entitlementVO.productIds = this.getProductIds();
            return entitlementVO;
        } else {
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            return entitlementVO;
        }
    }

    public EntitlementVO verifyEntitlement(HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter("authToken") != null && request.getParameter("productId") != null) {
            EntitlementVO entitlementVO = new EntitlementVO(true, this.getUUID());

            entitlementVO.entitled = Arrays.asList(this.getProductIds()).contains(request.getParameter("productId"));

            return entitlementVO;
        } else {
            EntitlementVO entitlementVO = new EntitlementVO(false, null);
            return entitlementVO;
        }
    }

    public UUID getUUID() {
        return UUID.randomUUID();
    }

    public String[] getProductIds() {
        return new String[] {"Cheese", "Pepperoni", "Black Olives"};
    }
}
