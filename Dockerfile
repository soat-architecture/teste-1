# Dockerfile para aplicação Quarkus com MySQL
FROM eclipse-temurin:21-jre-alpine

# Install curl for healthcheck
RUN apk add --no-cache curl

# Create user
RUN addgroup -g 1001 -S jboss && \
    adduser -u 1001 -S jboss -G jboss

# Set working directory
WORKDIR /deployments

# Copy the jar file
COPY --chown=jboss:jboss target/quarkus-app/lib/ /deployments/lib/
COPY --chown=jboss:jboss target/quarkus-app/*.jar /deployments/
COPY --chown=jboss:jboss target/quarkus-app/app/ /deployments/app/
COPY --chown=jboss:jboss target/quarkus-app/quarkus/ /deployments/quarkus/

# Copy the public key for JWT validation
COPY --chown=jboss:jboss src/main/resources/publicKey.pem /deployments/jwt-key.pem

# Switch to jboss user
USER jboss

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "quarkus-run.jar"] 