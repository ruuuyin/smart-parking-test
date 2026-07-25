# Smart Park

Parking lot management API built with Spring Boot. Register parking lots and vehicles, check vehicles in and out, and query occupancy and parked vehicles.

## Requirements

| Dependency | Version |
|---|---|
| Java (JDK) | 21+ |
| Maven | 3.9+ (or use the included Maven Wrapper) |

No external database is required. The app uses an in-memory H2 database with Flyway migrations.

Internet access is needed for the first build so Maven can download the project dependencies (and, if you use the wrapper, Maven itself).

## Initial setup

### 1. Clone the repository

```bash
git clone <repository-url>
cd smart-park
```

### 2. Verify your JDK

```bash
java -version
```

You need JDK 21 or newer. If the command reports an older version, install a JDK 21+ distribution (for example [Eclipse Temurin](https://adoptium.net/)) and point `JAVA_HOME` at it.

**Windows (current session)**

```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21"
```

**macOS / Linux (current session)**

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)   # macOS
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk      # Linux
```

### 3. Install the Maven dependencies

The repository ships with the Maven Wrapper, so you do not need Maven pre-installed. The first wrapper command downloads Maven 3.9.16 automatically, then resolves all project dependencies into your local repository (`~/.m2/repository`).

**Windows**

```powershell
.\mvnw.cmd clean install -DskipTests
```

**macOS / Linux**

```bash
./mvnw clean install -DskipTests
```

The first run takes a few minutes. Subsequent runs are served from the local cache.

### 4. IDE setup (optional)

This project uses Lombok and MapStruct, both of which run as annotation processors during compilation:

- Enable annotation processing in your IDE (IntelliJ IDEA: **Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing**).
- Install the Lombok plugin if your IDE does not bundle it.
- Generated mappers land in `target/generated-sources/annotations`; run a build once so the IDE can resolve them.

### 5. Troubleshooting

| Problem | Fix |
|---|---|
| `Unsupported class file major version` or compile errors | Ensure `JAVA_HOME` points to JDK 21+, not an older JDK |
| Dependency resolution fails behind a proxy | Configure a proxy in `~/.m2/settings.xml` |
| Stale or partial downloads | Force re-resolution with `.\mvnw.cmd clean install -U` |
| `mvnw: Permission denied` (macOS / Linux) | `chmod +x mvnw` |
| Cannot resolve generated mapper classes | Run a build, then refresh/reimport the Maven project in your IDE |

## Quick start

### Using the Maven Wrapper (recommended)

**Windows**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux**

```bash
./mvnw spring-boot:run
```

### Using a local Maven install

```bash
mvn spring-boot:run
```

The API starts at [http://localhost:8080](http://localhost:8080).

### Optional environment variables

| Variable | Default | Description |
|---|---|---|
| `DB_USERNAME` | `sa` | H2 username |
| `DB_PASSWORD` | `password` | H2 password |

Example:

```powershell
$env:DB_USERNAME="sa"; $env:DB_PASSWORD="password"; .\mvnw.cmd spring-boot:run
```

### H2 console

With the app running, open the H2 console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

- **JDBC URL:** `jdbc:h2:mem:smart-park`
- **User:** `sa` (or `DB_USERNAME`)
- **Password:** `password` (or `DB_PASSWORD`)

## Seed data

On startup, Flyway loads sample data:

| Parking lot | Location | Capacity |
|---|---|---|
| `LOT_001` | Downtown | 10 |
| `LOT_002` | Uptown | 10 |
| `LOT_003` | Suburb | 10 |

| License plate | Type | Owner |
|---|---|---|
| `XYZ123` | CAR | John Doe |
| `XYZ124` | MOTORCYCLE | John Doe 2 |
| `XYZ125` | TRUCK | John Doe 3 |

`XYZ123` and `XYZ124` start checked in; `XYZ125` starts checked out.

## API overview

JSON request and response fields use **camelCase** (for example `licensePlate`, `lotId`).

### Postman collection

A ready-to-import Postman collection is available at:

[`postman/Smart Park.postman_collection.json`](postman/Smart Park.postman_collection.json)

It was generated from the Spring REST Docs snippets under `target/generated-snippets` and includes success and error examples for every API test case.

**Import in Postman:** File → Import → select `postman/Smart Park.postman_collection.json`.

The collection variable `baseUrl` defaults to `http://localhost:8080`. Start the app before sending requests.

### Vehicles

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/vehicle` | Register a vehicle |

```json
{
  "license_plate": "ABC-123",
  "vehicle_type": "CAR",
  "ownerName": "Jane Smith"
}
```

`vehicle_type` values: `CAR`, `MOTORCYCLE`, `TRUCK`.

### Parking lots

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/parking-lot` | Register a parking lot |
| `POST` | `/api/parking-lot/vehicle/check-in` | Check a vehicle in |
| `POST` | `/api/parking-lot/vehicle/check-out` | Check a vehicle out |
| `GET` | `/api/parking-lot/{lotId}` | Get lot details / occupancy |
| `GET` | `/api/parking-lot/{lotId}/vehicles?page=0&size=10` | List vehicles in a lot |

**Register lot**

```json
{
  "lot_id": "LOT_004",
  "location": "Airport",
  "capacity": 50
}
```

**Check in**

```json
{
  "lot_id": "LOT_001",
  "license_plate": "ABC-123"
}
```

**Check out**

```json
{
  "license_plate": "ABC-123"
}
```

### Example request

```powershell
curl -X POST http://localhost:8080/api/vehicle `
  -H "Content-Type: application/json" `
  -d "{\"licensePlate\":\"ABC-123\",\"vehicleType\":\"CAR\",\"ownerName\":\"Jane Smith\"}"
```

## Testing

API tests use Spring Boot Test, MockMvc, and Spring REST Docs against the same in-memory H2 setup.

### Run all tests

**Windows**

```powershell
.\mvnw.cmd test
```

**macOS / Linux**

```bash
./mvnw test
```

### Run a single test class

```powershell
.\mvnw.cmd test -Dtest=VehicleRegistrationApiTest
```

### Test classes

| Class | Covers |
|---|---|
| `VehicleRegistrationApiTest` | Vehicle registration |
| `ParkingLotRegistrationApiTest` | Parking lot registration |
| `CheckInApiTest` | Vehicle check-in |
| `CheckOutApiTest` | Vehicle check-out |
| `GetParkingLotOccupancyStatusApiTest` | Lot occupancy |
| `GetVehiclesInParkingLotApiTest` | Vehicles in a lot |

## Build

Package a runnable JAR:

```powershell
.\mvnw.cmd clean package
```

Skip tests during packaging:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Run the JAR:

```powershell
java -jar target\smart-park-0.0.1-SNAPSHOT.jar
```

## Project structure

```
src/main/java/com/royeen/smartpark/
├── controller/          # REST endpoints
├── business/            # Services and use cases
├── gateway/repository/  # JPA repositories
├── models/              # Domain, entity, and presentation models
├── exceptions/          # Error handling
└── SmartParkApplication.java

src/main/resources/
├── application.properties
└── db/migration/        # Flyway SQL migrations

src/test/java/com/royeen/smartpark/api/
└── *ApiTest.java        # Integration-style API tests

postman/
└── Smart Park.postman_collection.json
```
