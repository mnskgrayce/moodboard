# Moodboard

A China-level Pinterest off-brand by a bunch of second year college students.

Built with the following tools:
1. Frontend: JavaScript, Bootstrap 5, Thymeleaf
2. Backend: Spring Boot, PostgreSQL (all dependencies in `pom.xml`)

## Main Features
- User authentication with encrypted password
- Search for images from the Unsplash Image API
- View image detail
- Add image to custom mood boards
- View, add, delete, and update mood boards and images

## How to Run
1. Install and run a PostgreSQL server at the default port 5432
2. Create user **minesk** with password **admin**
3. Create database **db** with all privileges granted to the user
4. Build and run the project inside IntelliJ IDEA
5. Go to http://localhost:8081 to log in and test

These credentials can be tweaked inside the `application.yml` file.

Sample users and data are created in the `bootstrap/BootstrapData.java` file.
