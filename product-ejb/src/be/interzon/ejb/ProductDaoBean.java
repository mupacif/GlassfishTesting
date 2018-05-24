package be.interzon.ejb;

import be.interzon.model.Product;

import javax.ejb.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
public class ProductDaoBean {

    private List<Product> products = Arrays.asList(
            new Product(1L,
                    "JavaEE pour tous",
                    "Un bon bouquin",
                    "https://www.packtpub.com/sites/default/files/7942EN.jpg",
                    2500L),
            new Product(2L,
                    "Un climatiseur efficace",
                    "Spécial pour les journées d'été",
                    "https://afrimarket.sn/media/catalog/product/cache/image/560x560/beff4985b56e3afdbeabfc89641a4582/g/r/gree-climatiseur-mural-serie-lomo-12000-btu-seer-16.jpg",
                    25000L),
            new Product(3L,
                    "Avion en papier",
                    "Très aérodynamique",
                    "https://i.ytimg.com/vi/GMAMRzKxNQw/maxresdefault.jpg",
                    1L)
    );

    public List<Product> findAll() {
        // JDBC SELECT * FROM products
//        return products;

        return getProducts();
    }

    public Product findById(Long id) {
        // JDBC SELECT * FROM products WHERE id = ?
//        return products.stream()
//                .filter(p -> p.getId().equals(id))
//                .findFirst()
//                .get();

        return getProduct(id);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {


        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/interzone_db",
                "root","password");

        System.out.println("Connected to database");
        return conn;
    }

    public void insert(Product p){




        String selectTableSQL = "INSERT INTO product(name,description,imageUrl,price) Values "
                +"('"+p.getName()+"','"+p.getDescription()+"','"+p.getImageUrl()+"','"+p.getPrice()+"')";

        System.out.println(selectTableSQL);
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            stmt.executeUpdate(selectTableSQL);
        } catch (SQLException e) {
            System.err.print(e.getSQLState());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    public List<Product> getProducts() {


        Statement statement;
        String selectTableSQL = "Select * from product";
        Connection conn = null;
        List<Product> products = new ArrayList<>();

        try {
            conn = getConnection();
            statement = conn.createStatement();

            System.out.println(selectTableSQL);




            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);


            while (rs.next()) {

                long id = rs.getLong("ID");
                long price = rs.getLong("price");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imageUrl = rs.getString("imageUrl");
                System.out.println(id);
                products.add(new Product(id, name, description, imageUrl, price));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;

    }


    public Product getProduct(long idProduct) {


        Statement statement;
        String selectTableSQL = "Select * from product WHERE ID="+idProduct;
        Connection conn = null;
        List<Product> products = new ArrayList<>();

        try {
            conn = getConnection();
            statement = conn.createStatement();

            System.out.println(selectTableSQL);




            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);


            while (rs.next()) {

                long id = rs.getLong("ID");
                long price = rs.getLong("price");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imageUrl = rs.getString("imageUrl");
                System.out.println(id);
                return new Product(id, name, description, imageUrl, price);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            return null;
    }


}
