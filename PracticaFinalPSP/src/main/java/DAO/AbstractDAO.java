package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase abstracta de la que heredarán todos los DAO para obtener la conexión de forma heredada y ahorrar código
 */
abstract class AbstractDao {

    private static final String URL = "jdbc:mysql://localhost:3306/bd_finalpsp?allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";
    protected Connection conexion;
    protected ResultSet rs;
    protected PreparedStatement st;

    /**
     * Método que nos permite conectar a la base de datos, y motivo por el cual heredaremos de esta clase
     */
    public void conectar() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Exito");
        } catch (SQLException e) {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }
    }
}