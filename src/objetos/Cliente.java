package objetos;

import java.util.ArrayList;

public class Cliente {
    
    private int id;
    private String nombre;
    private String rfc;
    private String direccion;
    private String telefono;
    private String correo;
    private String ultimaModificacion;

    public Cliente() {
        
    }
    
    public Cliente(int id, String nombre, String rfc, String direccion, String telefono, String correo, String ultimaModificacion) {
        this.id = id;
        this.nombre = nombre;
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public ArrayList<String> getArrayLisAtributos() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add(nombre);
        lista.add(rfc);
        lista.add(direccion);
        lista.add(telefono);
        lista.add(correo);
        
        return lista;
    }
    
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", rfc=" + rfc + ", direccion=" + direccion + ", telefono=" + telefono + ", correo=" + correo + '}';
    }
}
