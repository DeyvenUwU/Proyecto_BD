package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/rangel_yuriria";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private Connection conexion;
    
    public ConexionDB () {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException ex) {
            System.out.println("¡ERROR! No se pudo conectar con la base de datos");
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}
