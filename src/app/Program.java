package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import db.DB;
import entities.Order;
import entities.OrderStatus;
import entities.Product;

public class Program {

    public static void main(String[] args) throws SQLException {

        Connection conn = DB.getConnection();
        Statement st = conn.createStatement();

        ResultSet resultSet = st.executeQuery(
                "SELECT * FROM tb_order " +
                        "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id " +
                        "INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");

        Map<Long, Order> mapOrder = new HashMap<>();
        Map<Long, Product> mapProduct = new HashMap<>();

        while (resultSet.next()) {

            Long orderId = resultSet.getLong("order_id");
            if (mapOrder.get(orderId) == null) {
                Order order = instantiateOrder(resultSet);
                mapOrder.put(orderId, order);
            }

            Long productId = resultSet.getLong("product_id");
            if (mapProduct.get(productId) == null) {
                Product product = instantiateProduct(resultSet);
                mapProduct.put(productId, product);
            }

            mapOrder.get(orderId).getProducts().add(mapProduct.get(productId));
        }

        for (Long orderId : mapOrder.keySet()) {
            System.out.println(orderId);

            for (Product product : mapOrder.get(orderId).getProducts()) {
                System.out.println(product);
            }
            System.out.println();
        }
    }

    private static Order instantiateOrder(ResultSet rs) throws SQLException {
        Order order = new Order();

        order.setId(rs.getLong("id"));
        order.setLatitude(rs.getDouble("latitude"));
        order.setLongitude(rs.getDouble("longitude"));
        order.setMoment(rs.getTimestamp("moment").toInstant());
        order.setStatus(OrderStatus.values()[rs.getInt("status")]);

        return order;
    }

    private static Product instantiateProduct(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setDescription(rs.getString("description"));
        product.setImageUri(rs.getString("image_uri"));

        return product;
    }
}
