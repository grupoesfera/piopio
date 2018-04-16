-- Usuarios
insert into usuario(id, nombre) values (1, 'Marcelo');
insert into usuario(id, nombre) values (2, 'Brenda');
insert into usuario(id, nombre) values (3, 'India');
insert into usuario(id, nombre) values (4, 'Leon');
insert into usuario(id, nombre) values (5, 'Sebastian');
insert into usuario(id, nombre) values (6, 'Alejandro');
insert into usuario(id, nombre) values (7, 'Santiago');

insert into Usuario_Usuario values (1, 2);
insert into Usuario_Usuario values (1, 3);
insert into Usuario_Usuario values (1, 5);
insert into Usuario_Usuario values (2, 1);
insert into Usuario_Usuario values (2, 3);
insert into Usuario_Usuario values (3, 1);
insert into Usuario_Usuario values (3, 2);
insert into Usuario_Usuario values (3, 5);
insert into Usuario_Usuario values (5, 1);
insert into Usuario_Usuario values (6, 7);

-- Pios de Marcelo
insert into pio(id, mensaje, fechaCreacion, autor_id) values (1, 'Hola, este es mi primer pio', '2017-12-27', 1);
insert into pio(id, mensaje, fechaCreacion, autor_id) values (2, 'Hola, este es mi segundo pio', '2017-12-28', 1);
-- Pios de Brenda
insert into pio(id, mensaje, fechaCreacion, autor_id) values (3, 'Aguante India', '2018-01-01', 2);
-- Pios de India
insert into pio(id, mensaje, fechaCreacion, autor_id) values (4, 'Guau!', '2018-01-02', 3);
-- Pios de Leon
insert into pio(id, mensaje, fechaCreacion, autor_id) values (5, 'Miau', '2018-01-02', 4);

insert into favorito(id, pio_id, fan_id) values (1, 1, 2);
insert into favorito(id, pio_id, fan_id) values (2, 2, 2);
insert into favorito(id, pio_id, fan_id) values (3, 2, 3);
insert into favorito(id, pio_id, fan_id) values (4, 2, 5);
insert into favorito(id, pio_id, fan_id) values (5, 3, 1);
insert into favorito(id, pio_id, fan_id) values (6, 3, 3);
insert into favorito(id, pio_id, fan_id) values (7, 4, 2);

insert into comentario(id, mensaje, autor_id) values (1, 'Bien por vos', 2);
insert into comentario(id, mensaje, autor_id) values (2, 'Muy bien!', 4);
