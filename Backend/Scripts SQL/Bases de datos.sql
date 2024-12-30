CREATE DATABASE `ms-administrador`;
CREATE DATABASE `ms-administrativo`;
CREATE DATABASE `ms-calendarioacademico`;
CREATE DATABASE `ms-carrera`;
CREATE DATABASE `ms-curso`;
CREATE DATABASE `ms-docente`;
CREATE DATABASE `ms-estudiante`;
CREATE DATABASE `ms-evaluacionacademica`;
CREATE DATABASE `ms-materialeseducativos`;
CREATE DATABASE `ms-planificacionacademica`;
CREATE DATABASE `ms-auth`;
CREATE DATABASE `ms-persona`;
CREATE DATABASE `ms-roles`;
CREATE DATABASE `ms-usuarios`;
CREATE DATABASE `ms-inscripciones`;
CREATE DATABASE `ms-postulante`;
CREATE DATABASE `ms-realTimeChat`;
CREATE DATABASE `ms-matriculas`;
CREATE DATABASE `ms-pagos`;
CREATE DATABASE `ms-requisitosAcademicos`;
CREATE DATABASE `ms-nivelesDeEnsenanza`;

SHOW VARIABLES LIKE 'max_connections';
SET GLOBAL max_connections = 200; -- Aumenta según sea necesario