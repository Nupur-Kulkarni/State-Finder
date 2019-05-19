# State-Finder

State Finder is a RESTful API which returns a name of the state the given Location(latitude and longitude) lies in.

It has been built using a Spring MVC application. 

Use following command to start the State Server

1.	If java is not installed, then install java first
	apt-get install default-jdk

2.	Install maven
	sudo apt-get install maven

3.	Verify Installation	
	mvn -version

Now, go to the directory of StateServer project.

4.	Install Dependencies
	mvn clean install

5.	Verify war package
	mvn clean verify

6.	Start Tomcat server and deploy war file
	mvn tomcat7:run-war

Now, make a HTTP POST to StateServer using

curl -H "Content-Type: application/json" -d '{"longitude": -77.036133,"latitude": 40.513799}' http://localhost:8080/StateServer/
