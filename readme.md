sync-data
==========

My pet program. Main features is:

* uploads the contents of a table from the database into XML file
* synchronizes the contents of the database tables on the XML file.

## Rules:

  * If file exists the application will upload the content of database table
     Example XML file:
```

      <?xml version="1.0" encoding="UTF-8"?>
      <Departments>
          <Department DepCode="ABC130" DepJob="Install">Description 1</Department>
          <Department DepCode="ABC137" DepJob="Developing">Description 2</Department>
          <Department DepCode="ABC138" DepJob="Developing">Description 3</Department>
          <Department DepCode="ABC139" DepJob="Testing">Description 4</Department>
      </Departments>

```

  * If file does not exist the application will synchronize with database content

## Requirements:

  * Java SE Development Kit 7 (or newer)
  * Apache Maven 3.x
  * H2 database
  * Git 1.7.x (or newer)

## Preinstall

  For database need execute SQL script:
  ```
  
  CREATE TABLE DEPARTMENTS(
      ID INT PRIMARY KEY AUTO_INCREMENT,
      DEP_CODE VARCHAR(20) NOT NULL,
      DEP_JOB VARCHAR(100) NOT NULL,
      DESCRIPTION VARCHAR(255),
      UNIQUE KEY DEP_UNIQUE (DEP_CODE, DEP_JOB)
   )
   
```

  Application configuration contains in `/conf` path. log4j.properties - logger configuration. sync.properties - application configuration

## Build

   * Call command `mvn clean package`
   * Distribution is in 'sync-data\distribution\target>' by file name distribution-1.0-SNAPSHOT-bin.zip.
