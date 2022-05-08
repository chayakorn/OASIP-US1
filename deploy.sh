docker-compose rm -svf oasipfrontend oasipbackend;
docker rmi frontend;
docker rmi backend;
docker-compose up -d;