🎒 Lost & Found Backend API
A robust REST API for managing lost and found items. Built with Spring Boot, Spring Security, MySQL, and JWT Authentication. This project was created as part of the IJSE CMJD coursework assignment.

🌟 Features
🔐 JWT Authentication with Spring Security
📝 CRUD Operations for lost and found items
🖼️ Image Upload support for item photos
🔍 Advanced Search and filtering capabilities
👤 User Management with role-based access
📊 RESTful API with comprehensive endpoints
🗄️ Database Management with JPA/Hibernate

📁 Project Structure
src/
├── main/java/com/example/lostandfound/
│   ├── controller/          # REST Controllers
│   ├── model/              # Entity Classes
│   ├── repository/         # Data Access Layer
│   ├── service/            # Business Logic
│   ├── config/             # Security & Web Config
│   └── dto/                # Data Transfer Objects
└── resources/
    └── application.properties

🚀 Getting Started

Prerequisites
Java 11+
Maven 3.6+
MySQL 8.0+

Setup
bashgit clone https://github.com/Gevindu-Piyawansha/lost-and-found-springboot-back-end.git
cd lost-and-found-springboot-back-end
Configure Database
Update application.properties:
propertiesspring.datasource.url=jdbc:mysql://localhost:3306/lost_and_found
spring.datasource.username=your_username
spring.datasource.password=your_password
Run Application
bashmvn clean install
mvn spring-boot:run
API runs at http://localhost:8080

📚 API Endpoints
POST /api/auth/login - User authentication
GET /api/items - Get all items
POST /api/items - Create new item
PUT /api/items/{id} - Update item
DELETE /api/items/{id} - Delete item
GET /api/items/search - Search items

🧪 Technologies Used
☕ Java 11
🍃 Spring Boot 3.x
🔐 Spring Security with JWT
🗄️ Spring Data JPA
🐬 MySQL Database
🔨 Maven Build Tool

📜 License
This project is open source under the MIT License.

👤 Author
Gevindu Piyawansha

📧 gevindu.piyawansha@gmail.com
🌐 [LinkedIn](https://www.linkedin.com/in/gevindu-piyawansha/)

Made with ❤️ for the tech community
