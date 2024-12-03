select e.nombre, sum(v.total) as 'Total', count(v.id) as 'Cantidad' from
empleados e join ventas v on e.id = v.idEmpleado
group by e.id;

select * from ventas where fecha between '2024-01-01' and '2025-01-01';
select * from detalles_venta;
select * from ventas;

delete from ventas where id = 99;
select * from AuditoriaVentas;

select 
	p.descripcion,
    (select count(dv.idProducto)
	from detalles_venta dv join ventas v on dv.idVenta = v.id
	where dv.idProducto = p.id and month(v.fecha) between 1 and 3
	group by dv.idProducto) as 'Trimestre 1',
    (select count(dv.idProducto)
	from detalles_venta dv join ventas v on dv.idVenta = v.id
	where dv.idProducto = p.id and month(v.fecha) between 4 and 6
	group by dv.idProducto) as 'Trimestre 2',
    (select count(dv.idProducto)
	from detalles_venta dv join ventas v on dv.idVenta = v.id
	where dv.idProducto = p.id and month(v.fecha) between 7 and 9
	group by dv.idProducto) as 'Trimestre 3',
    (select count(dv.idProducto)
	from detalles_venta dv join ventas v on dv.idVenta = v.id
	where dv.idProducto = p.id and month(v.fecha) between 10 and 12
	group by dv.idProducto) as 'Trimestre 4'
from productos p 
join detalles_venta dv on p.id = dv.idProducto 
join ventas v on v.id = dv.idVenta
group by p.id;

select v.id, v.fecha, c.nombre as 'cliente', e.nombre as 'empleado', v.total, CantidadProductos(v.id) as 'CantDeatalles'
from empleados e
join ventas v on e.id = v.idEmpleado
join clientes c on v.idCliente = c.id
where month(v.fecha) = 2 and year(v.fecha) = 2024;


drop database rangel_yuriria;
CREATE DATABASE rangel_yuriria;
USE rangel_yuriria;
select * from ventas;

select * from ventas;
select * from detalles_venta;
call GenerarVentasSimuladas(100);

CREATE TABLE EMPLEADOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rfc VARCHAR(13) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    edad TINYINT NOT NULL,
    sexo ENUM('M', 'F') NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(10),
    correo VARCHAR(50),
    fechaNacimiento DATE NOT NULL,
    fechaContratacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    puesto ENUM('Admin', 'Cajero') NOT NULL,
    foto MEDIUMBLOB,
    ultimaModificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE USUARIOS (
    usuario VARCHAR(10) PRIMARY KEY,
    contrasena VARCHAR(64) NOT NULL,
    idEmpleado INT,
    FOREIGN KEY (idEmpleado) REFERENCES EMPLEADOS(id) ON DELETE CASCADE
);

CREATE TABLE CATEGORIAS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE PRODUCTOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigoBarras VARCHAR(20) NOT NULL,
	clave VARCHAR(20) NOT NULL, 
    descripcion TEXT NOT NULL,
    marca VARCHAR(50),
    precio DECIMAL(10, 2) NOT NULL,
    descuento TINYINT,
    stock INT NOT NULL,
    idCategoria INT,
    foto MEDIUMBLOB,
    ultimaModificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idCategoria) REFERENCES CATEGORIAS(id)
);

CREATE TABLE CLIENTES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    rfc VARCHAR(13) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    ultimaModificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE VENTAS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME,
    subTotal DECIMAL(10, 2) NOT NULL,
    iva DECIMAL(10, 2) NOT NULL,
    descuento DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    idEmpleado INT,
    idCliente INT,
    FOREIGN KEY (idEmpleado) REFERENCES EMPLEADOS(id),
    FOREIGN KEY (idCliente) REFERENCES CLIENTES(id)
);
ALTER TABLE VENTAS
MODIFY COLUMN fecha DATETIME DEFAULT CURRENT_TIMESTAMP;

select * from ventas;
select * from detalles_venta;
call GenerarVentasSimuladas(100);

drop table ventas;
drop table DETALLES_VENTA;
CREATE TABLE DETALLES_VENTA (
	id INT AUTO_INCREMENT PRIMARY KEY,
    idVenta INT NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descuento DECIMAL(10,2),
    FOREIGN KEY (idVenta) REFERENCES VENTAS(id) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES PRODUCTOS(id)
);

CREATE TABLE AuditoriaVentas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    accion ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    idVenta INT NOT NULL,
    usuario VARCHAR(50),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$
