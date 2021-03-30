Plain Java JAX-RS (resteasy) with JWT authentication example (https://finalexception.blogspot.com/2021/03/atenticacao-jwt-com-java-simples-parte-1.html).

#### Unit Tests
    mvn test
    
#### Integration Tests
Integration tests point to **http://localhost:8080/hello-authentication/api**, so in order to run them sucefully, you need first to deploy this application to **localhost:8080**.

    mvn integration-test    

#### Packaging
    mvn clean package
    
<br/>

##### Token endpoint:

    POST /auth/connect/token
 
 This endpoint returns a 1 minute valid JWT token after checking credentials. Then you use that token to access the other services:
        
        POST /service/send
        GET /service/{name}
                 
  Check the test packages out for further details.       
  
  refs: https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey