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
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
public class Checkout extends HttpServlet {

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
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
//            out.println("<title>Servlet Checkout</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Checkout at " + request.getContextPath() + "</h1>");
//            String pid = request.getParameter("pid");
            out.println("<!DOCTYPE html>");
            // out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Servlet Checkout</title>");    
            out.println("<link href='https://fonts.googleapis.com/css2?family=Lato&display=swap' rel='stylesheet'>");
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat\">");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"template/base/topnav.css\" />");
            out.println("<link rel=\"stylesheet\" href=\"template/base/responsive.css\">");
            out.println("<link rel='stylesheet' href='template/product/product.css'>");
            out.println("<script src='template/base/topnav.js'></script>");
            out.println("<script src='template/product/product.js'></script>");        
            out.println("</head>");
            out.println("<body>");
            Map<Integer, Integer> cart;
            HttpSession session = request.getSession();
            
            if(request.getParameter("addtocart")!=null){
                
                // ArrayList<Integer> cart;
                
                int pid = Integer.parseInt(request.getParameter("addtocart"));

    //            ArrayList<int> cart = session.getAttribute("cart");
                if(session.getAttribute("cart") == null){
                    cart = new HashMap<Integer, Integer>();
                    
                    cart.put(pid, 1);
                    session.setAttribute("cart", cart);
                } else {
//                    out.println("not null cart");
                    cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
                    if(cart.containsKey(pid)){
                        int quantity = cart.get(pid);
                        cart.put(pid, quantity+1);
                        
                    }
                    else{
                        cart.put(pid, 1);
                    }
                    
                    
                    session.setAttribute("cart", cart);
//                    out.println(cart.keySet());
                    // for(int i = 0; i<cart.size(); i++){
                    //     out.println(cart.get(i)+ " ");
                    // }
                }
            }
            else{
                if(session.getAttribute("cart")==null){
                    cart = new HashMap<>();
                }
                else{
                    cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
                    
                }
            }
            
//            if (session.getAttribute("cart")==null){
//                cart = new HashMap<Integer, Integer>();
//            }
//            out.println(cart.size());
            out.println("<div class = 'topnav'></div>");