select * from ventas$$
select CantidadProductos(1)$$
CREATE FUNCTION CantidadProductos(id INT)
RETURNS INT
DETERMINISTIC
BEGIN
    RETURN (SELECT COUNT(*) FROM DETALLES_VENTA WHERE idVenta = id);
END$$

CREATE PROCEDURE InsertarCliente(
    IN p_nombre VARCHAR(255),
    IN p_rfc VARCHAR(13),
    IN p_direccion VARCHAR(255),
    IN p_telefono VARCHAR(10),
    IN p_correo VARCHAR(255)
)
BEGIN
    INSERT INTO CLIENTES (nombre, rfc, direccion, telefono, correo)
    VALUES (p_nombre, p_rfc, p_direccion, p_telefono, p_correo);
END$$

CREATE PROCEDURE ActualizarCliente(
    IN p_id INT,
    IN p_nombre VARCHAR(255),
    IN p_rfc VARCHAR(13),
    IN p_direccion VARCHAR(255),
    IN p_telefono VARCHAR(15),
    IN p_correo VARCHAR(255)
)
BEGIN
    UPDATE CLIENTES
    SET nombre = p_nombre, rfc = p_rfc, direccion = p_direccion, telefono = p_telefono, correo = p_correo
    WHERE id = p_id;
END$$

CREATE PROCEDURE EliminarCliente(IN p_id INT)
BEGIN
    DELETE FROM CLIENTES WHERE id = p_id;
END$$

CREATE PROCEDURE ReducirStock(IN p_id_producto INT, IN p_cantidad INT)
BEGIN
    DECLARE stock_actual INT;
    SELECT stock INTO stock_actual
    FROM productos
    WHERE id = p_id_producto;

    IF stock_actual >= p_cantidad THEN
        UPDATE productos
        SET stock = stock - p_cantidad
        WHERE id = p_id_producto;
    END IF;
END $$

DROP PROCEDURE GenerarVentasSimuladas$$
CREATE PROCEDURE GenerarVentasSimuladas(IN N INT)
BEGIN
    DECLARE i INT DEFAULT 0;
	DECLARE ID_Venta INT;
    DECLARE ID_Empleado INT;
    DECLARE ID_Cliente INT;
	DECLARE ID_Producto INT;
	DECLARE Cant_Producto INT;
    DECLARE Cant_Detalles INT;
    DECLARE Pre_Producto DECIMAL(10, 2);
    DECLARE Iva_Producto DECIMAL(10, 2);
	DECLARE Des_Producto DECIMAL(10, 2);
    DECLARE Des_Temp TINYINT;
    DECLARE Sub_Venta DECIMAL(10, 2);
    DECLARE Iva_Venta DECIMAL(10, 2);
    DECLARE Des_Venta DECIMAL(10,2);
    DECLARE Tot_Venta DECIMAL(10, 2);
    DECLARE fecha_Venta DATETIME;

    WHILE i < N DO
        -- Seleccionar aleatoriamente un empleado y un cliente
        SET ID_Empleado = (SELECT id FROM EMPLEADOS ORDER BY RAND() LIMIT 1);
        SET ID_Cliente = (SELECT id FROM CLIENTES ORDER BY RAND() LIMIT 1);
        
        -- Crear la venta sin detalles aún
        INSERT INTO VENTAS (subTotal, iva, descuento, total, idEmpleado, idCliente)
        VALUES (0, 0, 0, 0, ID_Empleado, ID_Cliente);
        -- Obtener el id de la venta recién insertada
        SET ID_Venta = LAST_INSERT_ID();
        
        SET Sub_Venta = 0;
        SET Iva_Venta = 0;
        SET Des_Venta = 0;
        SET Tot_Venta = 0;
        
        -- Generar entre 1 y 5 productos aleatorios para la venta
        SET Cant_Detalles = FLOOR(1 + (RAND() * 10));
        WHILE Cant_Detalles > 0 DO
            SET ID_Producto = (SELECT id FROM PRODUCTOS ORDER BY RAND() LIMIT 1); -- Producto aleatorio
            SET Pre_Producto = (SELECT precio FROM PRODUCTOS WHERE id = ID_Producto);
			SET Cant_Producto = FLOOR(1 + (RAND() * 10));
            SET Iva_Producto = Pre_Producto/1.16;
            SET Sub_Venta = Sub_Venta + (Iva_Producto*Cant_Producto);
            SET Iva_Venta = Iva_Venta + ((Pre_Producto-Iva_Producto)*Cant_Producto);
            SET Des_Temp = (SELECT descuento FROM PRODUCTOS WHERE id = ID_Producto);
            SET Des_Producto = Pre_Producto*(Des_Temp/100);
            SET Des_Venta = Des_Venta + (Des_Producto*Cant_Producto);
            
            -- Insertar el detalle de la venta en la tabla DETALLES_VENTA
            INSERT INTO DETALLES_VENTA (idVenta, idProducto, cantidad, precio, descuento)
            VALUES (ID_Venta, ID_Producto, Cant_Producto, Pre_Producto, Des_Producto);
            
            SET Cant_Detalles = Cant_Detalles - 1;
        END WHILE;
        
        SET Tot_Venta = Tot_Venta + Sub_Venta + Iva_Venta - Des_Venta;
        SET Fecha_Venta = DATE_ADD('2010-01-01', INTERVAL (RAND() * (UNIX_TIMESTAMP(CURDATE()) - UNIX_TIMESTAMP('2010-01-01'))) SECOND);

        
        UPDATE VENTAS
        SET fecha = fecha_Venta, subTotal = Sub_Venta, iva = Iva_Venta, descuento = Des_Venta, total = Tot_Venta
        WHERE id = ID_Venta;
        
        SET i = i + 1;
    END WHILE;
