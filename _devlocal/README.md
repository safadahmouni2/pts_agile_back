# devlocal

Docker container / Kubernetes setup for containers used in developers environment

## Containers

### MySQL

Database

Use [init script](./mysql/initdb/schemas.sql) to create schemas for various microservices (via phpMyAdmin)

* Database user: root/root


Check connection status:

```sql
SELECT SCHEMA_NAME
  FROM INFORMATION_SCHEMA.SCHEMATA
 WHERE SCHEMA_NAME = 'DBName'
```

### phpMyAdmin

* [Open phpMyAdmin](http://localhost:8081)
* Login: root/root

