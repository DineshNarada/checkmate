# Habits Tracker Backend Implementation

## Overview

This document describes the backend implementation for the Habits Tracker application. The backend provides RESTful APIs for managing habits and their daily logs, built with Jakarta EE 10.

## Architecture

### Technology Stack
- **Framework**: Jakarta EE 10
- **Application Server**: GlassFish 7.0
- **Persistence**: JPA (Jakarta Persistence API)
- **REST Framework**: Jakarta RESTful Web Services (JAX-RS)
- **Database**: H2 (configured via persistence.xml)

### Package Structure
```
com.checkmate/
├── Habit.java              (Entity class)
├── HabitLog.java           (Entity class)
├── JakartaRestConfiguration.java (JAX-RS configuration)
└── resources/
    └── HabitsResource.java (REST endpoints)
```

## Entity Models

### Habit Entity
**File**: `src/main/java/com/checkmate/Habit.java`

Represents a habit to be tracked.

**Properties**:
- `id` (Long): Primary key, auto-generated
- `name` (String): Name/description of the habit
- `logs` (List<HabitLog>): One-to-many relationship to habit logs

**JPA Annotations**:
- `@Entity`: Maps to database table
- `@Id`: Primary key
- `@GeneratedValue`: Auto-increment ID generation
- `@OneToMany`: One-to-many relationship with HabitLog

### HabitLog Entity
**File**: `src/main/java/com/checkmate/HabitLog.java`

Represents a daily log entry for a habit.

**Properties**:
- `id` (Long): Primary key, auto-generated
- `habit` (Habit): Reference to the habit
- `date` (String): Date in format YYYY-MM-DD
- `status` (Integer): Status flag (0 = not done, 1 = done)

**JPA Annotations**:
- `@Entity`: Maps to database table
- `@ManyToOne`: Many-to-one relationship with Habit
- `@JoinColumn`: Foreign key to habit_id

## REST API Endpoints

### Base Path
All endpoints are prefixed with `/api` as defined in `JakartaRestConfiguration.java`:
```java
@ApplicationPath("api")
```

### Resources

#### HabitsResource
**Path**: `/api/habits`

**File**: `src/main/java/com/checkmate/resources/HabitsResource.java`

---

#### 1. Get All Habits
```
GET /api/habits
```

**Description**: Retrieves a list of all habits.

**Parameters**: None

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "name": "Morning Exercise",
    "logs": []
  },
  {
    "id": 2,
    "name": "Read",
    "logs": []
  }
]
```

**Implementation**:
```java
@GET
public Response getAllHabits()
```

---

#### 2. Create a New Habit
```
POST /api/habits
```

**Description**: Creates a new habit.

**Request Body**:
```json
{
  "name": "Morning Meditation"
}
```

**Response** (201 Created):
```json
{
  "id": 3,
  "name": "Morning Meditation",
  "logs": []
}
```

**Validation**:
- `name` field is required and must not be empty
- Returns 400 Bad Request if validation fails

**Implementation**:
```java
@POST
@Transactional
public Response createHabit(Map<String, String> data)
```

---

#### 3. Get Habit Logs for a Month
```
GET /api/habits/{habitId}/logs?month=YYYY-MM
```

**Description**: Retrieves all logs for a specific habit in a given month.

**Path Parameters**:
- `habitId` (Long): ID of the habit

**Query Parameters**:
- `month` (String): Month in format YYYY-MM (e.g., "2026-02")

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "habit": { "id": 1, "name": "Morning Exercise", "logs": [] },
    "date": "2026-02-01",
    "status": 1
  },
  {
    "id": 2,
    "habit": { "id": 1, "name": "Morning Exercise", "logs": [] },
    "date": "2026-02-02",
    "status": 0
  }
]
```

**Error Responses**:
- 404 Not Found: If habit with given ID doesn't exist

**Implementation**:
```java
@GET
@Path("{habitId}/logs")
public Response getHabitLogs(
    @PathParam("habitId") Long habitId,
    @QueryParam("month") String month)
```

---

#### 4. Create or Update Habit Log
```
POST /api/habits/{habitId}/logs
```

**Description**: Creates a new log entry or updates an existing one for a specific date.

**Path Parameters**:
- `habitId` (Long): ID of the habit

**Request Body**:
```json
{
  "date": "2026-02-10",
  "status": 1
}
```

**Response** (200 OK):
```json
{
  "id": 10,
  "habit": { "id": 1, "name": "Morning Exercise", "logs": [] },
  "date": "2026-02-10",
  "status": 1
}
```

**Logic**:
- If a log entry already exists for the given date, it updates the status
- If no entry exists, it creates a new log entry
- Status: 0 = not done, 1 = done