END$$

create trigger crearUsuario
after insert on EMPLEADOS
for each row
begin
    declare nuevoUsuario VARCHAR(10);
    set nuevoUsuario = concat('YUR', left(new.puesto, 1), right(year(curdate()), 2), lpad(new.id, 4, '0'));

    insert into USUARIOS (usuario, contrasena, idEmpleado)
    VALUES (nuevoUsuario, sha2('changepass', 256), new.id);
end $$

CREATE TRIGGER ValidarProductosTrigger
BEFORE INSERT ON PRODUCTOS
FOR EACH ROW
BEGIN
    -- Validar precios no negativos
    IF NEW.precio < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El precio no puede ser negativo.';
    END IF;

    -- Validar descripción no vacía
    IF TRIM(NEW.descripcion) = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La descripción no puede estar vacía.';
    END IF;

    -- Validar longitud del código de barras si no es NULL
    IF NEW.codigoBarras IS NOT NULL AND (CHAR_LENGTH(NEW.codigoBarras) < 8 OR CHAR_LENGTH(NEW.codigoBarras) > 20) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El código de barras debe tener entre 8 y 20 caracteres.';
    END IF;
END$$

CREATE TRIGGER AuditoriaVentasInsert
AFTER INSERT ON VENTAS
FOR EACH ROW
BEGIN
    INSERT INTO AuditoriaVentas (accion, idVenta, usuario)
    VALUES ('INSERT', NEW.id, USER());
END$$

CREATE TRIGGER AuditoriaVentasUpdate
AFTER UPDATE ON VENTAS
FOR EACH ROW
BEGIN
    INSERT INTO AuditoriaVentas (accion, idVenta, usuario)
    VALUES ('UPDATE', NEW.id, USER());
END$$

CREATE TRIGGER AuditoriaVentasDelete
AFTER DELETE ON VENTAS
FOR EACH ROW
BEGIN
    INSERT INTO AuditoriaVentas (accion, idVenta, usuario)
    VALUES ('DELETE', OLD.id, USER());
END$$

DELIMITER ;

