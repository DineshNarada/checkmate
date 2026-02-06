# Checkmate - Daily Tasks Tracker

A Jakarta EE 10 web application for tracking and managing your daily tasks. Stay organized and maintain consistency with your task management.

## Overview

Checkmate is your partner in helping you stay well-organized. This application provides a simple and effective way to manage your daily tasks using modern Jakarta EE technologies.

## Features

- **Task Management**: Create, view, and organize your daily tasks
- **Web-based Interface**: Clean and user-friendly web application
- **RESTful API**: Built with Jakarta RESTful Web Services (JAX-RS)
- **Persistent Storage**: Database integration via JPA/Hibernate

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/checkmate/checkmate/
│   │       ├── JakartaRestConfiguration.java
│   │       └── resources/
│   │           └── JakartaEE10Resource.java
│   ├── resources/
│   │   └── META-INF/
│   │       └── persistence.xml
│   └── webapp/
│       ├── index.html
│       ├── style.css
│       ├── tasks.html
│       └── WEB-INF/
│           ├── beans.xml
│           ├── glassfish-web.xml
│           └── web.xml
```

## Technology Stack

- **Jakarta EE**: 10.0.0
- **Java**: 11+
- **Build Tool**: Maven 3.9
- **Server**: GlassFish 7.0
- **Frontend**: HTML5, CSS3

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- GlassFish 7.0 (or compatible Jakarta EE 10 application server)

## Building the Project

### Clone the repository
```bash
git clone <repository-url>
cd checkmate
```

### Build with Maven
```bash
mvn clean package
```

This command will:
- Clean previous build artifacts
- Compile the project
- Run any tests
- Package the application as a WAR file

The built WAR file will be located at: `target/checkmate-1.0-SNAPSHOT.war`

## Deployment

### Deploy to GlassFish

1. Start your GlassFish server
2. Deploy the WAR file:
   ```bash
   asadmin deploy target/checkmate-1.0-SNAPSHOT.war
   ```

3. Access the application at: `http://localhost:8080/checkmate`

## Usage

1. Open your browser and navigate to the application home page
2. Click "Go to Tasks Page" to access the task management interface
3. Create and manage your daily tasks

## Configuration Files

- **persistence.xml**: JPA persistence unit configuration
- **beans.xml**: CDI (Contexts and Dependency Injection) configuration
- **web.xml**: Web application deployment descriptor
- **glassfish-web.xml**: GlassFish-specific web configuration
- **pom.xml**: Maven project configuration

## Development

### Running in Development Mode

```bash
mvn clean compile
```

### IDE Setup

This project can be opened in any IDE that supports:
- Maven projects
- Jakarta EE development (NetBeans, IntelliJ IDEA, Eclipse)

### Project Properties

```xml
Java Source: 11
Java Target: 11
Project Encoding: UTF-8
Packaging: WAR (Web Application Archive)
```

## REST API Endpoints

The application provides REST API endpoints under the `/resources` path. Base URL: `http://localhost:8080/checkmate/resources/`

Refer to `JakartaEE10Resource.java` for available endpoints.

## Contributing

Contributions are welcome! Please ensure:
- Code follows the project's style guidelines
- All tests pass
- Changes are well-documented

## License

License will be added soon.

## Author

- Dinesh Narada

## Support

For issues, questions, or suggestions, please create an issue in the project repository.
