package modules.entitlement.model.vo;

import java.util.UUID;

public class EntitlementVO {

    public boolean authenticated = false;
    public UUID authToken;
    public String[] productIds;
    public boolean entitled;

    public EntitlementVO(boolean authenticated, UUID authToken) {
        this.authenticated = authenticated;
        this.authToken = authToken;
    }
}