INSERT INTO EMPLEADOS (rfc, nombre, edad, sexo, direccion, telefono, correo, fechaNacimiento, puesto) 
VALUES
('CACD9804156C7', 'Carlos Pérez Rodríguez', 32, 'M', 'Calle 1, Ciudad, Gto.', '4442123456', 'carlos@gmail.com', '1992-05-12', 'Admin'),
('XAXX010101000', 'María González López', 28, 'F', 'Calle 2, Guanajuato, Gto.', '4451234567', 'maria@gmail.com', '1996-07-24', 'Cajero'),
('CACD9804156C7', 'Luis Hernández Ruiz', 35, 'M', 'Calle 3, León, Gto.', '4443345678', 'luis@gmail.com', '1989-02-09', 'Admin'),
('XAXX010101000', 'José López Martínez', 40, 'M', 'Calle 4, Celaya, Gto.', '4459876543', 'jose@gmail.com', '1984-11-15', 'Cajero'),
('CACD9804156C7', 'Ana Sánchez Pérez', 29, 'F', 'Calle 5, Irapuato, Gto.', '4447685987', 'ana@gmail.com', '1995-04-30', 'Admin'),
('XAXX010101000', 'Ricardo Jiménez Cruz', 23, 'M', 'Calle 6, Salamanca, Gto.', '4451112233', 'ricardo@gmail.com', '2001-09-05', 'Cajero'),
('CACD9804156C7', 'Patricia Vargas Morales', 31, 'F', 'Calle 7, Yuriria, Gto.', '4444539876', 'patricia@gmail.com', '1993-01-25', 'Admin'),
('XAXX010101000', 'Mario Ramírez Soto', 26, 'M', 'Calle 8, Pénjamo, Gto.', '4456778899', 'mario@gmail.com', '1998-12-14', 'Cajero'),
('CACD9804156C7', 'Silvia Gómez Rodríguez', 33, 'F', 'Calle 9, San Francisco, Gto.', '4449876543', 'silvia@gmail.com', '1991-08-17', 'Admin'),
('XAXX010101000', 'Eduardo Pérez Hernández', 38, 'M', 'Calle 10, León, Gto.', '4457764554', 'eduardo@gmail.com', '1986-03-25', 'Cajero');

INSERT INTO CATEGORIAS (nombre)
VALUES
('Abarrotes'),
('Electrónica'),
('Hogar'),
('Juguetes'),
('Deportes'),
('Ropa'),
('Calzado'),
('Belleza'),
('Salud'),
('Mascotas'),
('Automotriz'),
('Jardinería'),
('Música'),
('Papelería'),
('Libros'),
('Videojuegos'),
('Ferretería'),
('Bebidas sin alcohol'),
('Bebidas energéticas'),
('Productos orgánicos');

