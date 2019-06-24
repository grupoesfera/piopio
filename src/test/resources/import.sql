-- Usuarios
--insert into usuario(id, nombre) values (1, 'Marcelo');
--insert into usuario(id, nombre) values (2, 'Brenda');
--insert into usuario(id, nombre) values (3, 'India');
--insert into usuario(id, nombre) values (4, 'Leon');
--insert into usuario(id, nombre) values (5, 'Sebastian');
--insert into usuario(id, nombre) values (6, 'Alejandro');
--insert into usuario(id, nombre) values (7, 'Santiago');

--insert into seguidos(seguidor, seguido) values (1, 2);
--insert into seguidos(seguidor, seguido) values (1, 3);
--insert into seguidos(seguidor, seguido) values (1, 5);
--insert into seguidos(seguidor, seguido) values (2, 1);
--insert into seguidos(seguidor, seguido) values (2, 3);
--insert into seguidos(seguidor, seguido) values (3, 1);
--insert into seguidos(seguidor, seguido) values (3, 2);
--insert into seguidos(seguidor, seguido) values (3, 5);
--insert into seguidos(seguidor, seguido) values (5, 1);
--insert into seguidos(seguidor, seguido) values (6, 7);

-- Pios de Marcelo
--insert into pio(id, mensaje, fechaCreacion, autor_id) values (1, 'Hola, este es mi primer pio', '2017-12-27', 1);
--insert into pio(id, mensaje, fechaCreacion, autor_id) values (2, 'Hola, este es mi segundo pio', '2017-12-28', 1);
-- Pios de Brenda
--insert into pio(id, mensaje, fechaCreacion, autor_id) values (3, 'Aguante India', '2018-01-01', 2);
-- Pios de India
--insert into pio(id, mensaje, fechaCreacion, autor_id) values (4, 'Guau!', '2018-01-02', 3);
-- Pios de Leon
--insert into pio(id, mensaje, fechaCreacion, autor_id) values (5, 'Miau', '2018-01-02', 4);

--insert into favorito(id, pio_id, fan_id) values (1, 1, 2);
--insert into favorito(id, pio_id, fan_id) values (2, 2, 2);
--insert into favorito(id, pio_id, fan_id) values (3, 2, 3);
--insert into favorito(id, pio_id, fan_id) values (4, 2, 5);
--insert into favorito(id, pio_id, fan_id) values (5, 3, 1);
--insert into favorito(id, pio_id, fan_id) values (6, 3, 3);
--insert into favorito(id, pio_id, fan_id) values (7, 4, 2);

--insert into comentario(id, mensaje, autor_id) values (1, 'Bien por vos', 2);
--insert into comentario(id, mensaje, autor_id) values (2, 'Muy bien', 4);

DROP ALIAS IF EXISTS REVERSE;
CREATE ALIAS REVERSE AS $$ String reverse(String straight) { return new StringBuilder(straight).reverse().toString(); } $$;

DROP ALIAS IF EXISTS NOMBRES;
CREATE ALIAS NOMBRES AS $$ java.sql.ResultSet nombres(java.sql.Connection connection) throws java.sql.SQLException { return connection.createStatement().executeQuery("select nombre from usuario"); } $$;
