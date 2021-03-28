# Driver Service
## Solution

This webservice uses Spring Boot and an H2 in-memory database, and includes tests and Swagger UI interactive documentation with examples.  

run with `./mvnw spring-boot:run`

Interact with the API easily [using Swagger UI here.](http://localhost:8080/swagger-ui/#/)   

---

## Technical Challenge

### Objective

The objective of this challenge is to create a simple REST service that will manage details of drivers for usewithin a hypothetical insurance service. For the purposes of this challenge, a driver record consists of thefollowing information:

- A unique ID for the driver
- First Name
- Last Name
- Date of Birth
- Creation Date

Details of drivers should be stored in a simple flat file. You may store dates in any format you wish.

### Requirements

Your service should implement the following three REST endpoints.

#### URL: /driver/create

An endpoint to allow new drivers to be created and stored. This should be a POST request, with a bodycontaining a JSON object containing the driver first name, last name and date of birth in key value pairs. Itshould follow the format below, using these parameter names and the date format YYYY-MM-DD.

{"firstname":  "John","lastname": "Smith","date_of_birth": "1980-05-01"}

Driver ID and creation date should be generated automatically by the service before the data is stored in thefile.

#### URL: /drivers

A GET endpoint which returns a list of all existing drivers in JSON format

#### URL: /drivers/byDate?date=<date>

A GET endpoint which returns a list of all drivers created after the specified date. Again, the list should bereturned in JSON format. Again, the date parameter should use the format YYYY-MM-DD.

### Submission

Your submission should include all source code for the service along with instructions on how to build it andrun it. You can provide this as a zip file or a tar file, or you can give us a link to a Github repository or similar.Your solution can be packaged as a runnable jar file, or as a war file to deploy into an application server. If you choose the second option, make sure your war is compatible with Apache Tomcat. You are free to use anythird-party Java framework or library you wish.