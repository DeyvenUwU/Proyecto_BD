package conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objetos.Categoria;

public class accesoCategorias {
    
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet resultados;
    
    public accesoCategorias () {
        
    }
    
    public ArrayList<Categoria> getTodasLasCategorias() {
        String consulta = "SELECT * FROM CATEGORIAS";
        
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Categoria c = new Categoria();
                c.setId(resultados.getInt("id"));
                c.setNombre(resultados.getString("nombre"));
                
                categorias.add(c);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoCategorias: Fallo consulta en 'getTodasLasCategorias'");
        }
        
        return categorias;
    }
    
    public int getId(String nombre) {
        String consulta = "SELECT id FROM CATEGORIAS WHERE nombre = ?";
        
        int id = -1;
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, nombre);
            resultados = ps.executeQuery();
            while (resultados.next())
                id = resultados.getInt("id");
        }catch (SQLException ex) {
            System.out.println("ERROR accesoCategorias: Fallo consulta en 'getId'");
        }
        
        return id;
    }
    
    public String getNombre(int id) {
        String consulta = "SELECT nombre FROM CATEGORIAS WHERE id = ?";
        
        String nombre = null;
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setInt(1, id);
            resultados = ps.executeQuery();
            while (resultados.next())
                nombre = resultados.getString("nombre");
        }catch (SQLException ex) {
            System.out.println("ERROR accesoCategorias: Fallo consulta en 'getNombre'");
        }
        
        return nombre;
    }
    
    public boolean agregarCategoria (Categoria categoria) {
        String consulta = "INSERT INTO CATEGORIAS (nombre) values (?)";
        boolean ok = false;
        
        try {
            conexion = new ConexionDB().getConexion();
            conexion.setAutoCommit(false);
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, categoria.getNombre());
                
            if (ps.executeUpdate() > 0)
                ok = true;
            
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("ERROR accesoCategorias: Fallo consulta en 'agregarCategoria'");
            try {
                if (conexion != null)
                    conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("ERROR en el rollback: " + rollbackEx);}
        } finally {
            try {
                conexion.setAutoCommit(true);
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                System.out.println("ERROR al cerrar la conexión: " + ex);}
        }
        
        return ok;
    }
}
