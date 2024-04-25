Inventory Service
This is a RESTful API for managing inventory. It allows users to perform CRUD operations on products in the inventory database. The API provides endpoints for retrieving, creating, updating, and deleting products. Additionally, it includes endpoints for checking and updating product quantities.

Table of Contents
Endpoints
Usage
Dependencies
Setup
Endpoints
Retrieve Products
GET /inventory/products: Retrieve a paginated list of all products.
GET /inventory/products/{productId}: Retrieve details of a specific product by its ID.
GET /inventory/products/byIds?ids={ids}: Retrieve multiple products by their IDs.
GET /inventory/products/category/{category}: Retrieve products by category.
Create, Update, Delete Products
POST /inventory/products: Create a new product.
PUT /inventory/products: Update an existing product.
DELETE /inventory/products/{productId}: Delete a product by its ID.
Product Quantity Management
PUT /inventory/products/{productId}/increment: Increment the quantity of a product by its ID.
PUT /inventory/products/{productId}/decrement: Decrement the quantity of a product by its ID.
GET /inventory/products/{productId}/availability: Check the availability of a product by its ID.
Miscellaneous
GET /inventory/products/count: Retrieve the total count of products in the inventory.
Usage
Use the provided endpoints to interact with the inventory database.
Ensure proper authentication and authorization mechanisms are implemented based on your application requirements.
Dependencies
Spring Boot
Spring Data JPA
ModelMapper
Jakarta Validation API
Setup
Clone this repository.
Configure your database connection in application.properties.
Build and run the application using Maven or your preferred IDE.
Contributors
Obada Qasrawi
