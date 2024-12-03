package objetos;

import java.util.ArrayList;

public class Venta {
    
    private int id;
    private String fecha;
    private double subTotal;
    private double iva;
    private double descuento;
    private double total;

    private int idEmpleado;
    private int idCliente;

    public Venta() {
        
    }
    
    public Venta(int id, String fecha, double subTotal, double descuento, int idEmpleado, int idCliente) {
        this.id = id;
        this.fecha = fecha;
        this.subTotal = subTotal;
        this.descuento = descuento;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        //Calcular iva
        //calcular total
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public ArrayList<String> getArrayLisAtributos() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("" + subTotal);
        lista.add("" + iva);
        lista.add("" + descuento);
        lista.add("" + total);
        lista.add("" + idEmpleado);
        lista.add("" + idCliente);
        
        return lista;
    }
    @Override
    public String toString() {
        return "Venta{" + "id=" + id + ", fecha=" + fecha + ", subTotal=" + subTotal + ", descuento=" + descuento + ", iva=" + iva + ", total=" + total + ", idEmpleado=" + idEmpleado + ", idCliente=" + idCliente + '}';
    }
}
