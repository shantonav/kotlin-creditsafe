## Kotlin + maven based library project to demonstrate test integration with CreditSafe. 
This project generates the boiler platecodes to transform requests, configure REST calls and communicate  with 
CreditSafe APIs through <https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-maven-plugin/README.md>. Application only interacts with API handlers
through an object-oriented approach without worrying about the actual HTTP communication.

### Project uses  
 - kotlin
 - JDK 11
 - maven 3.6.x
 - Open Api client side code generation ()

### To build the project it requires the open api client side models and handlers to be generated
`mvn clean install -P generate-creditsafe-openapi` this will generate model and handler classes under `target/generated-sources/src/main/kotlin`. 
Pre-requite to this is having the CreditSafe openAPi spec under `src/main/resources/creditsafe/CreditSafeOpenAPISpec.json`.

Note : I had to manually modify the spec because  
a. the company search response does not match the spec for example response contains `vatNo` as `array` whereas in the spec
it is `string`  
b. there are many schema definitions which were incomplete or not used and do not adhere to 3.0.x standards.

### There are a couple of simple IT test classes under `src/test/kotlin`. These classes test the authentication with CreditSafe
and a couple basic company searches. 
They can be run using `mvn verify -P cs-it-tests`