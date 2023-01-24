# hotel-availibility-pad
# hotel-availibility-pad
Docker commands to run in local
--------------------------------
docker build -t hotel-availibility-pad:0.0.1-SNAPSHOT .
docker run -p 8089:8089 -e EUREKA_SERVER=http://172.17.0.2:8761/eureka/ -e CONFIG_SERVER=http://172.17.0.4:9296/config-server --name hotel-availibility-pad hotel-availibility-pad:0.0.1-SNAPSHOT
NOTE : The ip in the url has to be replaced by the container ip of the respective containers. To get the container ip run the following command.
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container-name
eg:- docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' service-registry
