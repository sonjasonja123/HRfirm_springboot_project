# Deployment Guide

## Production Deployment

### Backend Deployment

#### Option 1: JAR File
1. Build the application:
   ```bash
   mvn clean package
   ```

2. Run the JAR file:
   ```bash
   java -jar target/hr-management-system-0.0.1-SNAPSHOT.jar
   ```

#### Option 2: Docker
1. Create Dockerfile:
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY target/hr-management-system-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```

2. Build and run:
   ```bash
   docker build -t hr-management-backend .
   docker run -p 8080:8080 hr-management-backend
   ```

#### Option 3: Cloud Deployment
- **AWS**: Use Elastic Beanstalk or ECS
- **Google Cloud**: Use App Engine or Cloud Run
- **Azure**: Use App Service
- **Heroku**: Use Heroku CLI

### Frontend Deployment

#### Option 1: Static Hosting
1. Build the application:
   ```bash
   cd frontend
   npm run build
   ```

2. Deploy the `build` folder to:
   - **Netlify**: Drag and drop the build folder
   - **Vercel**: Connect your repository
   - **AWS S3**: Upload build folder
   - **GitHub Pages**: Use GitHub Actions

#### Option 2: Docker
1. Create Dockerfile:
   ```dockerfile
   FROM node:16-alpine as build
   WORKDIR /app
   COPY package*.json ./
   RUN npm install
   COPY . .
   RUN npm run build

   FROM nginx:alpine
   COPY --from=build /app/build /usr/share/nginx/html
   EXPOSE 80
   CMD ["nginx", "-g", "daemon off;"]
   ```

2. Build and run:
   ```bash
   docker build -t hr-management-frontend .
   docker run -p 80:80 hr-management-frontend
   ```

### Database Configuration

#### Production Database Setup
1. **PostgreSQL**:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/hr_management
       username: your_username
       password: your_password
       driver-class-name: org.postgresql.Driver
     jpa:
       database-platform: org.hibernate.dialect.PostgreSQLDialect
   ```

2. **MySQL**:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/hr_management
       username: your_username
       password: your_password
       driver-class-name: com.mysql.cj.jdbc.Driver
     jpa:
       database-platform: org.hibernate.dialect.MySQL8Dialect
   ```

3. Add database driver dependency to pom.xml:
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

### Environment Configuration

#### Backend Environment Variables
```bash
export SPRING_PROFILES_ACTIVE=production
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/hr_management
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
```

#### Frontend Environment Variables
Create `.env.production`:
```bash
REACT_APP_API_URL=https://your-backend-domain.com/api
```

### Security Configuration

#### Production Security
1. **HTTPS**: Use SSL certificates
2. **CORS**: Configure allowed origins
3. **JWT**: Use strong secret keys
4. **Database**: Use connection pooling
5. **Logging**: Configure proper logging levels

#### application-prod.yml
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  security:
    user:
      name: ${ADMIN_USERNAME:admin}
      password: ${ADMIN_PASSWORD:admin123}

server:
  port: 8080
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_KEYSTORE_PASSWORD}
    key-store-type: PKCS12

cors:
  allowed-origins: ${CORS_ALLOWED_ORIGINS:http://localhost:3000}

jwt:
  secret: ${JWT_SECRET:mySecretKey}
  expiration: 86400000

logging:
  level:
    com.hr: INFO
    org.springframework.security: WARN
    org.hibernate: WARN
```

### Monitoring and Logging

#### Application Monitoring
1. **Actuator**: Add Spring Boot Actuator
2. **Metrics**: Use Micrometer
3. **Health Checks**: Configure health endpoints
4. **Logging**: Use Logback or Log4j2

#### Add to pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### CI/CD Pipeline

#### GitHub Actions Example
```yaml
name: Deploy HR Management System

on:
  push:
    branches: [ main ]

jobs:
  build-backend:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Build with Maven
      run: mvn clean package
    - name: Deploy to production
      run: # Your deployment commands

  build-frontend:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up Node.js
      uses: actions/setup-node@v2
      with:
        node-version: '16'
    - name: Install dependencies
      run: cd frontend && npm install
    - name: Build
      run: cd frontend && npm run build
    - name: Deploy to production
      run: # Your deployment commands
```

### Load Balancing

#### Nginx Configuration
```nginx
upstream backend {
    server backend1:8080;
    server backend2:8080;
}

server {
    listen 80;
    server_name your-domain.com;

    location /api/ {
        proxy_pass http://backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location / {
        root /var/www/html;
        try_files $uri $uri/ /index.html;
    }
}
```

### Backup and Recovery

#### Database Backup
```bash
# PostgreSQL
pg_dump -h localhost -U username -d hr_management > backup.sql

# MySQL
mysqldump -h localhost -u username -p hr_management > backup.sql
```

#### Application Backup
1. **Code**: Use Git repositories
2. **Configuration**: Store in secure configuration management
3. **Files**: Regular file system backups
4. **Logs**: Centralized logging system

### Troubleshooting

#### Common Issues
1. **Database Connection**: Check connection strings and credentials
2. **CORS Issues**: Verify allowed origins configuration
3. **Memory Issues**: Monitor JVM heap size
4. **Performance**: Use profiling tools
5. **Security**: Regular security audits

#### Log Analysis
1. **Application Logs**: Check for errors and warnings
2. **Access Logs**: Monitor API usage
3. **Error Logs**: Track and fix errors
4. **Performance Logs**: Monitor response times

### Scaling

#### Horizontal Scaling
1. **Load Balancer**: Use Nginx or HAProxy
2. **Multiple Instances**: Run multiple backend instances
3. **Database Clustering**: Use database replication
4. **Caching**: Implement Redis or Memcached

#### Vertical Scaling
1. **Increase Memory**: Add more RAM
2. **Increase CPU**: Use more powerful processors
3. **Optimize Code**: Profile and optimize performance
4. **Database Optimization**: Index optimization, query tuning
