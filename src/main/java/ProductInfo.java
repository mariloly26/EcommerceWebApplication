/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tracking.DBConn;
import tracking.RecentProducts;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
public class ProductInfo extends HttpServlet {

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
//        
        
        try (PrintWriter out = response.getWriter()) {
            
            String pid = request.getParameter("id");

            Connection con = DBConn.initializeDatabase();
            // register viewed product with database
            RecentProducts.addProduct(con, request.getSession().getId(), Integer.parseInt(pid));
            PreparedStatement ps = con.prepareStatement("select * from products where id=" + pid);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            PreparedStatement ps2 = con.prepareStatement("select * from images where id="+pid);
            ResultSet rs2 = ps2.executeQuery();
            ResultSetMetaData rsmd2 = rs2.getMetaData();
            ArrayList<String> images = new ArrayList<>();
            while(rs2.next()){
                images.add("http://circinus-10.ics.uci.edu:8080/project2/static/"+(String)rs2.getObject("url"));
            }
            rs.next();
            String name = rs.getString("name");
            String cat = rs.getString("category");
            String manufacturer = rs.getString("manufacturer");
            int set_quantity = rs.getInt("set_quantity");
            String color = rs.getString("color");
            double price = rs.getDouble("price");
            String imgString;
            int imageIndex = 0;
            if((request.getParameter("img")!=null) && (Integer.parseInt(request.getParameter("img"))<images.size())){
                
                
                imgString = images.get(Integer.parseInt(request.getParameter("img")));
                imageIndex = Integer.parseInt(request.getParameter("img"));
                
            }
            else{
               imgString = images.get(0);
              
               
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+name+"</title>"); 
            out.println("<link href='https://fonts.googleapis.com/css2?family=Lato&display=swap' rel='stylesheet'>");
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat\">");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"template/base/topnav.css\" />");
            out.println("<link rel=\"stylesheet\" href=\"template/base/responsive.css\">");
            out.println("<link rel='stylesheet' href='template/product/product.css'>");
            out.println("<script src='template/base/topnav.js'></script>");
            out.println("<script src='template/product/product.js'></script>");   
            out.println("</head>");
            out.println("<body>");
            
            NumberFormat formatter = new DecimalFormat("#0.00");
            
            out.println("<div class='topnav'></div>");
            out.println("<div class='prod'>");
            out.println("<h1>"+name+"</h1>");
            out.println("<a href='ProductInfo?id="+pid+"&img="+(imageIndex+1)+"'><img id='pic' src= '"+ imgString +"'></a>\n"
                    + "<p><i>Click image for more photos</i></p>\n"
                    + "<h2>$" + formatter.format(price) + "</h2>" 
                    + "<h3>ADDITIONAL DETAILS</h3>\n"
                    + "<p>Color: "+ color +"; " + "Set Quantity: " + set_quantity + ";</p>\n"
                            + "<p>Manufacturer: " + manufacturer + "</p>");
            
            
            out.println("<form action='Checkout' method='post'>\n"
                    + "<button class='prod' type='submit' name='addtocart' value='"+pid+"'>Add to cart</button>");
            out.println("</div>");
            
            
            
//            if(request.getParameter("addtocart")!=null){
//                HttpSession session = request.getSession();
//                ArrayList<Integer> cart;
//
//    //            ArrayList<int> cart = session.getAttribute("cart");
//                if(session.getAttribute("cart") == null){
//                    cart = new ArrayList<>();
//                    cart.add(Integer.parseInt(pid));
//                    session.setAttribute("cart", cart);
//                } else {
//                    cart = (ArrayList<Integer>)session.getAttribute("cart");
//                    cart.add(Integer.parseInt(pid));
//                    session.setAttribute("cart", cart);
//                }
//                
//                out.println(cart);
//            }
//            ArrayList<int> cart = new ArrayList<int>();
            
            
            
                
            
            out.println("");
//            out.println(rs);
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(ProductInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductInfo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProductInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductInfo.class.getName()).log(Level.SEVERE, null, ex);
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
