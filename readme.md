Plain Java JAX-RS (resteasy) with JWT authentication example.

#### Unit Tests
    mvn test
    
#### Integration Tests
Integration tests point to **http://localhost:8080/hello-authentication/api**, so in order to run them sucefully, you need first to deploy this application to **localhost:8080**.

    mvn integration-test    

#### Packaging
    mvn clean package
    

######Token endpoint:

    POST /auth/connect/token
 
 This endpoint returns a 1 minute JWT token after checking credentials. Then you use that token to access the other services:
        
        POST /service/send
        GET /service/{name}
                 
         