//            out.println(request.getQueryString());
            if(request.getParameter("error")=="TRUE"){
                out.println("<p>Error: You cannot place an empty order!</p>");
            }
            out.println("<div class = 'form'>");
            out.println("<div class='mainrow'>");
            out.println("<div class='col-50'>");
            out.println("<div class='container'>");
            
            out.println("<h2>Checkout</h2>");
            out.println("<form name='order' action='SubmitOrder' id='order' method='post'onSubmit='return (validateOrderForm())' >");
            out.println("<div class='row'>");
            
            out.println("<div class='col-50'>");
            out.println("<h3>Shipping Address</h3>");
            out.println("<div class='row'>");
            out.println("<div class='col-50'>");
            out.println("<label for='FirstName'> First Name</label>");
            out.println("<input type='text' id='FirstName' name='FirstName' placeholder='Brutalitops'>");
            out.println("</div>");
            out.println("<div class='col-50'>");
            out.println("<label for='LastName'> Last Name</label>");
            out.println("<input type='text' id='LastName' name='LastName' placeholder='The Magician'>");
            out.println("</div>");
            out.println("</div>");
            out.println("<label for='Email'> Email Address</label>");
            out.println("<input type='text' id='Email' name='Email' placeholder='brutalitops123@gmail.com'>");
            out.println("<label for='PhoneNum'> Phone</label>");
            out.println("<input type='tel' id='PhoneNum' name='PhoneNum' placeholder='888 888 8888'>");
            
            out.println("<div class='row'>");
            out.println("<div class='col-75'>");
            out.println("<label for='Address'> Address</label>");
            out.println("<input type='text' id='Address' name='Address' placeholder='12345 Main St'>");
            out.println("</div>");
            out.println("<div class='col-25'>");
            out.println("<label for='Apartment'> Apt./Unit</label>");
            out.println("<input type='text' id='Apartment' name='Apartment' placeholder='67'>");
            out.println("</div>");
            out.println("</div>");

            out.println("<label for='City'> City</label>");
            out.println("<input type='text' id='City' name='City' placeholder='Irvine'>");

            out.println("<div class='row'>");
            out.println("<div class='col-50'>");
            out.println("<label for='State'> State</label>");
            out.println("<select id='State' name='State'>");
            out.println("<option value='' selected='selected'>Select a State</option>");
            out.println("<option value='AL'>Alabama</option>");
            out.println("<option value='AK'>Alaska</option>");
            out.println("<option value='AZ'>Arizona</option>");
            out.println("<option value='AR'>Arkansas</option>");
            out.println("<option value='CA'>California</option>");
            out.println("<option value='CO'>Colorado</option>");
            out.println("<option value='CT'>Connecticut</option>");
            out.println("<option value='DE'>Delaware</option>");
            out.println("<option value='DC'>District Of Columbia</option>");
            out.println("<option value='FL'>Florida</option>");
            out.println("<option value='GA'>Georgia</option>");
            out.println("<option value='HI'>Hawaii</option>");
            out.println("<option value='ID'>Idaho</option>");
            out.println("<option value='IL'>Illinois</option>");
            out.println("<option value='IN'>Indiana</option>");
            out.println("<option value='IA'>Iowa</option>");
            out.println("<option value='KS'>Kansas</option>");
            out.println("<option value='KY'>Kentucky</option>");
            out.println("<option value='LA'>Louisiana</option>");
            out.println("<option value='ME'>Maine</option>");
            out.println("<option value='MD'>Maryland</option>");
            out.println("<option value='MA'>Massachusetts</option>");
            out.println("<option value='MI'>Michigan</option>");
            out.println("<option value='MN'>Minnesota</option>");
            out.println("<option value='MS'>Mississippi</option>");
            out.println("<option value='MO'>Missouri</option>");
            out.println("<option value='MT'>Montana</option>");
            out.println("<option value='NE'>Nebraska</option>");
            out.println("<option value='NV'>Nevada</option>");
            out.println("<option value='NH'>New Hampshire</option>");
            out.println("<option value='NJ'>New Jersey</option>");
            out.println("<option value='NM'>New Mexico</option>");
            out.println("<option value='NY'>New York</option>");
            out.println("<option value='NC'>North Carolina</option>");
            out.println("<option value='ND'>North Dakota</option>");
            out.println("<option value='OH'>Ohio</option>");
            out.println("<option value='OK'>Oklahoma</option>");
            out.println("<option value='OR'>Oregon</option>");
            out.println("<option value='PA'>Pennsylvania</option>");
            out.println("<option value='RI'>Rhode Island</option>");
            out.println("<option value='SC'>South Carolina</option>");
            out.println("<option value='SD'>South Dakota</option>");
            out.println("<option value='TN'>Tennessee</option>");
            out.println("<option value='TX'>Texas</option>");
            out.println("<option value='UT'>Utah</option>");
            out.println("<option value='VT'>Vermont</option>");
            out.println("<option value='VA'>Virginia</option>");
            out.println("<option value='WA'>Washington</option>");
            out.println("<option value='WV'>West Virginia</option>");
            out.println("<option value='WI'>Wisconsin</option>");
            out.println("<option value='WY'>Wyoming</option>");
            out.println("</select>");
            out.println("</div>");
            out.println("<div class ='col-50'>");
            out.println("<label for='Zip'> Zipcode</label>");
            out.println("<input type='text' id='Zip' name='Zip' placeholder='92617' onblur='update_totals()'>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='col-50'>");
            out.println("<h3>Payment Info</h3>");
            out.println("<label for='CCName'> Name on Card</label>");
            out.println("<input type='text' id='CCName' name='CCName' placeholder='Brutalitops the Magician'>");
            
            out.println("<div class='row'>");
            out.println("<div class='col-75'>");
            out.println("<label for='CCNum'> Credit Card Number</label>");
            out.println("<input type='text' id='CCNum' name='CCNum' placeholder='0000 0000 0000 0000'>");
            out.println("</div>");
            out.println("<div class='col-25'>");
            out.println("<label for='CVV'> CVV</label>");
            out.println("<input type='text' id='CVV' name='CVV' placeholder='123' maxlength='4'>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='row'>");
            out.println("<div class='col-25'>");
            out.println("<label for='ExpMonth'> Exp. Month</label>");
            out.println("<select id='ExpMonth' name='ExpMonth'>");
            out.println("<option value='' selected='selected'> Month</option>");
            out.println("<option value='01'>1</option>");
            out.println("<option value='02'>2</option>");
            out.println("<option value='03'>3</option>");
            out.println("<option value='04'>4</option>");
            out.println("<option value='05'>5</option>");
            out.println("<option value='06'>6</option>");
            out.println("<option value='07'>7</option>");
            out.println("<option value='08'>8</option>");
            out.println("<option value='09'>9</option>");
            out.println("<option value='10'>10</option>");
            out.println("<option value='11'>11</option>");
            out.println("<option value='12'>12</option>");
            out.println("</select>");
            out.println("</div>");
            out.println("<div class='col-25'>");
            out.println("<label for='ExpYear'> Exp. Year</label>");
            out.println("<select id='ExpYear' name='ExpYear'>");
            out.println("<option value='' selected='selected'>Year</option>");
            out.println("<option value='2020'>2020</option>");
            out.println("<option value='2021'>2021</option>");
            out.println("<option value='2022'>2022</option>");
            out.println("<option value='2023'>2023</option>");
            out.println("<option value='2024'>2024</option>");
            out.println("<option value='2025'>2025</option>");
            out.println("<option value='2026'>2026</option>");
            out.println("<option value='2027'>2027</option>");
            out.println("</select>");
            out.println("</div>");
            out.println("</div>");

            out.println("<h3>Shipping Options</h3>");
            out.println("<input type='radio' id='Overnight' name='Shipping' value='Overnight' checked>");
            out.println("<label class='side' for='Overnight'> Overnight (1 Business Day)</label>");
            out.println("<br/>");
            out.println("<input type='radio' id='Expedited' name='Shipping' value='Expedited'>");
            out.println("<label class='side' for='Expedited'> Expedited (2 Business Days)</label>");
            out.println("<br/>");
            out.println("<input type='radio' id='Ground' name='Shipping' value='Ground'>");
            out.println("<label class='side' for='Ground'> Ground (5-6 Business Days)</label>");
            out.println("</div>");
            out.println("</div>");

            out.println("<input type='submit' name='submit' value='Finish Order' class='btn'>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='col-50'>");
            out.println("<div class='container'>");
            out.println("<h2>Cart</h2>");


            // Add code block for each item in cart
            
            cart = (HashMap<Integer, Integer>)session.getAttribute("cart");
            
            NumberFormat formatter = new DecimalFormat("#0.00");
            if(cart!=null){
                double order_total = 0.00;
                out.println("<table style='width:100%'>");
                out.println("<tr>");
                out.println("<th class='leftalign'>Item</th>");
                out.println("<th class='rightalign'>Quantity</th>");
                out.println("<th class='rightalign'>Price</th>");
                out.println("</tr>");
                
                Connection con = DBConn.initializeDatabase();
                PreparedStatement ps = con.prepareStatement("select * from products where id=?");

                for (Map.Entry<Integer,Integer> entry : cart.entrySet())
                {
                    ps.setInt(1, entry.getKey());
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

                ps.close();
                con.close();

                // out.println("<h3 style='float:left;width:75%;'>Subtotal: </h3>");
                // out.println("<hr>");

                // out.println("<hr>");
                // out.println("<hr>");
                out.println("</table>");
                out.println("<hr>");
                out.println("<h3 style='float:left;width:75%;'>Order Total: </h3>");
                // BigDecimal bd = new BigDecimal(Double.toString(order_total));
    //            bd = bd.setScale(2, RoundingMode.HALF_UP);
    //            out.println("<h3 style='float:right;width:25%;'>$"+DoubleRounder.round(order_total, 2)+"</h3>");

                out.println("<h3 style='float:right;width:25%;'>$"+formatter.format(order_total)+"</h3>");
            
            }
            else{
                out.println("<p>Your cart is empty.</p>");
            }
            // Add code block for each item in cart
            // for (Map.Entry<int,int> entry : cart.entrySet())
            // {
            //         PreparedStatement ps = con.prepareStatement("select * from products
            //                                                      where id="+entry.getKey());
            //         String name = ;
            //         out.println("<h3 style='float:left;width:50%;'>" + name + "</h3>");
            //         out.println("")
            // }
            
            out.println("</div");
            out.println("</div");
            out.println("</div");
            out.println("</div");
            out.println("</body>");
            out.println("</html>");
            
            
            
            out.println("</body>");
            out.println("</html>");
//            HttpSession session = request.getSession();
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
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
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
