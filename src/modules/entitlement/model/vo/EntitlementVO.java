package modules.entitlement.model.vo;

import java.util.UUID;

public class EntitlementVO {

    public boolean authenticated = false; //common

    public UUID authToken; //SignInWithCredentials, RenewAuthToken

    public String[] productIds; //Entitlements

    public boolean entitled; //VerifyEntitlement

    public int errorCode; //fault
    public String errorMessage; //fault

    public EntitlementVO(boolean authenticated, UUID authToken) {
        this.authenticated = authenticated;
        this.authToken = authToken;
    }
}
