# Authentication-App
This project is a full-stack web application that demonstrates user authentication and role-based authorization. Users can register, login, and access different resources based on their role (USER or ADMIN).  The backend provides secure APIs using JWT authentication, while the frontend consumes these APIs and displays role-based content.


Project Strucutre

project-root
backend/
   src/main/java/com/example/auth
       controller
       service
       repository
       entity
       dto
       mapper
       config
       security

frontend/
   src
      pages
      components
      services
      routes
      hooks
