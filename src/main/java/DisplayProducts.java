/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import tracking.DBConn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maril
 */
@WebServlet("/DisplayProducts")
public class DisplayProducts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            // website head
            out.println("<head>");
            out.println("<link rel='stylesheet' type='text/css' href='template/base/responsive.css' ></link>");
            out.println("<link rel='stylesheet' type='text/css' href='template/base/topnav.css' </link>");
            out.println("<link rel='stylesheet' type='text/css' href='template/home/home.css' </link>");
            out.println("<script src = 'template/base/topnav.js' ></script>");
            out.println("<script src = 'template/home/home.js' ></script>");
            out.println("</head>");
            // website body
            out.println("<body>");
            out.println("<div class= 'topnav'></div>");
            out.println("<div class = 'header'>");
            out.println("<h2>Welcome To Sumatra!</h2>");
            out.println("<h6>A shop for all your D&D needs</h6>");
            out.println("</div>");

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecentProducts");
            dispatcher.include(request, response);
            // another servlet populates the recently viewed products

            //button container
            out.println("<div class = 'row col-100'>");
            
            out.println("<div class=\"col-100\">");
            out.println("<div id = 'myBtnContainer'>");
            out.println("<button class = 'btn active' onclick = 'filterSelection('all')'> All Products</button>");
            out.println("<button class = 'btn' onclick = 'filterSelection('book')'>Books </button>");
            out.println("<button class = 'btn' onclick = 'filterSelection('dice')'>Dice </button>");
            out.println("<button class = 'btn' onclick = 'filterSelection('bag')'>Dice Bag </button>");
            out.println("<button class = 'btn' onclick = 'filterSelection('mat')'>Table-Top Mat </button>");
            out.println("</div>");
            //establish connection to db
            Connection con = DBConn.initializeDatabase();
            Statement stmt = con.createStatement();
            //String staticLocation = "http://circinus-10.ics.uci.edu:8080/project2/static/"; // don't know why not working
            String staticLocation = "./static/";
            //send query to the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM products LIMIT 10 ");
            while(rs.next())
            {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String material = rs.getString("material");
                String color = rs.getString("color");
                String price = rs.getString("price");
                String set_quantity = rs.getString("set_quantity");
                String manufacturer = rs.getString("manufacturer");
                
                out.println("<div class = 'product col-25 col-s-50 col-xs-100 " + category + " show'>");
                out.println("<div class = 'content'>");
                out.println("<a href = './ProductInfo?id=" + id + "'>"); //transfer's the user to mohra's servlet
                //out.println("</a>");
                out.println("<img class = 'regular' src = ");
                ResultSet urls = stmt.executeQuery("SELECT url FROM images WHERE id = " + id +" LIMIT 1");
                //loop starts here
                while(urls.next())
                {
                    String url = urls.getString("url");
                    out.print("'"+ staticLocation + url + "' alt = '"+ name.replace("\'", "") + "'");
                    out.print("onmouseover='imageSize(this, 300)' onmouseout='imageSize(this, 250)'>");
                }
                
                out.println("<h4>" + name + "</h4>");
                out.println("</a>");
                out.println("<p>" + price + "</p>");
                out.println("<p>" + material + "</p>");
                out.println("<p>" + manufacturer + "</p>");
                out.println("</div>");
                out.println("</div>");
                
            }
                
            con.close();
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");  
        } 
        catch (ClassNotFoundException ex) {
            out.println("class not found");
            Logger.getLogger(DisplayProducts.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            out.println("sql query failed");
            Logger.getLogger(DisplayProducts.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally{
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Java Servlet that renders all available products (home page)";
    }// </editor-fold>

}
