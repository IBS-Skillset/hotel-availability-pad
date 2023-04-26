FROM public.ecr.aws/amazoncorretto/amazoncorretto:11
WORKDIR /opt/app

COPY target/hotel-availibility-pad.jar /opt/app/hotel-availibility-pad.jar

ENTRYPOINT ["/usr/bin/java"]
CMD ["-Dorg.apache.catalina.STRICT_SERVLET_COMPLIANCE=true", "-jar", "/opt/app/hotel-availibility-pad.jar"]

EXPOSE 8080