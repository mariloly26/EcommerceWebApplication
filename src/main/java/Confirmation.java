/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tracking.DBConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mohraarsala
 */
public class Confirmation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            Class.forName("org.mariadb.jdbc.Driver");
            HttpSession session = request.getSession();
            
            Connection con = DBConn.initializeDatabase();
//            PreparedStatement ps = con.prepareStatement("select * from products where id=" + pid);
//            ResultSet rs = ps.executeQuery();
//            ResultSetMetaData rsmd = rs.getMetaData();

           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Confirmation</title>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Lato&display=swap' rel='stylesheet'>");
            out.println("<link rel='stylesheet' href='template/base/responsive.css'>");
            out.println("<link rel='stylesheet' href='template/product/product.css'>");
            out.println("<link rel='stylesheet' type='text/css' href='template/base/topnav.css' />");
            out.println("<link rel='stylesheet' href='confirmation.css'>");
            out.println("<script src='template/base/topnav.js'></script> ");           
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=topnav></div>");
            out.println("<h1 style='text-align: center;'>Thanks for the order, " + request.getParameter("FirstName")+ ".");
            
            out.println("</h1>");
            out.println("<div class='conf'>");
            out.println("<h2>ORDER CONFIRMATION</h2>");
            out.println("<div class=prodpricewrapper>");
            // out.println("<div class='prodcard'>");
           
            // TRANSLATE 109

            //
            // out.println("</div>");
            out.println("<div class='proddetails'>");
            // TRANSLATE 111 - 115
            // PRODUCT DETAILS (NAME, QTY, PRICE)
            double order_total = 0.00;
            Map<Integer, Integer> cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
    
            out.println("<table style='width:100%'>");
            out.println("<tr>");
            out.println("<th class='leftalign'>Item</th>");
            out.println("<th class='rightalign'>Quantity</th>");
            out.println("<th class='rightalign'>Price</th>");
            out.println("</tr>");
            NumberFormat formatter = new DecimalFormat("#0.00");
            for (Map.Entry<Integer,Integer> entry : cart.entrySet())
            {
                PreparedStatement ps = con.prepareStatement("select * from products where id="+entry.getKey());
                ResultSet rs = ps.executeQuery();
                rs.next();
                String name = (String) rs.getObject("name");
                BigDecimal big = (BigDecimal) rs.getObject("price");
                double price = big.doubleValue();
                out.println("<tr>");
                out.println("<td class='leftalign'> <span id='itemname"+entry.getKey()+"'>"+name+"</span></td>");
                out.println("<td class='rightalign'> <span id='itemqty"+entry.getKey()+"'>"+entry.getValue()+"</span></td>");
                out.println("<td class='rightalign'> $<span id='itemprice"+entry.getKey()+"'>"+formatter.format(price)+"</span></td>");
                out.println("</tr>");
                order_total += entry.getValue() * price;
            }
             PreparedStatement ps = con.prepareStatement("select * from tax_rates where zip="+request.getParameter("Zip"));
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            con.close();
            double rate = (double)rs.getObject("rate");
//            double rate = big.doubleValue();
            

            // SUBTOTAL
            out.println("</table>");
            out.println("<hr>");
            out.println("<table style='width:100%'>");
            // SUBTOTAL
            // out.println("<tr style='border-bottom: 1px solid black;'>");
            //     out.println("<td colspan='100%'></td>");
            // out.println("</tr>");
            out.println("<tr>");
            out.println("<td class='leftalign'>Subtotal</td>");
            out.println("<td class='rightalign'></td>");
            out.println("<td class='rightalign'>$" + formatter.format(order_total) + "</td>");
            out.println("</tr>");

            // TAX
            out.println("<tr>");
            out.println("<td class='leftalign'>"+ request.getParameter("State") +" Sales Tax</td>");
            out.println("<td class='rightalign'></td>");
            out.println("<td class='rightalign'>" + formatter.format(100*rate) + "%</td>");
            out.println("</tr>");

            // GRAND TOTAL
            out.println("<tr>");
            out.println("<td class='leftalign'>Order Total</td>");
            out.println("<td class='rightalign'></td>");
            out.println("<td class='rightalign'>$" + formatter.format(order_total * (1 + rate)) + "</td>");
            out.println("</tr>");

            out.println("</table>");
//            out.println("<hr>");
//            out.println("<h3 style='float:left;width:75%;'>Order Total: </h3>");
//            BigDecimal bd = new BigDecimal(Double.toString(order_total));
//
//            out.println("<h3 style='float:right;width:25%;'>$"+formatter.format(order_total)+"</h3>");
//            out.println("<h3 style='float:right;width:25%;'>$"+round(order_total, 2)+"</h3>");
            // END TRANSLATE 111 - 115
            
            out.println("</div>");
            out.println("</div>");
            out.println("<hr>");
            out.println("<div class='orderdetails'>");
            out.println("<h3>Shipping & Payment Information</h3>");
            out.println("<div class='shipping'>");
            out.println("<h3>Shipping</h4>");
            // TRANSLATE 123 - 131
            out.println("<p>" + request.getParameter("FirstName") + " " + request.getParameter("LastName") + "</p>");
            out.println("<p>" + request.getParameter("Email") + "</p>");
            out.println("<p>" + request.getParameter("Address") + "</p>");
            if (request.getParameter("Apartment").length()>0)
                out.println("<p>Apt. " + request.getParameter("Apartment") + "</p>");
            out.println("<p>" + request.getParameter("City") + ", " + request.getParameter("State") + "</p>");
            out.println("<p>" + request.getParameter("Zip") + "</p>");
            out.println("<p>" + request.getParameter("Shipping") + "</p>");
            // END TRANSLATE 123 - 131

            out.println("</div>");
             out.println("<div class='payment'>");
            out.println("<h3>Payment</h4>");
            // TRANSLATE 134 - 136
            out.println("<p>" + request.getParameter("CCName") + "</p>");
            out.println("<p>**** **** **** " + request.getParameter("CCNum").substring(request.getParameter("CCNum").length() - 4) + "</p>");
            // TRANSLATE 134 - 136

            //
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
            session.removeAttribute("cart");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