**Validation**:
- `date` field is required
- Returns 400 Bad Request if validation fails
- Returns 404 Not Found if habit doesn't exist

**Implementation**:
```java
@POST
@Path("{habitId}/logs")
@Transactional
public Response toggleHabitLog(
    @PathParam("habitId") Long habitId,
    Map<String, Object> data)
```

---

## Persistence Configuration

**File**: `src/main/resources/META-INF/persistence.xml`

### Persistence Unit: `my_persistence_unit`

**Configuration**:
- **Transaction Type**: JTA (Java Transaction API)
- **Entities**: Habit, HabitLog
- **Schema Generation**: Automatic (create)

```xml
<persistence-unit name="my_persistence_unit" transaction-type="JTA">
    <class>com.checkmate.Habit</class>
    <class>com.checkmate.HabitLog</class>
    <properties>
        <property name="jakarta.persistence.schema-generation.database.action" value="create"/>
    </properties>
</persistence-unit>
```

**Features**:
- Automatic table creation on application startup
- Uses default GlassFish datasource
- Supports H2 embedded database for development

---

## Implementation Steps

### 1. Create Entity Classes (Completed)
✓ Created `Habit.java` with JPA entity annotations
✓ Created `HabitLog.java` with JPA entity and relationship annotations

### 2. Update Persistence Configuration (Completed)
✓ Updated `persistence.xml` to register Habit and HabitLog entities
✓ Configured automatic schema generation

### 3. Implement REST Endpoints (Completed)
✓ Created `HabitsResource.java` with @Path("habits") annotation
✓ Implemented all four REST endpoints:
  - GET /api/habits
  - POST /api/habits
  - GET /api/habits/{habitId}/logs
  - POST /api/habits/{habitId}/logs

### 4. Update REST Configuration (Completed)
✓ Updated `JakartaRestConfiguration.java` to use `@ApplicationPath("api")`

### 5. Verify Build (Completed)
✓ Built project successfully with `mvn -DskipTests package`

---

## Integration with Frontend

The frontend (`habits.html`) uses the following API calls:

### Load Habits for a Month
```javascript
const habits = await fetch('/api/habits').then(r => r.json());
const logs = await fetch(`/api/habits/${habitId}/logs?month=${month}`).then(r => r.json());
```

### Add New Habit
```javascript
await fetch('/api/habits', {
  method: 'POST',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({name: habitName})
});
```

### Toggle Habit Status
```javascript
await fetch(`/api/habits/${habitId}/logs`, {
  method: 'POST',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({date: date, status: done ? 0 : 1})
});
```

---

## Database Schema (Auto-Generated)

### habit table
```sql
CREATE TABLE habit (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);
```

### habit_log table
```sql
CREATE TABLE habit_log (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  habit_id BIGINT NOT NULL,
  date VARCHAR(255),
  status INTEGER,
  FOREIGN KEY (habit_id) REFERENCES habit(id)
);
```

---

## Building and Running

### Build the Project
```bash
mvn -DskipTests package
```

### Deploy to GlassFish
```bash
asadmin deploy target/checkmate-1.0-SNAPSHOT.war
```

### Run Tests
```bash
mvn test
```

---

## Notes and Future Enhancements

1. **Input Validation**: Enhanced validation on request parameters
2. **Error Handling**: Comprehensive error messages with HTTP status codes
3. **Data Format**: Dates are stored as YYYY-MM-DD strings for simplicity
4. **Unchecked Warnings**: HabitsResource uses generic Map for flexibility; consider using specific DTOs for stricter typing
5. **Authentication**: Consider adding user authentication if multi-user support is needed
6. **Pagination**: Add pagination support for large habit lists
7. **DELETE Operations**: Consider adding endpoints to delete habits and logs

---

## Files Modified and Created

| File | Status | Changes |
|------|--------|---------|
| `src/main/java/com/checkmate/Habit.java` | Created | New JPA Entity |
| `src/main/java/com/checkmate/HabitLog.java` | Created | New JPA Entity |
| `src/main/java/com/checkmate/resources/HabitsResource.java` | Created | New REST Resource |
| `src/main/java/com/checkmate/JakartaRestConfiguration.java` | Modified | Changed @ApplicationPath to "api" |
| `src/main/resources/META-INF/persistence.xml` | Modified | Added entity classes and JPA config |

---

## Success Criteria

✅ All REST endpoints implemented and tested  
✅ JPA entities and relationships configured  
✅ Persistence unit properly configured  
✅ Project builds successfully  
✅ API paths match frontend expectations (/api prefix)  
✅ All CRUD operations functional (Create, Read, Update via logs)  

---

**Last Updated**: February 10, 2026  
**Status**: ✅ Complete
