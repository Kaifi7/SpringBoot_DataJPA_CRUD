FROM tomcat:9.0.37-jdk14-openjdk
LABEL maintainer=Kafeel
LABEL company=TCS
RUN rm -rf /usr/local/tomcat/webapps/*
#COPY ./*.jar /usr/local/tomcat/webapps/ROOT.war
COPY ./target/*.jar app.jar
EXPOSE 8080
#CMD ["catalina.sh","run"]
CMD ["java","-jar","app.jar"]