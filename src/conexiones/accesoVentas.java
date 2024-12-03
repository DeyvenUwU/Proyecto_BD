package conexiones;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objetos.DetalleVenta;
import objetos.Venta;

public class accesoVentas {
 
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet resultados;
    
    public accesoVentas () {
        
    }
    
    public Venta getVenta (int idVenta) {
        String consulta = "SELECT * FROM VENTAS WHERE id = ?";
        Venta v = null;
        
        try {
            conexion = new ConexionDB().getConexion();
            ps = conexion.prepareStatement(consulta);
            
            ps.setInt(1, idVenta);
            resultados = ps.executeQuery();
            
            while (resultados.next()) {
                v = new Venta();
                v.setId(resultados.getInt("id"));
                v.setId(resultados.getInt("id"));
                v.setFecha(resultados.getString("fecha"));
                v.setSubTotal(resultados.getDouble("subTotal"));
                v.setIva(resultados.getDouble("iva"));
                v.setDescuento(resultados.getDouble("descuento"));
                v.setTotal(resultados.getDouble("total"));
                v.setIdEmpleado(resultados.getInt("idEmpleado"));
                v.setIdCliente(resultados.getInt("idCliente"));
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoEmpleados: Fallo consulta en 'getEmpleado'");
        }
        
        return v;
    }
    
     public ArrayList<DetalleVenta> getTodosLosDetalles(int idVenta) {
        String consulta = "SELECT * FROM DETALLES_VENTA WHERE idVenta = ?";
        
        ArrayList<DetalleVenta> detalles = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idVenta);
            
            resultados = ps.executeQuery();
            while (resultados.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setId(resultados.getInt("id"));
                dv.setIdVenta(resultados.getInt("idVenta"));
                dv.setIdProducto(resultados.getInt("idProducto"));
                dv.setCantidad(resultados.getInt("cantidad"));
                dv.setDescuento(resultados.getDouble("descuento"));
                dv.setPrecio(resultados.getDouble("precio"));
                
                detalles.add(dv);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getTodosLosProductos'");
        }
        
        return detalles;
    }
    
    public ArrayList<Venta> getTodasLasVentas() {
        String consulta = "SELECT * FROM VENTAS";
        
        ArrayList<Venta> ventas = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Venta v = new Venta();
                v.setId(resultados.getInt("id"));
                v.setFecha(resultados.getString("fecha"));
                v.setSubTotal(resultados.getDouble("subTotal"));
                v.setIva(resultados.getDouble("iva"));
                v.setDescuento(resultados.getDouble("descuento"));
                v.setTotal(resultados.getDouble("total"));
                v.setIdEmpleado(resultados.getInt("idEmpleado"));
                v.setIdCliente(resultados.getInt("idCliente"));

                ventas.add(v);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getTodosLosProductos'");
        }
        
