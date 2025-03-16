

# cd to backend
# shellcheck disable=SC2164
cd ~/Masters_Syr/2025-winter/cse681_projects/project3/backend/

# update maven locally
mvn clean install -U
mvn clean package -U

# local run
mvn spring-boot:run
# confirm if service is running on 8080 (default tomcat port)
lsof -i :8080 -S