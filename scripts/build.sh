echo "build web application"

cd src/main/angular \
  && yarn \
  && ng build --configuration production --output-path ../resources/static \
  && cd ../../../

echo "copy web application to resources"

cd src/main/resources/static \
  && mkdir error \
  && cp index.html error/404.html \
  && cd ../../../../

# echo "build java application"

# mvn clean install package -Dmaven.test.skip=true -Pstandalone

echo "build java application with gradle"

gradle build -Pstandalone -x test