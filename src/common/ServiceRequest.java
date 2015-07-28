package common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceRequest {

    public HttpServletRequest request;
    public HttpServletResponse response;
    public Object resultData;


    public ServiceRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

}
