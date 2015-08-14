package modules.profile.model;

import common.connections.Entitlement;
import modules.profile.model.vo.UserVO;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProxy extends Proxy {

    public static final String NAME = "UserProxy";

    public UserProxy() {
        super(NAME, null);
    }

    public UserVO getUser(int id) throws NamingException, SQLException {
        Connection connection = Entitlement.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setInt(1, id);

        ResultSet resultSet = query.executeQuery();
        connection.close();

        if(resultSet.next()) {
            return new UserVO(id, resultSet.getString("first"), resultSet.getString("last"), resultSet.getString("emailAddress"));
        } else {
            return new UserVO();
        }
    }

}
