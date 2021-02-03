# Anheuser-Busch InBev: Code challenge API
API and simple database to handle the products of the ABInBev ecommerce

This project was created from the Spring Initializr with MongoDB, Spring Web, Spring Data MongoDB and Spring Security

### Instructions to use
Download the zip project by clicking on Clone or download button, then Downlod ZIP;  
Extract the zip file and open a terminal in the folder extracted;  
Run the following command:  

```
$ mvn spring-boot:run
```

Access http://localhost:9090/api/products with user "test" and password "test" to see a list of products.

###API Documentation

http://localhost:9090/v2/api-docs  
http://localhost:9090/swagger-ui.html

### APIs endpoints
GET http://localhost:9090/api/products [list all products]  
GET http://localhost:9090/api/products/{id} [list a product by ID]  
GET http://localhost:9090/api/products/findByName/{name} [list a product by name ignoring case]  
POST http://localhost:9090/api/products/ [add a new product]  
PUT http://localhost:9090/api/products/{id} [update product all attributes]  
DELETE http://localhost:9090/api/products/{id} [remove a product]  

