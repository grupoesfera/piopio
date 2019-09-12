DROP ALIAS IF EXISTS NOMBRES;
CREATE ALIAS NOMBRES AS $$ java.sql.ResultSet nombres(java.sql.Connection connection) throws java.sql.SQLException { return connection.createStatement().executeQuery("select nombre from usuario"); } $$;

DROP ALIAS IF EXISTS CONTAR_NUMERO_DE_FAVORITOS_DE_UN_PIO;
CREATE ALIAS CONTAR_NUMERO_DE_FAVORITOS_DE_UN_PIO AS $$ java.sql.ResultSet numeroDeFavortiosDeUnPio(java.sql.Connection connection, java.lang.Long idPio) throws java.sql.SQLException { return connection.createStatement().executeQuery("select count(*) from favorito f where f.pioId = " + idPio + " group by f.pioId"); } $$

 