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
│   │   └── com/checkmate/
│   │       ├── JakartaRestConfiguration.java
│   │       ├── Habit.java
│   │       ├── HabitLog.java
│   │       └── resources/
│   │           ├── JakartaEE10Resource.java
│   │           └── HabitsResource.java
│   ├── resources/
│   │   └── META-INF/
│   │       └── persistence.xml
│   └── webapp/
│       ├── index.html
│       ├── style.css
│       ├── tasks.html
│       ├── habits.html
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

The application exposes REST APIs under the `/api` path. Base URL: `http://localhost:8080/checkmate/api/`

Implemented endpoints include (see `src/main/java/com/checkmate/resources`):

- `JakartaEE10Resource.java` — example Jakarta EE resource (kept for demo/ping)
- `HabitsResource.java` — Habits Tracker API used by `habits.html`

Key Habits endpoints:

- `GET /api/habits` — list all habits
- `POST /api/habits` — create a new habit (JSON body: `{ "name": "..." }`)
- `GET /api/habits/{id}/logs?month=YYYY-MM` — get logs for a habit for a month
- `POST /api/habits/{id}/logs` — create/update a daily log (JSON body: `{ "date": "YYYY-MM-DD", "status": 0|1 }`)

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
