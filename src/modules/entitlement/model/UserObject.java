package modules.entitlement.model;

import common.connections.Entitlement;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserObject {

    public static final String NAME = "UserObject";

    public boolean authenticate(String emailAddress, String password, String authToken) throws NamingException, SQLException {
        Connection connection = Entitlement.getInstance().getConnection();
        String sql = null;
        PreparedStatement query = null;

        if(emailAddress != null && password != null) {
            sql = "SELECT * FROM user WHERE emailAddress = ? AND password = ?";
            query = connection.prepareStatement(sql);
            query.setString(1, emailAddress);
            query.setString(2, password);
        } else if(authToken != null) {
            sql = "SELECT * FROM user WHERE authToken = ?";
            query = connection.prepareStatement(sql);
            query.setString(1, authToken);
        } else {
            return false;
        }

        ResultSet resultSet = query.executeQuery();
        connection.close();

        if(resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    public UUID renewAuthToken(String authToken) throws NamingException, SQLException {
        Connection connection = Entitlement.getInstance().getConnection();

        String sql = "UPDATE user SET authToken = ? WHERE authToken = ?";
        PreparedStatement query = connection.prepareStatement(sql);

        UUID uuid = this.getUUID();
        query.setString(1, String.valueOf(uuid));
        query.setString(2, authToken);

        query.executeUpdate();
        connection.close();
        return uuid;
    }

    public UUID setAuthToken(String emailAddress) throws NamingException, SQLException {
        Connection connection = Entitlement.getInstance().getConnection();

        String sql = "UPDATE user SET authToken = ? WHERE emailAddress = ?";
        PreparedStatement query = connection.prepareStatement(sql);

        UUID uuid = this.getUUID();
        query.setString(1, String.valueOf(uuid));
        query.setString(2, emailAddress);

        query.executeUpdate();
        connection.close();
        return uuid;
    }

    public UUID getUUID() {
        return UUID.randomUUID();
    }

}
