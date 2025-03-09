java -jar target/untitled-1.0-SNAPSHOT.jar server hello-world.yml

If you are not planning on distributing a client library for developers, one can combine project-api and project-application into a single project, which tends to look like this:

com.example.myapplication:
api: Representations. Request and response bodies. 
cli: Commands
client: Client code that accesses external HTTP services.
core: Domain implementation; where objects not used in the API such as POJOs, validations, crypto, etc, reside.
db: Database access classes
health: Health Checks
resources: Resources
MyApplication: The application class
MyApplicationConfiguration: configuration class

todo

add junit test
add integration test
spring boot admin ui - extension 
changing logging add hoc at prod

curl http://localhost:8080/swagger




