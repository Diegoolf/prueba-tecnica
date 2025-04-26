-- Crear base de datos (SQL Server no usa "IF NOT EXISTS" así directamente)
IF DB_ID('inventario_almacen1') IS NULL
    CREATE DATABASE inventario_almacen1;
GO

USE inventario_almacen1;
GO

-- Tabla de roles
CREATE TABLE roles (
    idRol INT PRIMARY KEY,
    nombreRol VARCHAR(50) NOT NULL
);
GO

-- Tabla de usuarios
CREATE TABLE usuarios (
    idUsuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    idRol INT NOT NULL,
    estatus INT DEFAULT 1,
    FOREIGN KEY (idRol) REFERENCES roles(idRol)
);
GO

-- Tabla de productos
CREATE TABLE productos (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(250),
    cantidad INT DEFAULT 0,
    estatus INT DEFAULT 1
);
GO

-- Tabla de movimientos
CREATE TABLE movimientos (
    idMovimiento INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    tipo_movimiento VARCHAR(10) CHECK (tipo_movimiento IN ('entrada', 'salida')),
    cantidad INT NOT NULL,
    fecha_hora DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(idUsuario)
);

GO

insert into roles (idRol, nombreRol) values (1,'Administrador');
insert into roles (idRol, nombreRol) values (2,'Almacenista');

INSERT INTO usuarios (nombre, correo, contrasena, estatus, idRol)
VALUES (
    'Administrador',
    'admin123@example.com',
    '$2a$12$XIWRtzfAY3m.CSR6ljCVuuzI7nruSasIWb4oQvHp3EBPPaO5vS0hy', -- admin123 en BCrypt
    1,
    1 -- ID del rol ADMINISTRADOR
);