INSERT INTO PRODUCTOS (codigoBarras, clave, descripcion, marca, precio, descuento, stock, idCategoria) 
VALUES
('9876543210012', 'ABR-001', 'Arroz Integral 1kg', 'La Costeña', 20.00, 5, 150, 1),
('9876543210013', 'ELE-001', 'Televisor LED 40"', 'Samsung', 5000.00, 10, 30, 2),
('9876543210014', 'HOG-001', 'Sofá 3 plazas', 'Ikea', 2000.00, 0, 50, 3),
('9876543210015', 'JUQ-001', 'Muñeca Barbie', 'Mattel', 300.00, 0, 200, 4),
('9876543210016', 'DEP-001', 'Pelota de fútbol', 'Nike', 250.00, 0, 180, 5),
('9876543210017', 'ROP-001', 'Camisa de algodón', 'Adidas', 350.00, 0, 120, 6),
('9876543210018', 'CAL-001', 'Zapatos deportivos', 'Reebok', 800.00, 5, 90, 7),
('9876543210019', 'BEL-001', 'Crema hidratante', 'Nivea', 150.00, 10, 150, 8),
('9876543210020', 'SAL-001', 'Ibuprofeno 200mg', 'Bayer', 50.00, 0, 200, 9),
('9876543210021', 'MAS-001', 'Croquetas para perro', 'Pedigree', 200.00, 5, 100, 10),
('9876543210022', 'AUT-001', 'Aceite motor 5W-30', 'Castrol', 500.00, 0, 75, 11),
('9876543210023', 'JAR-001', 'Maceta de cerámica', 'Vivero XYZ', 100.00, 0, 60, 12),
('9876543210024', 'MUS-001', 'Guitarra acústica', 'Fender', 3500.00, 10, 50, 13),
('9876543210025', 'PAP-001', 'Cuaderno 100 hojas', 'Bic', 25.00, 0, 200, 14),
('9876543210026', 'LIB-001', 'Libro "Cien años de soledad"', 'Editorial XYZ', 400.00, 0, 150, 15),
('9876543210027', 'VID-001', 'Juego FIFA 24', 'EA Sports', 1200.00, 15, 80, 16),
('9876543210028', 'FER-001', 'Martillo de acero', 'Husky', 150.00, 0, 100, 17),
('9876543210029', 'BEA-003', 'Jugo de naranja 1L', 'Del Valle', 25.00, 0, 200, 18),
('9876543210030', 'BEA-004', 'Red Bull 250ml', 'Red Bull', 35.00, 0, 180, 19),
('9876543210031', 'ORG-001', 'Manzana orgánica 1kg', 'Agrícola Verde', 50.00, 0, 150, 20),
('9876543210032', 'ABR-002', 'Frijoles Negros 500g', 'La Costeña', 15.00, 5, 200, 1),
('9876543210033', 'ELE-002', 'Auriculares inalámbricos', 'Sony', 1500.00, 10, 75, 2),
('9876543210034', 'HOG-002', 'Lámpara de mesa', 'Philips', 350.00, 0, 50, 3),
('9876543210035', 'JUQ-002', 'Lego Star Wars', 'LEGO', 500.00, 0, 120, 4),
('9876543210036', 'DEP-002', 'Raqueta de tenis', 'Wilson', 800.00, 0, 90, 5),
('9876543210037', 'ROP-002', 'Pantalón de mezclilla', 'Levi’s', 600.00, 0, 200, 6),
('9876543210038', 'CAL-002', 'Botines de fútbol', 'Nike', 1200.00, 5, 50, 7),
('9876543210039', 'BEL-002', 'Shampoo anticaspa', 'Head & Shoulders', 80.00, 0, 150, 8),
('9876543210040', 'SAL-002', 'Paracetamol 500mg', 'Genérico', 30.00, 0, 200, 9),
('9876543210041', 'MAS-002', 'Collar para perro', 'Petco', 150.00, 0, 180, 10),
('9876543210042', 'AUT-002', 'Frenos para automóvil', 'Brembo', 700.00, 0, 60, 11),
('9876543210043', 'JAR-002', 'Silla de jardín', 'Leroy Merlin', 350.00, 0, 80, 12),
('9876543210044', 'MUS-002', 'Bajo eléctrico', 'Gibson', 5500.00, 10, 40, 13),
('9876543210045', 'PAP-002', 'Lápices de colores', 'Crayola', 50.00, 0, 250, 14),
('9876543210046', 'LIB-002', 'Libro "El Quijote"', 'Editorial ABC', 450.00, 0, 100, 15),
('9876543210047', 'VID-002', 'Juego Call of Duty', 'Activision', 1300.00, 10, 120, 16),
('9876543210048', 'FER-002', 'Pico de acero', 'Válido', 250.00, 0, 100, 17),
('9876543210049', 'BEA-005', 'Agua mineral 500ml', 'Ciel', 10.00, 0, 200, 18),
('9876543210050', 'BEA-006', 'Monster Energy 500ml', 'Monster', 40.00, 0, 150, 19),
('9876543210051', 'ORG-002', 'Tomate orgánico 1kg', 'Agrícola Verde', 40.00, 0, 180, 20),
('9876543210052', 'ABR-003', 'Tortillas de maíz 1kg', 'Bodega Aurrera', 18.00, 5, 250, 1),
('9876543210053', 'ELE-003', 'Móvil Samsung Galaxy S21', 'Samsung', 12000.00, 10, 50, 2),
('9876543210054', 'HOG-003', 'Silla de oficina ergonómica', 'Staples', 1500.00, 0, 60, 3),
('9876543210055', 'JUQ-003', 'Muñeco de acción', 'Hasbro', 200.00, 0, 150, 4),
('9876543210056', 'DEP-003', 'Bicicleta de montaña', 'Trek', 8000.00, 0, 30, 5),
('9876543210057', 'ROP-003', 'Chaqueta de cuero', 'Bershka', 1000.00, 0, 80, 6),
('9876543210058', 'CAL-003', 'Sandalias para verano', 'Crocs', 600.00, 0, 90, 7),
('9876543210059', 'BEL-003', 'Perfume para mujer', 'Chanel', 2000.00, 10, 50, 8),
('9876543210060', 'SAL-003', 'Amoxicilina 500mg', 'Farmacias similares', 50.00, 0, 250, 9),
('9876543210061', 'MAS-003', 'Juguete para gato', 'PetSmart', 120.00, 0, 180, 10),
('9876543210062', 'AUT-003', 'Batería para coche', 'Varta', 1500.00, 0, 40, 11),
('9876543210063', 'JAR-003', 'Set de jardinería', 'Vivero XYZ', 250.00, 0, 100, 12),
('9876543210064', 'MUS-003', 'Piano eléctrico', 'Yamaha', 4500.00, 5, 30, 13),
('9876543210065', 'PAP-003', 'Marcadores permanentes', 'Sharpie', 60.00, 0, 200, 14),
('9876543210066', 'LIB-003', 'Libro "1984"', 'Editorial DEF', 400.00, 0, 80, 15),
('9876543210067', 'VID-003', 'Juego The Witcher 3', 'CD Projekt', 1500.00, 5, 150, 16),
('9876543210068', 'FER-003', 'Sierra eléctrica', 'Bosch', 3000.00, 0, 50, 17),
('9876543210069', 'BEA-007', 'Jugo de uva 1L', 'Del Valle', 25.00, 0, 180, 18),
('9876543210070', 'BEA-008', 'Bebida energética Red Bull', 'Red Bull', 45.00, 0, 200, 19),
('9876543210071', 'ORG-003', 'Lechuga orgánica 500g', 'Agrícola Verde', 30.00, 0, 150, 20);

