CREATE DATABASE ecoentorno;
USE ecoentorno;

-- tabla empleados
create table empleados (
    documento int primary key, -- documento como llave primaria
    nombre varchar(255) not null,
    segundo_nombre varchar(255),
    apellido varchar(255) not null,
    segundo_apellido varchar(255),
    correo varchar(255) unique, -- correo único
    telefono varchar(20),
    rol enum('administrador', 'usuario_epp', 'coordinador', 'operario') not null, -- rol del empleado
    edad int check (edad > 0), -- edad debe ser positiva
    descripcion text,
    fecha_registro date not null, -- fecha de registro obligatoria
    direccion varchar(255)
);

-- tabla credenciales, usando el documento como usuario para el login
create table credenciales (
    empleado_documento varchar(20) primary key, -- documento como nombre de usuario y llave primaria
    contrasena varchar(255) not null, -- contraseña para el inicio de sesión
    foreign key (empleado_documento) references empleados(documento) on delete cascade
);

-- tabla entregas_epp sin campo id, con documento como clave primaria
create table entregas_epp (
    id_entrega int auto_increment, -- Nuevo campo id como clave primaria
    empleado_documento int,
    nombre_ope varchar(255) not null,
    nombre_epp varchar(255) not null,
    cantidad int not null check (cantidad > 0), -- cantidad debe ser positiva
    fecha date not null,
    primary key (id_entrega), -- Clave primaria con el nuevo campo id
    foreign key (empleado_documento) references empleados(documento) on delete cascade -- Relación con empleados
);


-- tabla registros_pesos sin campo id, con documento como clave primaria
CREATE TABLE registros_pesos (
    id_registro INT AUTO_INCREMENT, -- identificador único para cada registro
    empleado_documento int,
    tipo ENUM('patologico', 'biosanitario') NOT NULL,
    peso DECIMAL(10, 2) NOT NULL CHECK (peso > 0),
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    PRIMARY KEY (id_registro), -- id_registro es ahora la clave primaria
    FOREIGN KEY (empleado_documento) REFERENCES empleados(documento) ON DELETE CASCADE
);


