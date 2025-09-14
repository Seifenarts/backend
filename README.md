# SeifenArts Backend Setup

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