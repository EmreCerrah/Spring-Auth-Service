# All-in-one image for Auth Service with PostgreSQL and Zipkin
FROM eclipse-temurin:21-jdk-jammy AS base

# Set environment variables to reduce noise and improve performance
ENV DEBIAN_FRONTEND=noninteractive \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    TZ=UTC

# Install PostgreSQL and utilities in a single layer to reduce image size
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        postgresql \
        postgresql-contrib \
        curl \
        ca-certificates \
        tzdata && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    # Create directory for PostgreSQL init scripts
    mkdir -p /docker-entrypoint-initdb.d

# Install Zipkin
RUN curl -sSL https://zipkin.io/quickstart.sh | bash -s && \
    mv zipkin.jar /opt/zipkin.jar

# Create a non-root user to run the application
RUN groupadd -r appuser && \
    useradd -r -g appuser -d /home/appuser -m appuser && \
    mkdir -p /opt/app && \
    chown -R appuser:appuser /opt/app

# Copy application files
COPY target/auth-service.jar /opt/app/app.jar
COPY docker-entrypoint-initdb.d /docker-entrypoint-initdb.d/
COPY start.sh /start.sh

# Set proper permissions
RUN chmod +x /start.sh && \
    chown -R appuser:appuser /docker-entrypoint-initdb.d

# Expose ports
EXPOSE 8090 5432 9411

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8090/actuator/health || exit 1

# Set the entrypoint
ENTRYPOINT ["/start.sh"]
