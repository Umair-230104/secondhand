package app.persistence;

import app.entities.Item;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static List<Item> getItems(Context ctx) throws DatabaseException {
        List<Item> items = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT * FROM items"; // Removed the 'public.' as the schema is specified in the connection URL

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String author = rs.getString("author");
                String title = rs.getString("title");
                String body = rs.getString("body");
                int price = rs.getInt("price");
                items.add(new Item(id, author, title, body, price));
            }
        } catch (SQLException e) {
            // Here we convert SQLException to a custom DatabaseException
            // This can help to abstract the SQLException from the controllers that use this method
            throw new DatabaseException("Error accessing the database: " + e.getMessage());
        }
        return items;
    }
}
