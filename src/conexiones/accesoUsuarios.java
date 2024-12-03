package conexiones;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class accesoUsuarios {
    
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet resultados;
    
    public accesoUsuarios () {
        
    }
    
    public boolean ingresar (String usuario, String contrasena) {
        String consulta = "SELECT usuario, contrasena FROM USUARIOS WHERE usuario = ?";
        
        String user = null;
        String pass = null;
        
        try {
            conexion = new ConexionDB().getConexion();
            
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, usuario);
            resultados = ps.executeQuery();
            while (resultados.next()) {
                user = resultados.getString("usuario");
                pass = resultados.getString("contrasena");
            }
        }catch (SQLException ex) {
            System.out.println("ERROR accesoUsuarios: Fallo consulta en 'ingresar'");
        }
            
        if (sha2(contrasena).equals(pass))
            return true;
        else
            return false;
    }
    
    public String sha2 (String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashBytes = digest.digest(s.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("ERROR accesoUsuarios: Fallo al calcular el hash en metodo 'sha2'", e);
        }
    }
}
