package modules.entitlement.model;

import common.connections.Entitlement;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    public String[] getProductIds(int id) throws SQLException, NamingException {
        Connection connection = Entitlement.getInstance().getConnection();

        String sql = "SELECT product.name AS productId FROM product INNER JOIN user_product ON product.id = user_product.product_id WHERE user_product.user_id = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setInt(1, id);
        ResultSet resultSet = query.executeQuery();
        connection.close();

        resultSet.last();
        String[] productIds = new String[resultSet.getRow()];
        resultSet.beforeFirst();

        while(resultSet.next()) {
            productIds[resultSet.getRow() - 1] = resultSet.getString("productId");
        }
        return productIds;
    }

}
