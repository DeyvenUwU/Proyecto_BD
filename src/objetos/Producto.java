package objetos;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class Producto {

    private int id;
    private String codigoBarras;
    private String clave;
    private String descripcion;
    private String marca;
    private double precio;
    private int descuento;
    private int stock;
    private int idCategoria;
    private InputStream foto;
    private String ultimaModificacion;
    
    private BufferedImage fotoLeida;

    public Producto () {
        
    }

    public Producto(int id, String codigoBarras, String clave, String descripcion, String marca, double precio, int descuento, int stock, int idCategoria, InputStream foto, String ultimaModificacion) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.clave = clave;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.descuento = descuento;
        this.stock = stock;
        this.idCategoria = idCategoria;
        this.foto = foto;
        this.ultimaModificacion = ultimaModificacion;  
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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

    public ArrayList<String> getArrayLisAtributos () {
        ArrayList<String> lista = new ArrayList<>();
        lista.add(codigoBarras);
        lista.add(clave);
        lista.add(descripcion);
        lista.add(marca);
        lista.add("" + precio);
        lista.add("" + descuento);
        lista.add("" + stock);
        lista.add("" + idCategoria);
        
        return lista;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", codigoBarras=" + codigoBarras + ", clave=" + clave + ", descripcion=" + descripcion + ", marca=" + marca + ", precio=" + precio + ", descuento=" + descuento + ", stock=" + stock + ", foto=" + foto + ", ultimaModificacion=" + ultimaModificacion + ", idCategoria=" + idCategoria + '}';
    }
}
