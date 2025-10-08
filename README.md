# SeifenArts Backend

**SeifenArts** ist ein realer Online-Shop für handgemachte Seifenbouquets.  
Dieses Backend stellt die API und Datenlogik für das Frontend bereit.  
Es bietet sichere Benutzerverwaltung, Produktverwaltung und grundlegende Administratorfunktionen.

---

## 🧩 Funktionen

- Ausgabe von Produkten mit Pagination  
- CRUD-Operationen für Produkte  
- Benutzerregistrierung, Login und Passwort-Wiederherstellung  
- Administratorfunktionen (Produktmanagement, Benutzerverwaltung)  
- Validierung der Eingaben  
- Automatische Swagger-Dokumentation  
- Sicherer Zugriff auf API-Endpunkte  

---

## 🛠️ Tech Stack

- **Java 17**  
- **Spring Boot 3.5.3**  
- **Spring Security** (JWT-basierte Authentifizierung und Autorisierung)  
- **Spring Data JPA + Hibernate**  
- **PostgreSQL**  
- **MapStruct** (DTO-Mapper)  
- **Lombok** (Reduzierung von Boilerplate-Code)  
- **Spring Validation**  
- **OpenAPI / Swagger UI** (API-Dokumentation)  
- **Maven** (Build-Management)  

---

## 🗄️ Datenbank Setup

### Verbindung mit PostgreSQL

1. Öffne **pgAdmin**  
2. Verbinde dich mit deinem PostgreSQL-Server mit folgenden Daten:

## 🚀 Starten des Projekts

### Voraussetzungen

- JDK 17 oder höher  
- Maven 3.8+  
- PostgreSQL 14+  

### Installation und Start

```bash
git clone https://github.com/LutsDM/seifenarts-backend.git
cd seifenarts-backend
mvn clean install
mvn spring-boot:run


## Database Connection

1. Open pgAdmin
2. Connect to your PostgreSQL server using:
    - Host: `localhost`
    - Port: `5432`
    - Username: `postgres`
    - Password: `(your_postgres_password)`

3. Open the Query Tool and execute:

```sql
-- Create a new role/user
CREATE ROLE username WITH LOGIN PASSWORD '1234';

-- Create a new database owned by this user
CREATE DATABASE seifenarts OWNER username;