        return ventas;
    }
    
    public int agregarVenta(Venta venta, ArrayList<DetalleVenta> detalles) {
        String insertVenta = "INSERT INTO VENTAS "
                + "(subTotal, iva, descuento, total, idEmpleado, idCliente) "
                + "values (?, ?, ?, ?, ?, ?)";
        String insertDetalle = "INSERT INTO DETALLES_VENTA "
                + "(idVenta, idProducto, cantidad, precio, descuento) "
                + "values (?, ?, ?, ?, ?)";
        String updateProducto = "{call ReducirStock(?, ?)}";
        
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        CallableStatement csProducto = null;
        int idVenta = 0;
        
        try {
            conexion = new ConexionDB().getConexion();
            conexion.setAutoCommit(false);
            
            //CREAR VENTA
            psVenta = conexion.prepareStatement(insertVenta, 1);
            ArrayList<String> datosVenta = venta.getArrayLisAtributos();
            for (int i=1; i<=datosVenta.size(); i++)
                psVenta.setString(i, datosVenta.get(i-1));
            
            if (psVenta.executeUpdate() > 0) {
                ResultSet resultados = psVenta.getGeneratedKeys();
                if (resultados.next())
                    idVenta = resultados.getInt(1);
            }
            
            //INSERTAR TODOS LOS DETALLES DE LA VENTA y REDUCIR EL STOCK
            psDetalle = conexion.prepareStatement(insertDetalle);
            csProducto = (CallableStatement) conexion.prepareCall(updateProducto);
            for (DetalleVenta dv : detalles) {
                
                dv.setIdVenta(idVenta);
                ArrayList<String> datosDetalle = dv.getArrayLisAtributos();
                for (int i=1; i<=datosDetalle.size(); i++)
                    psDetalle.setString(i, datosDetalle.get(i-1));
                psDetalle.executeUpdate();

                csProducto.setInt(1, dv.getIdProducto());
                csProducto.setInt(2, dv.getCantidad());
                csProducto.executeUpdate();
            }
            
            conexion.commit();
        } catch (SQLException e) {
            idVenta = 0;
            System.out.println("ERROR accesoVentas: Fallo consulta en 'agregarVenta'");
            try {
                if (conexion != null)
                    conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("ERROR en el rollback: " + rollbackEx);}
        } finally {
            try {
                conexion.setAutoCommit(true);
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (csProducto != null) csProducto.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                System.out.println("ERROR al cerrar la conexión: " + ex);}
        }
        
        return idVenta;
    }
    
    public ArrayList<Object[]> getVentasPorEmpleado (){ 
        String consulta = "select e.nombre, sum(v.total) as 'Total', count(v.id) as 'Cantidad' from\n" +
                        "empleados e join ventas v on e.id = v.idEmpleado\n" +
                        "group by e.id";
        
        ArrayList<Object[]> datos = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement(consulta);
            
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Object[] Fila = new Object[3];
                Fila[0] = resultados.getString("nombre");
                Fila[1] = resultados.getString("Total");
                Fila[2] = resultados.getString("Cantidad");
                
                datos.add(Fila);
            }
        }catch (SQLException ex) {
            System.out.println("¡Error! La consulta esta mal");
        }
        
        return datos;
    }
    
    public ArrayList<Object[]> getVentasPorMes (int mes, int ano){ 
        String consulta = "select v.id, v.fecha, c.nombre as 'cliente', e.nombre as 'empleado', v.total, CantidadProductos(v.id) as 'CantDetalles'\n" +
"from empleados e\n" +
"join ventas v on e.id = v.idEmpleado\n" +
"join clientes c on v.idCliente = c.id\n" +
"where month(v.fecha) = ? and year(v.fecha) = ?";
        
        ArrayList<Object[]> datos = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement(consulta);
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Object[] Fila = new Object[6];
                Fila[0] = resultados.getString("id");
                Fila[1] = resultados.getString("fecha");
                Fila[2] = resultados.getString("cliente");
                Fila[3] = resultados.getString("empleado");
                Fila[4] = resultados.getString("total");
                Fila[5] = resultados.getString("CantDetalles");
                
                datos.add(Fila);
            }
        }catch (SQLException ex) {
            System.out.println("¡Error! La consulta esta mal");
        }
        
        return datos;
    }
    
    public ArrayList<Object[]> getVentasPorTimestre (){ 
        String consulta = "select \n" +
"	p.descripcion,\n" +
"    (select count(dv.idProducto)\n" +
"	from detalles_venta dv join ventas v on dv.idVenta = v.id\n" +
"	where dv.idProducto = p.id and month(v.fecha) between 1 and 3\n" +
"	group by dv.idProducto) as 'Trimestre 1',\n" +
"    (select count(dv.idProducto)\n" +
"	from detalles_venta dv join ventas v on dv.idVenta = v.id\n" +
"	where dv.idProducto = p.id and month(v.fecha) between 4 and 6\n" +
"	group by dv.idProducto) as 'Trimestre 2',\n" +
"    (select count(dv.idProducto)\n" +
"	from detalles_venta dv join ventas v on dv.idVenta = v.id\n" +
"	where dv.idProducto = p.id and month(v.fecha) between 7 and 9\n" +
"	group by dv.idProducto) as 'Trimestre 3',\n" +
"    (select count(dv.idProducto)\n" +
"	from detalles_venta dv join ventas v on dv.idVenta = v.id\n" +
"	where dv.idProducto = p.id and month(v.fecha) between 10 and 12\n" +
"	group by dv.idProducto) as 'Trimestre 4'\n" +
"from productos p \n" +
"join detalles_venta dv on p.id = dv.idProducto \n" +
"join ventas v on v.id = dv.idVenta\n" +
"group by p.id;";
        
        ArrayList<Object[]> datos = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement(consulta);
            
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Object[] Fila = new Object[5];
                Fila[0] = resultados.getString("descripcion");
                Fila[1] = resultados.getString("Trimestre 1");
                Fila[2] = resultados.getString("Trimestre 2");
                Fila[3] = resultados.getString("Trimestre 3");
                Fila[4] = resultados.getString("Trimestre 4");
                
                datos.add(Fila);
            }
        }catch (SQLException ex) {
            System.out.println("¡Error! La consulta esta mal");
        }
        
        return datos;
    }
    
    public ArrayList<Venta> getVentasPorFecha(String inicio, String fin) {
        String consulta = "select * from ventas where fecha between ? and ?";  
        
        ArrayList<Venta> ventas = new ArrayList<>();
        try {
            conexion = new ConexionDB().getConexion();
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, inicio);
            ps.setString(2, fin);
            
            resultados = ps.executeQuery();
            while (resultados.next()) {
                Venta v = new Venta();
                v.setId(resultados.getInt("id"));
                v.setFecha(resultados.getString("fecha"));
                v.setSubTotal(resultados.getDouble("subTotal"));
                v.setIva(resultados.getDouble("iva"));
                v.setDescuento(resultados.getDouble("descuento"));
                v.setTotal(resultados.getDouble("total"));
                v.setIdEmpleado(resultados.getInt("idEmpleado"));
                v.setIdCliente(resultados.getInt("idCliente"));

                ventas.add(v);
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoProductos: Fallo consulta en 'getTodosLosProductos'");
        }
        
        return ventas;
    }
}
