# JDBC APP

## Introduction 
The purpose of this application is to allow user use JDBC to connect with PotsgresSQl databae and implement CRUD (create, read, update,delete) operations by using Data Access Object(DAO) pattern. This project is to follow [Learning JDBC course](https://www.linkedin.com/learning/learning-jdbc/get-going-with-data-access-in-java) by Frank Moley. The project make me learn about the concepts of Data Access Objects (DAO) pattern, Data Transfer Objects(DTO) pattern, Repository patterns, CRUD operations, transactions and commits & rollbacks as well as exception handling in JDBC.

## ER Diagram

![](https://github.com/jarviscanada/jarvis_data_eng_cecilia/blob/develop/core_java/jdbc/assets/ER%20Diagram.png)

## Usage & Structure 
To set up database, you can either run [psql_docker](https://github.com/jarviscanada/jarvis_data_eng_cecilia/blob/master/linux_sql/scripts/psql_docker.sh) to start and create new database or u can follow the actions below :
### Running PostgreSQL
1. Pull Docker Image
`docker pull postgres`

2. Build data directory
`mkdir -p ~/srv/postgres`

3. Run docker image
`docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres`

### Stopping PostgreSQL
`docker stop lil-postgres`

### Logging into Database
* `psql -h localhost -U postgres -d hplussport`

### Creating starter data
1. `psql -h localhost -U postgres -f database.sql`
2. `psql -h localhost -U postgres -d hplussport -f customer.sql`
3. `psql -h localhost -U postgres -d hplussport -f product.sql`
4. `psql -h localhost -U postgres -d hplussport -f salesperson.sql`
5. `psql -h localhost -U postgres -d hplussport -f orders.sql`

![](https://github.com/jarviscanada/jarvis_data_eng_cecilia/blob/develop/core_java/jdbc/assets/diagram.png)

The diagram shows the relations between each files. The JDBCExecutor are set to perform all four CRUD operations on customer table and order table. These operations are implements for customer and order two tables in custmer & custormerDAO and order & orderDAO files. 

## Design patterns
The Data Access Patterns (DAO) is an abstraction of data persistence. It's considered closer to the database, often table-centric.It can be used isolate application from database. User can change application or databse without impact others by using DAO. DAO often work with Data Transform Object (DTO) as abtraction layer. DTO provide a single domain of data and fully encapsulated object. The input and output of single DAO should be a single DTO and all of its child objects. This pattern can and usually support multiple tables, but it also can be a single table. 

The repository pattern is an abtraction of a collection of objects. It would considered closer to domain, dealing only in aggregate roots. Repository could implemented by using DAO but can't opposite. Repository pattern focus only on single table for each class. When using a Repository, changes to entities would usually be tracked by separate unit of work. 
