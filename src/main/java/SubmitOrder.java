/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tracking.DBConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
/**
 *
 * @author mohraarsala
 */
public class SubmitOrder extends HttpServlet {

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
        Connection con = DBConn.initializeDatabase();
        try{
//            con.setAutoCommit(false);
        
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO orders "
                    + "(first_name, last_name, email, phone, "
                    + "address, apartment, city, state, zip, "
                    + "cc_name, cc_number, cvv, cc_month, cc_year, shipping)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            out.println(request.getQueryString());
            ps.setString( 1, request.getParameter("FirstName"));
            ps.setString (2, request.getParameter("LastName"));
            ps.setString( 3, request.getParameter("Email"));
            ps.setString( 4, request.getParameter("PhoneNum"));
            ps.setString( 5, request.getParameter("Address"));
            ps.setString( 6, request.getParameter("Apartment"));
            ps.setString( 7, request.getParameter("City"));
            ps.setString( 8, request.getParameter("State"));
            ps.setString( 9, request.getParameter("Zip"));
            ps.setString(10, request.getParameter("CCName"));
            ps.setString(11, request.getParameter("CCNum"));
            ps.setString(12, request.getParameter("CVV"));
            ps.setString(13, request.getParameter("ExpMonth"));
            ps.setString(14, request.getParameter("ExpYear"));
            ps.setString(15, request.getParameter("Shipping"));


            ps.executeUpdate();
            out.println(ps);

            ps = con.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = ((BigInteger)rs.getObject("LAST_INSERT_ID()")).intValue();

            HttpSession session = request.getSession();
            Map<Integer, Integer> cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
            PreparedStatement psCart = con.prepareStatement("INSERT INTO order_content"
                        + "(orderid, productid, quantity) VALUES (?,?,?)");
            for (Map.Entry<Integer,Integer> entry : cart.entrySet())
            {
                psCart = con.prepareStatement("INSERT INTO order_content"
                        + "(orderid, productid, quantity) VALUES (?,?,?)");
                psCart.setString(1, String.valueOf(id));
                psCart.setString(2, String.valueOf(entry.getKey()));
                psCart.setString(3, String.valueOf(entry.getValue()));
                psCart.executeUpdate();
            }
            
//            psCart.executeBatch();
            out.println("made it here");
//            con.commit();
            RequestDispatcher rd = request.getRequestDispatcher("Confirmation");
            rd.forward(request, response);
        }
        catch(Exception e){
//            con.rollback();
//            out.println("here");
//            out.println(e);
//            out.println("<p>Error submitting order. ");

            RequestDispatcher rd = request.getRequestDispatcher("Checkout?error=TRUE");
            rd.include(request, response);
            
        }
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
            Logger.getLogger(SubmitOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SubmitOrder.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SubmitOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SubmitOrder.class.getName()).log(Level.SEVERE, null, ex);
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
