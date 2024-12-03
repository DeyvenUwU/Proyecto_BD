package objetos;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class Empleado {
    
    private int id;
    private String rfc;
    private String nombre;
    private int edad;
    private String sexo;
    private String direccion;
    private String telefono;
    private String correo;    
    private String fecha_nac;
    private String fecha_cont;
    private String puesto;
    private InputStream foto;
    private String ultimaModificacion;
    
    private BufferedImage fotoLeida;
    
    public Empleado(){
        
    }

    public Empleado(int id, String rfc, String nombre, int edad, String sexo, String direccion, String telefono, String correo, String fecha_nac, String fecha_cont, String puesto, InputStream foto, String ultimaModificacion) {
        this.id = id;
        this.rfc = rfc;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.fecha_cont = fecha_cont;
        this.puesto = puesto;
        this.foto = foto;
        this.ultimaModificacion = ultimaModificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getFecha_cont() {
        return fecha_cont;
    }

    public void setFecha_cont(String fecha_cont) {
        this.fecha_cont = fecha_cont;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public BufferedImage getFotoLeida() {
        return fotoLeida;
    }

    public void setFotoLeida(BufferedImage fotoLeida) {
        this.fotoLeida = fotoLeida;
    }
    
    public ArrayList<String> getArrayLisAtributos() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add(rfc);
        lista.add(nombre);
        lista.add("" + edad);
        lista.add(sexo);
        lista.add(direccion);
        lista.add(telefono);
        lista.add(correo);
        lista.add(fecha_nac);
        lista.add(puesto);
        
        return lista;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", rfc=" + rfc + ", nombre=" + nombre + ", edad=" + edad + ", sexo=" + sexo + ", direccion=" + direccion + ", telefono=" + telefono + ", correo=" + correo + ", fecha_nac=" + fecha_nac + ", fecha_cont=" + fecha_cont + ", puesto=" + puesto + ", foto=" + foto + ", ultimaModificacion=" + ultimaModificacion + '}';
    }
}