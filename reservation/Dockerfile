# Build Stage for spring boot application image 

#Taking the base image and adding stage name as build
FROM openjdk:8-jdk-alpine as build

WORKDIR /app

COPY mvnw .

COPY .mvn .mvn

COPY pom.xml .


RUN chmod +x ./mvnw

ARG BCRYPT_SALT
ARG JWT_SECRET
ARG SENDGRID_API_KEY
ARG SPRING_DATASOURCE_PASSWORD
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ENV BCRYPT_SALT=${BCRYPT_SALT}
ENV JWT_SECRET=${JWT_SECRET}
ENV SENDGRID_API_KEY=${SENDGRID_API_KEY}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}

#download the dependency if needed or if the pom file is changed

RUN ./mvnw dependency:go-offline -B

COPY src src


RUN ./mvnw package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


#Production Stage for spring boot application image 

FROM openjdk:8-jre-alpine as production

# Create the argument so we cane use it as argument for any instruction for docker
ARG DEPENDENCY=/app/target/dependency


# Copy the dependency application file from build stage artifact

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#Run the application
ENTRYPOINT [ "java", "-cp", "app:app/lib*", "com.reservation.ReservationApplication" ]
