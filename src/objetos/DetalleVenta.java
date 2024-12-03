package objetos;

import java.util.ArrayList;

public class DetalleVenta {
    
    private int id;
    private int cantidad;
    private double precio;
    private double descuento;
    
    private int idVenta;
    private int idProducto;

    public DetalleVenta() {
        
    }
    
    public DetalleVenta(int id, int cantidad, double precio, double descuento, int idVenta, int idProducto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    public ArrayList<String> getArrayLisAtributos() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("" + idVenta);
        lista.add("" + idProducto);
        lista.add("" + cantidad);
        lista.add("" + precio);
        lista.add("" + descuento);
        
        return lista;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "id=" + id + ", cantidad=" + cantidad + ", precio=" + precio + ", descuento=" + descuento + ", idVenta=" + idVenta + ", idProducto=" + idProducto + '}';
    }
}
