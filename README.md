# Prueba_Tecnica_sistema_de_inventario-

para este pequeño sistema tenemos los siguientes datos relevantes para su
desarrollo:
    - IDE: Visual studio code
    - Versión del lenguaje de programación utilizado: Java 21
    - DBMS utilizado y su versión: Microsoft SQL Server 2022 (RTM) - 16.0.1000.6

pasos para correr la aplicacion:
  1.- Pre-requisitos instalados
    - Java JDK 17 o superior
    - Maven 
    - IDE como IntelliJ, VS Code o Spring Tool Suite
    - SQL Server activo
    - SQL Server Management Studio (SSMS) u otra herramienta para manejar la BD

  2.- Crear la base de datos
    -Ejecutar el script SQL(se encuentra en la carpeta Scripts) para crear la base de datos inventario_almacen y sus tablas.

  3.- Configurar application.properties con las credenciales correspondientes(usuario y contraseña sql). 
      spring.application.name=prueba-tecnica
      spring.datasource.url=jdbc:sqlserver://localhost;databaseName=inventario_almacen;encrypt=true;trustServerCertificate=true
      spring.datasource.username=TU_USUARIO_SQL
      spring.datasource.password=TU_CONTRASENA_SQL
      spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
      # JPA / Hibernate
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

 4.- Ejecutar la aplicacion y acceder a ella:Abre un navegador y ve a la URL:http://localhost:8080/

Nota: toma en cuenta que en el registro solo se pueden registrar usuarios con el rol de almacenista, para ingresar como administrador ya se cuenta con un perfil que es: Correo: admin123@example.com Contrasena: admin123

  
