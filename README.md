# Demo eShop

This is a Spring Boot-based eCommerce backend project, called Demo eShop, which includes key functionalities like cart management, product details retrieval, and image handling using a REST API architecture.

## Features
- **Product Management**: Add, view, and manage products.
- **Cart Management**: Add items to the cart, view the cart, and handle quantities.
- **Product Image Handling**: Product images are managed using BLOB storage in the database and provided as downloadable URLs in the response.
- **User Management**: Handle user-related data.
- **DTO Pattern**: Leverages DTOs for clean separation of business logic from data representation.
- **Custom Exceptions**: Handles errors gracefully using custom exception classes.

## Technology Used
- **Spring Boot**: Backend framework for building the REST API.
- **Spring Data JPA**: For interacting with the relational database.
- **MariaDB**: Database used for storing product, cart, and user data.
- **ModelMapper**: Used for mapping entities to DTOs.
- **Lombok**: Used to reduce boilerplate code for model classes.
- **Maven**: For dependency management.
- **Java 17**: Programming language.

## Getting Started
### Prerequisites
 Before you begin , ensure you have the following installed:
 - Jave 17+
 - Maven
 - MariaDB

 ### Setup MariaDB Database
 1. Create MariaDB database 

    `CREATE DATABASE demo_eshop;`

2. Update your application.properties file with the correct MariDB credentials

    `spring.application.name=demo_eshop`
    `spring.datasource.url=jdbc:mariadb://localhost:3306/eshops_db`
    `spring.datasource.username=your_username`
    `spring.datasource.password=your_password`
    `##(create, update, create-drop, validate)`
    `spring.jpa.hibernate.ddl-auto=update`

### Installation

1. Clone the repository:

    `git clone https://github.com/your-username/demo-eshop.git`

2. Navigate to the project directory:

    `cd demo-eshop`

3. Buld the project using maven:

    `mvn clean install`

4. Run the application:

    `mvn spring-boot:run`

5. Test the API:

    `http://localhost:8080/swagger-ui/index.html`


