# Cafe.
The client makes an Order for lunch (chooses from the menu) and indicates the time
when he would like to receive an order. The system shows the price of the Order and
offers to pay from a client account or in cash upon receipt
order. Points are awarded to the client for pre-orders
loyalty. If the Client places an order and does not pick it up, then the points
loyalty is removed up to its blocking. The client can evaluate
every order and leave feedback. The administrator manages the menu,
sets/removes bans/bonuses/points for Clients.

### The client can:
+ create an order,
+ delete order,
+ edit order,
+ add products from the shopping cart to the order,
+ remove products from an order,
+ remove products from cart,
+ add products to cart,
+ change profile,
+ change language.

### Administrator can:
In this implementation of the project, the admin logic was not developed


This is a more modern implementation of the Cafe Web Application that was written earlier without using Spring.
The Spring framework was chosen in this project to show how Spring can reduce the workload of a large application using newer programming techniques.

This app has been designed to showcase how Spring works and how Spring works. How easy it is to avoid redundant code. This application also uses the Hibernate ORM to show how easy and fast it is to link a Java Entity to tables from a database.

## Application Logic
First, an HTTP request occurs on the client side. This request is handled by the DispatcherServlet. The DispatcherServlet is a servlet specifically for Spring. After that, the DispatcherServlet gives control to one or another method of a specific controller (it all depends on the client's request). After that, the controller method calls the methods of the service, for which the injection into this same controller was previously made using Spring.
The Service contains the main application logic. The service calls certain methods from the DAO layer and manipulates them. For the DAO classes that the Service needs, an injection by Spring was created in advance.
When one or another method from the DAO class was called by the Service, work begins on obtaining the hibernate session. After that, Hibernate himself will turn to the database, receive information, wrap it in the desired Entity and return it.
After that, the Entity will return to the service, where some manipulation will be performed on it, and then it will return to the controller. The controller will put the Entity into the Model and give it to the client.
