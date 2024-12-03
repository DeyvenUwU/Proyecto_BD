package conexiones;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objetos.Cliente;

public class accesoClientes {
    
    private Connection conexion;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet resultados;
    
    public accesoClientes () {
        
    }
    
    public ArrayList<Cliente> getTodosLosClientes() {
        String consulta = "SELECT * FROM CLIENTES";
        
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Cliente c = new Cliente();
                c.setId(resultados.getInt("id"));
                c.setNombre(resultados.getString("nombre"));
                c.setRfc(resultados.getString("rfc"));
                c.setDireccion(resultados.getString("direccion"));
                c.setTelefono(resultados.getString("telefono"));
                c.setCorreo(resultados.getString("correo"));
                c.setUltimaModificacion(resultados.getString("ultimaModificacion"));

                clientes.add(c);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoClientes: Fallo consulta en 'getTodosLosClientes'");
        }
        
        return clientes;
    }
    
    public int getIdCliente (String nombre) {
        String consulta = "SELECT id FROM CLIENTES WHERE nombre = ?";
        
        int id = 0;
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, nombre);
            resultados = ps.executeQuery();
            while (resultados.next())
                id = resultados.getInt("id");
        }catch (SQLException ex) {
            System.out.println("ERROR accesoClientes: Fallo consulta en 'getIdCliente'");
        }
        
        return id;
    }
    
    public boolean guardarCliente(Cliente cliente) {
        String insert = "{call InsertarCliente(?, ?, ?, ?, ?)}";
        String update = "{call ActualizarCliente(?, ?, ?, ?, ?, ?)}";
        boolean ok = false;
        int pos = 1;
        
        conexion = new ConexionDB().getConexion();
        try {
            if (cliente.getId() == 0)
                cs = (CallableStatement) conexion.prepareCall(insert);
            else {
                cs = (CallableStatement) conexion.prepareCall(update);
                cs.setInt(pos, cliente.getId());
                pos++;
            }
            
            ArrayList<String> datos = cliente.getArrayLisAtributos();
            for (int i=pos; i<datos.size()+pos; i++)
                cs.setString(i, datos.get(i-pos));
            
            if (cs.executeUpdate() > 0)
                ok = true;
        } catch (SQLException ex) {
            System.out.println("ERROR accesoClientes: Fallo consulta en 'guardarCliente'");
        }
                
        return ok;
    }
    
    public boolean eliminarCliente(int id) {
        String call = "{call EliminarCliente(?)}";
        boolean ok = false;
        
        conexion = new ConexionDB().getConexion();
        try {
            cs = (CallableStatement) conexion.prepareCall(call);
            cs.setInt(1, id);
            
            if (cs.executeUpdate() > 0)
                ok = true;
        } catch (SQLException ex) {
            System.out.println("ERROR accesoClientes: Fallo consulta en 'guardarCliente'");
        }
                
        return ok;    
    }
}
