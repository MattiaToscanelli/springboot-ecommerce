# ⚙️ Spring Boot eCommerce API

This is the backend REST API for an eCommerce application built with **Spring Boot**.  
It provides services for product management, order processing, checkout, and integrates with Stripe for payments and Okta for authentication (if enabled from the frontend).

> This backend is meant to be used with the [Angular eCommerce frontend](https://github.com/MattiaToscanelli/angular-ecommerce).

---

## 🚀 How to Run Locally

1. Make sure you have **Java 17+** and **Maven** installed.

2. Clone the project and navigate into it:
   ```bash
   git clone https://github.com/MattiaToscanelli/spring-boot-ecommerce.git
   cd spring-boot-ecommerce
   ```

3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

4. The API will be available at:
   ```
   https://localhost:8443/api
   ```

---

## 🔒 Create SSL Certificate

To enable HTTPS locally, create a self-signed certificate using the following steps:

1. Open a command-prompt window.

2. Move into the Spring Boot project directory:
   ```bash
   cd spring-boot-ecommerce
   ```

3. Execute the command below, replacing all `<var>` values accordingly:
   ```bash
   keytool -genkeypair \
   -alias <alias> \
   -keystore src/main/resources/<alias>-keystore.p12 \
   -keypass secret \
   -storeType PKCS12 \
   -storepass secret \
   -keyalg RSA \
   -keysize 2048 \
   -validity 365 \
   -dname "C=<country_code>, ST=<state>, L=<city>, O=<department_name>, OU=<group_name>, CN=localhost" \
   -ext "SAN=dns:localhost"
   ```

> ⚠️ This creates a `.p12` keystore that will be automatically picked up by Spring Boot if referenced correctly in `application.properties`.

