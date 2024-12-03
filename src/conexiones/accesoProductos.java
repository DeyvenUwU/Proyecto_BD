package conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objetos.Producto;

public class accesoProductos {
    
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet resultados;
    
    public accesoProductos () {
        
    }
    
    public ArrayList<Producto> getTodosLosProductos() {
        String consulta = "SELECT * FROM PRODUCTOS";
        
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Producto p = new Producto();
                p.setId(resultados.getInt("id"));
                p.setCodigoBarras(resultados.getString("codigoBarras"));
                p.setClave(resultados.getString("clave"));
                p.setDescripcion(resultados.getString("descripcion"));
                p.setMarca(resultados.getString("marca"));
                p.setPrecio(resultados.getDouble("precio"));
                p.setDescuento(resultados.getInt("descuento"));
                p.setStock(resultados.getInt("stock"));
                p.setIdCategoria(resultados.getInt("idCategoria"));
                p.setFoto(resultados.getBinaryStream("foto"));
                p.setUltimaModificacion(resultados.getString("ultimaModificacion"));

                productos.add(p);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getTodosLosProductos'");
        }
        
        return productos;
    }
    
    public Producto getProducto(String codigo) {
        String consulta = "select * from PRODUCTOS where codigoBarras = ?";
        
        Producto p = null;
        
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, codigo);
            resultados = ps.executeQuery();
            
            while (resultados.next()) {
                p = new Producto();
                p.setId(resultados.getInt("id"));
                p.setCodigoBarras(resultados.getString("codigoBarras"));
                p.setClave(resultados.getString("clave"));
                p.setDescripcion(resultados.getString("descripcion"));
                p.setMarca(resultados.getString("marca"));
                p.setPrecio(resultados.getDouble("precio"));
                p.setDescuento(resultados.getInt("descuento"));
                p.setStock(resultados.getInt("stock"));
                p.setIdCategoria(resultados.getInt("idCategoria"));
                p.setFoto(resultados.getBinaryStream("foto"));
                p.setUltimaModificacion(resultados.getString("ultimaModificacion"));
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoEmpleados: Fallo consulta en 'getEmpleado'");
        }
        
        return p;
    }
    
    public String getDescripcionProducto(int id) {
        String consulta = "SELECT descripcion FROM PRODUCTOS WHERE id = ?";
        
        String nombre = null;
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setInt(1, id);
            resultados = ps.executeQuery();
            while (resultados.next())
                nombre = resultados.getString("descripcion");
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getNombreProducto'");
        }
        
        return nombre;
    }
    
    public boolean guardarProducto(Producto producto) {
        String insert = "INSERT INTO PRODUCTOS "
                + "(codigoBarras, clave, descripcion, marca, precio, descuento, stock, idCategoria, foto) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String update = "UPDATE PRODUCTOS SET codigoBarras = ?, clave = ?, descripcion = ?, marca = ?, "
                + "precio = ?, descuento = ?, stock = ?, idCategoria = ?, foto = ? WHERE id = ?";
        String updateSinFoto = "UPDATE PRODUCTOS SET codigoBarras = ?, clave = ?, descripcion = ?, marca = ?, "
                + "precio = ?, descuento = ?, stock = ?, idCategoria = ? WHERE id = ?";
        boolean ok = false;
        
        try {
            conexion = new ConexionDB().getConexion();
            conexion.setAutoCommit(false);
            
            if (producto.getId() == 0) 
                ps = conexion.prepareStatement(insert);
            else {
                if (producto.getFoto() == null) {
                    ps = conexion.prepareStatement(updateSinFoto);
                    ps.setInt(9, producto.getId());
                }
                else {
                    ps = conexion.prepareStatement(update);
                    ps.setBlob(9, producto.getFoto());
                    ps.setInt(10, producto.getId());
                }
            }
            
            ArrayList<String> datos = producto.getArrayLisAtributos();
            for (int i=1; i<=datos.size(); i++)
                ps.setString(i, datos.get(i-1));

            if (ps.executeUpdate() > 0)
                ok = true;
            
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'guardarProducto'");
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
    
    public boolean eliminarProducto(int id) {
        String consulta = "DELETE FROM PRODUCTOS WHERE id = ?";
        boolean ok = false;
        
        try {
            conexion = new ConexionDB().getConexion();
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(consulta);
            ps.setInt(1, id);
                
            if (ps.executeUpdate() > 0)
                ok = true;
            
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'eliminarProducto'");
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
