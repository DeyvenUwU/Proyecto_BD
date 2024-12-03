package conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import objetos.Empleado;
import java.sql.SQLException;
import java.util.ArrayList;

public class accesoEmpleados {
    
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet resultados;
    
    public accesoEmpleados () {
        
    }
    
    public Empleado getEmpleado (String usuario) {
        String consulta = "SELECT * FROM EMPLEADOS e JOIN USUARIOS u on e.id = u.idEmpleado WHERE u.usuario = ?";
        
        Empleado e = new Empleado ();
        
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, usuario);
            resultados = ps.executeQuery();
            
            while (resultados.next()) {
                e.setId(resultados.getInt("id"));
                e.setRfc(resultados.getString("rfc"));
                e.setNombre(resultados.getString("nombre"));
                e.setEdad(resultados.getInt("edad"));
                e.setSexo(resultados.getString("sexo"));
                e.setDireccion(resultados.getString("direccion"));
                e.setTelefono(resultados.getString("telefono"));
                e.setCorreo(resultados.getString("correo"));
                e.setFecha_nac(resultados.getString("fechaNacimiento"));
                e.setFecha_cont(resultados.getString("fechaContratacion"));
                e.setPuesto(resultados.getString("puesto"));
                e.setFoto(resultados.getBinaryStream("foto"));
                e.setUltimaModificacion(resultados.getString("ultimaModificacion"));
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoEmpleados: Fallo consulta en 'getEmpleado'");
        }
        
        return e;
    }
    
    public ArrayList<Empleado> getTodosLosEmpleados() {
        String consulta = "SELECT * FROM EMPLEADOS";
        
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Empleado e = new Empleado();
                e.setId(resultados.getInt("id"));
                e.setRfc(resultados.getString("rfc"));
                e.setNombre(resultados.getString("nombre"));
                e.setEdad(resultados.getInt("edad"));
                e.setSexo(resultados.getString("sexo"));
                e.setDireccion(resultados.getString("direccion"));
                e.setTelefono(resultados.getString("telefono"));
                e.setCorreo(resultados.getString("correo"));
                e.setFecha_nac(resultados.getString("fechaNacimiento"));
                e.setFecha_cont(resultados.getString("fechacontratacion"));
                e.setPuesto(resultados.getString("puesto"));
                e.setFoto(resultados.getBinaryStream("foto"));
                e.setUltimaModificacion(resultados.getString("ultimaModificacion"));

                empleados.add(e);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getTodosLosProductos'");
        }
        
        return empleados;
    }
    
    public boolean guardarEmpleado(Empleado empleado) {
        String insert = "INSERT INTO EMPLEADOS "
                + "(rfc, nombre, edad, sexo, direccion, telefono, correo, fechaNacimiento, puesto, foto) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String update = "UPDATE EMPLEADOS SET rfc = ?, nombre = ?, edad = ?, sexo = ?, direccion = ?,"
                + " telefono = ?, correo = ?, fechaNacimiento = ?, puesto = ?, foto = ? WHERE id = ?";
        String updateSinFoto = "UPDATE EMPLEADOS SET rfc = ?, nombre = ?, edad = ?, sexo = ?, direccion = ?,"
                + " telefono = ?, correo = ?, fechaNacimiento = ?, puesto = ? WHERE id = ?";
        boolean ok = false;
        
        try {
            conexion = new ConexionDB().getConexion();
            conexion.setAutoCommit(false);
            
            if (empleado.getId() == 0) 
                ps = conexion.prepareStatement(insert);
            else {
                if (empleado.getFoto() == null) {
                    ps = conexion.prepareStatement(updateSinFoto);
                    ps.setInt(10, empleado.getId());
                }
                else {
                    ps = conexion.prepareStatement(update);
                    ps.setBlob(10, empleado.getFoto());
                    ps.setInt(11, empleado.getId());
                }
            }
            
            ArrayList<String> datos = empleado.getArrayLisAtributos();
            for (int i=1; i<=datos.size(); i++)
                ps.setString(i, datos.get(i-1));
            
            if (ps.executeUpdate() > 0)
                ok = true;
            
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("ERROR accesoEmpleados: Fallo consulta en 'guardarEmpleado'");
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
    
    public boolean eliminarEmpleado(int id) {
        String consulta = "DELETE FROM EMPLEADOS WHERE id = ?";
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
            System.out.println("ERROR accesoEmpleados: Fallo consulta en 'eliminarEmpleado'");
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
