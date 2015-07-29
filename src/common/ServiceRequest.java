package common;

import modules.entitlement.model.vo.EntitlementVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceRequest {

    public HttpServletRequest request;
    public HttpServletResponse response;
    public EntitlementVO resultData;

    public ServiceRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

}
