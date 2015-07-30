package common.connections;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Entitlement {

    private static Entitlement instance;

    private DataSource dataSource;

    public Entitlement() throws NamingException {
        InitialContext context = new InitialContext();
        this.dataSource = (DataSource) context.lookup("jdbc/entitlement");
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public static Entitlement getInstance() throws NamingException {
        if(instance == null) {
            instance = new Entitlement();
        }
        return instance;
    }

}
