INSERT INTO Zona (id_zona, nombre ) VALUES (1, 'Zona Norte' );
INSERT INTO Zona (id_zona, nombre ) VALUES (2, 'Zona Sur' );
INSERT INTO Zona (id_zona, nombre ) VALUES (3, 'Zona Este' );
INSERT INTO Zona (id_zona, nombre ) VALUES (4, 'Zona Oeste' );

/*Primer Dispositivo de Riego*/
INSERT INTO Dispositivo (id_dispositivo, nombre, id_zona, fecha_hora_creacion, fecha_hora_actualizacion, baja) VALUES (1, 'DispositivoDeRiego1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
INSERT INTO Dispositivo_de_riego (id_dispositivo, humedad, temperatura,activo) VALUES (1, 0.7, 25.0,false);

/*Segundo Dispositivo de Riego*/
INSERT INTO Dispositivo (id_dispositivo, nombre, id_zona, fecha_hora_creacion, fecha_hora_actualizacion, baja) VALUES (2, 'DispositivoDeRiego2', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
INSERT INTO Dispositivo_de_riego (id_dispositivo, humedad, temperatura,activo) VALUES (2, 25, 25.34,false);

/*Tercer Dispositivo de Riego*/
INSERT INTO Dispositivo (id_dispositivo, nombre, id_zona, fecha_hora_creacion, fecha_hora_actualizacion, baja) VALUES (3, 'DispositivoDeRiego3', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
INSERT INTO Dispositivo_de_riego (id_dispositivo, humedad, temperatura,activo) VALUES (3, 55, 40.0,false);

/*Cuarto Dispositivo de Riego*/
INSERT INTO Dispositivo (id_dispositivo, nombre, id_zona, fecha_hora_creacion, fecha_hora_actualizacion, baja) VALUES (4, 'DispositivoDeRiego4', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);
INSERT INTO Dispositivo_de_riego (id_dispositivo, humedad, temperatura,activo) VALUES (4, 45, 25.23,false);
