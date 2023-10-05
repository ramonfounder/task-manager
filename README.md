
---

# [Task Manager]


## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   ├── com/
│   │   │   │   ├── me.thejramon.taskmanager/
│   │   │   │   │   ├── controller/  - REST controllers for handling web requests.
│   │   │   │   │   ├── domain/       - Entity classes and other data models.
│   │   │   │   │   ├── repository/  - JPA repositories.
│   │   │   │   │   ├── service/     - Business logic and service layer.
│   │   │   │   │   └── ...          - Other packages and classes.
│   │   ├── resources/              - Configuration ymls and other resources.
│   ├── test/                       - Unit and integration tests.
└── build.gradle.kts                - Gradle build script.
```

## Prerequisites

- Java JDK (Version 17 recommended)
- IntelliJ IDE
- Gradle
- Docker

## Installation & Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/thejramon/task-manager.git
   cd task-manager
   ```

2. **Build the project**

   ```bash
   gradle build
   ```

3. **Run the application**

   ```bash
   gradle bootRun
   ```

   Alternatively, you can run the built JAR file:

   ```bash
   java -jar build/libs/yourprojectname-0.0.1-SNAPSHOT.jar
   ```

## Usage



```http
GET /api/tasks/
POST /api/tasks/
GET /api/tasks/{id}
```

...and so on.

## Testing


```bash
gradle test
```

## Contributing

## License

---