INSERT INTO CLIENTES (nombre, rfc, direccion, telefono, correo) 
VALUES
("MOSTRADOR", "XAXX010101000", "direccion", "telefono", "correo"),
('Juan Pérez Sánchez', 'JUPS901201MAS', 'Calle Principal 10, León, Gto.', '4441234567', 'juan.perez@gmail.com'),
('Ana María López González', 'AMLG850701MZ1', 'Calle 4, Irapuato, Gto.', '4458765432', 'ana.lopez@gmail.com'),
('Carlos Alberto Martínez Ramírez', 'CAMR760215HDF', 'Av. Reforma 33, Guanajuato, Gto.', '4442345678', 'carlos.martinez@gmail.com'),
('Luis Fernando Hernández Soto', 'LFHS860630JHT', 'Calle Morelos 45, Celaya, Gto.', '4451122334', 'luis.hernandez@gmail.com'),
('Sandra Cristina García Ramos', 'SCGR891220FJ6', 'Calle Hidalgo 56, Salamanca, Gto.', '4448765432', 'sandra.garcia@gmail.com'),
('Miguel Ángel López García', 'MALG750620HAS', 'Calle 5, Yuriria, Gto.', '4452233445', 'miguel.lopez@gmail.com'),
('Ricardo José González Pérez', 'RJGP800430HZS', 'Calle Juárez 60, Moroleón, Gto.', '4443456789', 'ricardo.gonzalez@gmail.com'),
('Patricia Isabel Ramos Rodríguez', 'PIRR901201MEC', 'Calle Fco. Villa 32, León, Gto.', '4453344556', 'patricia.ramos@gmail.com'),
('Fernando Alejandro López Aguilar', 'FALA801119HDF', 'Calle Guerrero 10, Guanajuato, Gto.', '4446677889', 'fernando.lopez@gmail.com'),
('Elena Martínez Muñoz', 'EMMU850810VNL', 'Calle Zaragoza 78, Irapuato, Gto.', '4455566778', 'elena.martinez@gmail.com'),
('Juan Antonio Reyes Gómez', 'JARG790805A51', 'Calle 8, Salamanca, Gto.', '4441122334', 'juan.reyes@gmail.com'),
('Rosa María González Romero', 'RMGR920504MZA', 'Calle Independencia 21, León, Gto.', '4456655443', 'rosa.gonzalez@gmail.com'),
('Oscar Javier Ramírez Cruz', 'OJRC750313MTS', 'Calle 4, Celaya, Gto.', '4442233445', 'oscar.ramirez@gmail.com'),
('Juliana Verónica Rodríguez López', 'JVRL880125HLP', 'Calle Benito Juárez 90, Yuriria, Gto.', '4455443322', 'juliana.rodriguez@gmail.com'),
('Andrés Francisco Martínez Sánchez', 'AFMS820202JHF', 'Calle Pino Suárez 13, Moroleón, Gto.', '4449988776', 'andres.martinez@gmail.com'),
('Verónica Patricia Flores García', 'VPFG890705HDF', 'Calle Constitución 5, Salamanca, Gto.', '4452233445', 'veronica.flores@gmail.com'),
('Raúl Ernesto Soto Hernández', 'RESH900805HBC', 'Calle Madero 4, Irapuato, Gto.', '4447766554', 'raul.soto@gmail.com'),
('Margarita Guadalupe Pérez Silva', 'MGPS850319M1X', 'Av. Juárez 7, Guanajuato, Gto.', '4453356677', 'margarita.perez@gmail.com'),
('José Manuel González Rodríguez', 'JMGR800412HSL', 'Calle 9, León, Gto.', '4442233556', 'jose.gonzalez@gmail.com'),
('Gabriela Mariana Rodríguez López', 'GMRL780906MA9', 'Calle 3, Moroleón, Gto.', '4454433221', 'gabriela.rodriguez@gmail.com');