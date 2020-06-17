package tracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import java.util.ArrayList;

// import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import com.mysql.cj.protocol.Resultset;

@WebServlet("/RecentProducts")
public class RecentProducts extends HttpServlet {

    public static void addProduct(Connection conn, String userID, int productID) {
        // insert new row
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("INSERT INTO session (userID, whenAdded, productID) VALUES (\"%s\", NOW(), %d) ON DUPLICATE KEY UPDATE whenAdded=NOW();", userID, productID);
            System.out.println(sql);
            stmt.executeQuery(sql);
            System.out.println("insert complete");
            // count number of rows in sql
            sql = String.format("SELECT COUNT(*) - 5 AS excess FROM session WHERE userID = \"%s\"", userID);
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int rows = 0;
            if (rs.first())
                rows = rs.getInt("excess");
            rs.close();
            System.out.println(rows);
            // remove old row(s)
            if (rows > 0) {
                sql = String.format("DELETE FROM session WHERE userID = \"%s\" ORDER BY whenAdded asc LIMIT %d", userID, rows);
                System.out.println(sql);
                rs = stmt.executeQuery(sql);
                System.out.println("delete called");
                rs.close();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // tried to add a duplicate row. (productID, userID) is the primary key
            // no-op
            return; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recentlyViewedProducts(PrintWriter out, ArrayList<Product> products) {
        out.println("<b>Recently Viewed:</b><br>");
        out.println("<div class=\"flex row col-100 no-wrap\">");
        if (products.size() > 0) {
            for (Product p : products) {
                out.println("<div class=\"col-15\">");
                out.println(String.format("<a href=\"ProductInfo?id=%d\">", p.getId()));
                out.println(String.format("<img class=\"small-img regular\" src=\"%s%s\" alt=\"%s\" %s>",
                        "http://circinus-10.ics.uci.edu:8080/project2/static/", p.getUrl(),
                        "Requires secure UCI connection",
                        "onmouseover=\"imageSize(this, 250)\" onmouseout=\"imageSize(this, 200)\""));
                out.println(String.format("<p>%s</p></a></div>", p.getName()));
            }
            out.println("</div>");
        } else {
            out.println("<p>No products viewed recently</p></div>");
        }
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // Create a session object if it is already not  created.
        HttpSession session = req.getSession(true);
        
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Connection conn = DBConn.initializeDatabase();
            // prepare reused statements
            PreparedStatement ps_name = conn.prepareStatement("SELECT name from products WHERE id = ?");
            PreparedStatement ps_url = conn.prepareStatement("SELECT url FROM images WHERE id = ?");
            // executed once, use Statement
            Statement st_user_session = conn.createStatement();
            ResultSet rs_user_session = st_user_session.executeQuery(
                String.format("SELECT productID FROM session WHERE userID = \"%s\"", session.getId()));
            // while rows in results
            while(rs_user_session.next()) {
                int productID = rs_user_session.getInt("productID");
                if (productID <= 0) continue; // signals error, all product ids are > 0
                String url = null;
                String name = null;
                ps_url.setInt(1, productID);
                ResultSet rs_url = ps_url.executeQuery();

                if (rs_url.next())
                    url = rs_url.getString("url"); // grab imagepath
                rs_url.close();

                // grab the product name
                ps_name.setInt(1, productID);
                ResultSet rs_name = ps_name.executeQuery();
                if (rs_name.next()) // if exists
                    name = rs_name.getString("name"); // grab product name
                rs_name.close();

                if (name != null && url != null) { // compile listing of viewed products
                    products.add(new Product(productID, name, url));
                }
            }
            // terminate db connections
            st_user_session.close();
            rs_user_session.close();
            ps_url.close();
            ps_name.close();
            conn.close();
            // output html
            recentlyViewedProducts(out, products);
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}