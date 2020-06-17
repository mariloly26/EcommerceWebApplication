Trevor Keller,        45642596
Matthew Piazza,       10064450
Mariloly Rodriguez,   15241022
Mohra Arsala,         29658422

Website URL: Presentation

Landing Page: About Us
Home Page: Product Listing + Recently Viewed Products
(the above are accessible from the navigation bar on every page)

You can access each product's individual page by clicking on the
product's image on the Product Listing page.
From the product info page, a product can be added to the user's cart.
The user is redirected to view the cart after each addition. The user can also view the
cart at anytime by selecting the "Cart" button in the navbar.
From the cart page, the user may complete the order form to view the confirmation page.

Requirement Locations:

Servlets
1a (Product Listing). 
    src/main/java/DisplayProducts.java
    <URL>/DisplayProducts
1b (Recently Viewed using "include").
    src/main/java/tracking/RecentProducts.java
    included through DisplayProducts
2 (Product Details using Add to Cart). 
    src/main/java/ProductInfo.java
    <URL>/ProductInfo?id=[#1-10]
3 (Checkout using "forward").
    (View cart)
    src/main/java/tracking/Checkout.java
    <URL>/Checkout
    (Order form)
    src/main/java/tracking/SubmitOrder.java
    <URL>/SubmitOrder
    (Confirmation page)
    src/main/java/tracking/Confirmation.java
    <URL>/Confirmation
    forwarded from SubmitOrder on successful insertion into database

