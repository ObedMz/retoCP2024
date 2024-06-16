# RETO CP 2024
## Overview

This application provides an API for managing products and orders. It includes JWT-based authentication and handles common exception scenarios with global exception handlers.

## Table of Contents

- [Endpoints](#endpoints)
- [Authentication](#authentication)
- [Docker Setup](#docker-setup)

## Endpoints

### Auth

- **Generate Token**
    - `GET /auth/token`
    - Returns a JWT token for authentication.

  ```bash
  curl -X GET http://localhost:8080/auth/token
  ```

### Products
#### Create or Update Product

- ` POST /api/products`
- Request Body: `Product` (JSON)
- Creates a new product or updates an existing one.

```bash
curl -X POST http://localhost:8080/api/products -H "Authorization: Bearer <JWT_TOKEN>" -H "Content-Type: application/json" -d '{"name":"Product Name","price":100}'
```
### Get All Products

- `GET /api/products`
- Returns a list of all products.
```bash
curl -X GET http://localhost:8080/api/products -H "Authorization: Bearer <JWT_TOKEN>"
```
- Get Product by ID

- `GET /api/products/{id}`
- Returns a product by its ID.
```bash
curl -X GET http://localhost:8080/api/products/1 -H "Authorization: Bearer <JWT_TOKEN>"
```
### Delete Product

- `DELETE /api/products/{id}`
- Deletes a product by its ID.
```bash
curl -X DELETE http://localhost:8080/api/products/1 -H "Authorization: Bearer <JWT_TOKEN>"
```
### Orders
- Create Order
- `POST /api/orders`
- Request Body: Order (JSON)
- Creates a new order.
```bash
curl -X POST http://localhost:8080/api/orders -H "Authorization: Bearer <JWT_TOKEN>" -H "Content-Type: application/json" -d '{"quantity":10,"product":{"id":1}}'
```
### Get All Orders

- `GET /api/orders`
- Returns a paginated list of all orders.
```bash
curl -X GET http://localhost:8080/api/orders -H "Authorization: Bearer <JWT_TOKEN>"
```
### Get Order by ID

- `GET /api/orders/{id}`
- Returns an order by its ID.
```bash
curl -X GET http://localhost:8080/api/orders/1 -H "Authorization: Bearer <JWT_TOKEN>"
```
### Delete Order

- `DELETE /api/orders/{id}`
- Deletes an order by its ID.
```bash
curl -X DELETE http://localhost:8080/api/orders/1 -H "Authorization: Bearer <JWT_TOKEN>"
```
### Get Orders by Product ID

- `GET /api/orders/product/{productId}`
- Returns a paginated list of orders by product ID.
```bash
curl -X GET http://localhost:8080/api/orders/product/1 -H "Authorization: Bearer <JWT_TOKEN>"
```
### Get Orders by Customer Name

- `GET /api/orders/customer/{customerName}`
- Returns a paginated list of orders by customer name.
```bash
curl -X GET http://localhost:8080/api/orders/customer/JohnDoe -H "Authorization: Bearer <JWT_TOKEN>"
```
### Authentication
The application uses JWT tokens for authentication. To interact with the secured endpoints, follow these steps:

#### Obtain a Token:

- Make a GET request to /auth/token to receive a JWT token.
  Example:
```bash
curl -X GET http://localhost:8080/auth/token
```
### Use the Token:

Include the token in the Authorization header of your requests:
```bash
-H "Authorization: Bearer <JWT_TOKEN>"
```
### Docker Setup

- Set Environment Variables:

- Ensure the JWT_SECRET is configured in your environment. You can do this in a .env file:

```makefile
JWT_SECRET=your_secret_key
```
- Build the Docker Image:

- Navigate to the project directory and build the Docker image:

```bash
Copiar código
docker run -p 8080:8080 --env-file .env currency-exchange-api
```

## Additional Notes
Swagger Integration: The API documentation is available via Swagger. Access it at `http://localhost:8080/swagger-ui/index.html`


