package ventanas;

import conexiones.accesoCategorias;
import conexiones.accesoClientes;
import conexiones.accesoProductos;
import conexiones.accesoVentas;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import objetos.Categoria;
import objetos.Cliente;
import objetos.DetalleVenta;
import objetos.ImgTabla;
import objetos.Empleado;
import objetos.Producto;
import objetos.Venta;

public class Caja extends javax.swing.JFrame {
    
    private Empleado usuario;
    
    private accesoClientes clientes;
    private accesoCategorias categorias;
    private accesoProductos productos;
    private accesoVentas ventas;
    private ArrayList<Cliente> clientesData;
    private ArrayList<Categoria> categoriasData;
    private ArrayList<Producto> productosData;
    private ArrayList<Producto> filtro;
    private ArrayList<DetalleVenta> ventaData;
    
    private ImageIcon noDisponible = new ImageIcon("C:\\Users\\EliteBook\\Documents\\NetBeansProjects\\Proyecto_BD\\src\\images\\NoAvailable.jpg");
    
    public Caja() {
        config();
    }
    
    public Caja(Empleado usuario) {
        config();
        this.usuario = usuario;
        
        lblAtiende.setText("Le atiende: " + usuario.getNombre());
    }
    
    private void config () {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblProductos.rowAtPoint(e.getPoint());
                    Producto seleccionado = filtro.get(row);
                    agregarProducto(seleccionado);
                }
            }
        });
        
        txtCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                Producto p = productos.getProducto(codigo);
                
                if (p != null)
                    agregarProducto(p);
                else
                    JOptionPane.showMessageDialog(Caja.this ,"ERROR: Ese producto no existe", "", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        categorias = new accesoCategorias();
        categoriasData = categorias.getTodasLasCategorias();
        for (Categoria c : categoriasData)
            cmbCategorias.addItem(c.getNombre());
        
        clientes = new accesoClientes();
        clientesData = clientes.getTodosLosClientes();
        for (Cliente c : clientesData)
            cmbClientes.addItem(c.getNombre());
        
        ventaData = new ArrayList<>();
        actualizarVenta();
        
        productos = new accesoProductos();
        productosData = productos.getTodosLosProductos();
        for (Producto p : productosData) {
            if (p.getStock() > 0)
                filtro = productosData;
        }
        for (Producto p : productosData) {
            if (p.getFoto() != null) {
                if (p.getFotoLeida() == null) {
                    try {
                        BufferedImage foto = ImageIO.read(p.getFoto());
                        p.setFotoLeida(foto);
                    } catch (IOException ex) {
                        Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        }
        
        actualizarTabla(productosData);
        ventas = new accesoVentas();
    }
    
    private void agregarProducto(Producto p) {
        boolean yaExiste = false;
        for (DetalleVenta dv : ventaData) {
            if (dv.getIdProducto() == p.getId()) {
                if (p.getStock()-dv.getCantidad() > 0)
                    dv.setCantidad(dv.getCantidad() + 1);
                else
                    JOptionPane.showMessageDialog(Caja.this ,"ERROR: Solo hay " + dv.getCantidad() + " piezas", "", JOptionPane.ERROR_MESSAGE);
                yaExiste = true;
                break;
            }
        }
        
        if (!yaExiste) {
            DetalleVenta nuevo = new DetalleVenta();
            nuevo.setIdProducto(p.getId());
            nuevo.setCantidad(1);
            nuevo.setPrecio(p.getPrecio());
            nuevo.setDescuento(p.getDescuento());
                        
            ventaData.add(nuevo);
        }

        actualizarVenta();
    }
    
    private void actualizarTabla (ArrayList<Producto> lista) {
        tblProductos.setDefaultRenderer(Object.class, new ImgTabla());
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("FOTO");
        tabla.addColumn("DESCRIPCION");
        
        for (Producto p : lista) {
            Object[] Fila = new Object[2];
            ImageIcon icon = null;
            if (p.getFotoLeida() != null)
                icon = new ImageIcon(p.getFotoLeida().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            else
                icon = new ImageIcon(noDisponible.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            Fila[0] = new JLabel(icon);  
            Fila[1] = p.getDescripcion();
                
            tabla.addRow(Fila);
        }
        
        tblProductos.setModel(tabla);
        tblProductos.setRowHeight(100);
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(358);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lblCantProductos.setText(lista.size() + " Producto(s)");
    }
    
    private void actualizarVenta () {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column == 2)
                    return true;
                
                return false;
            }
        };
        
        tabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 2) {
                        Integer newValue = Integer.parseInt((String) tabla.getValueAt(row, column));
                        Producto p = filtro.get(row);
                        
                        if (newValue <= p.getStock()) {
                            for(DetalleVenta dv : ventaData) {
                                if (dv.getIdProducto() == p.getId()) {
                                    dv.setCantidad(newValue);
                                    break;
                                }
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(Caja.this ,"ERROR: Solo quedan " + p.getStock() + " piezas", "", JOptionPane.ERROR_MESSAGE);
                        actualizarVenta();
                    }
                }
            }
        }); 
        
        tabla.addColumn("CLAVE");
        tabla.addColumn("DESCRIPCION");
        tabla.addColumn("CANT.");
        tabla.addColumn("PRECIO UNITARIO");
        tabla.addColumn("IMPORTE");
        
        Double sumaSubTotal = 0.0;
        Double sumaDesc = 0.0;
        Double sumaIva = 0.0;
        
        for (DetalleVenta dv : ventaData) {
            Object[] Fila = new Object[5];
            
            int cant = dv.getCantidad();
            Double precio = dv.getPrecio();
            Double iva = precio/1.16;
            Double desc = precio*(dv.getDescuento()/100.0);
            
            sumaSubTotal += iva*cant;
            sumaIva += (precio-iva)*cant;
            sumaDesc += (desc)*cant;
            
            Fila[0] = dv.getIdProducto();
            String descripcion = productos.getDescripcionProducto(dv.getIdProducto());
            Fila[1] = descripcion;
            Fila[2] = dv.getCantidad();   
            Fila[3] = "$ " + String.format("%.2f", precio-desc);
            Fila[4] = "$ " + String.format("%.2f", (precio-desc)*dv.getCantidad());
            
            tabla.addRow(Fila);
        }

        lblSubTotal.setText(String.format("%.2f", sumaSubTotal));
        lblIVA.setText(String.format("%.2f", sumaIva));
        lblDescuento.setText(String.format("%.2f", sumaDesc));
        lblTotal.setText(String.format("%.2f", sumaSubTotal+sumaIva-sumaDesc));
        
        tblVenta.setModel(tabla);
        tblVenta.setRowHeight(20);
        tblVenta.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblVenta.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblVenta.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblVenta.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblVenta.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        itmEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblIVA = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblAtiende = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        lblCantProductos = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        btnMenu = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();

        itmEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        itmEliminar.setText("Eliminar");
        itmEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itmEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 173, 29));

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Código de Barras:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("CAJA");

        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblVenta.setComponentPopupMenu(jPopupMenu1);
        tblVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVentaMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblVenta);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTotal.setText("0.00");

        lblSubTotal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSubTotal.setText("0.00");

        lblIVA.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblIVA.setText("0.00");

        lblDescuento.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDescuento.setText("0.00");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("TOTAL: $");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Descuento:  $");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Iva:               $");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Subtotal:      $");

        lblAtiende.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAtiende.setText("Le Atiende:");

        cmbClientes.setBackground(new java.awt.Color(102, 255, 102));
        cmbClientes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Cliente:");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        lblCantProductos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCantProductos.setText("N Producto(s)");

        cmbCategorias.setBackground(new java.awt.Color(102, 255, 102));
        cmbCategorias.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los Productos" }));

        btnMenu.setBackground(new java.awt.Color(102, 255, 102));
        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMenu.setText("MENU PRINCIPAL");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(102, 255, 102));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCobrar.setBackground(new java.awt.Color(102, 255, 102));
        btnCobrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCobrar.setText("COBRAR $");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addComponent(lblAtiende)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(cmbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(569, 569, 569))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblCantProductos))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDescuento, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblIVA, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblSubTotal, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbCategorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAtiende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(lblSubTotal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(lblIVA))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(lblDescuento)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTotal)
                                    .addComponent(jLabel3))
                                .addGap(36, 36, 36))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCantProductos)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char tecla = evt.getKeyChar();
        String s = txtCodigo.getText() + tecla;
        
        if (s.length() > 20)
            evt.consume();
        else if (!(tecla >= '0' && tecla <= '9'))
            evt.consume();
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void tblVentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentaMousePressed
        int index = tblVenta.rowAtPoint(tblVenta.getMousePosition());
        tblVenta.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_tblVentaMousePressed

    private void itmEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEliminarActionPerformed
        int index = tblVenta.getSelectedRow();
        ventaData.remove(index);
        actualizarVenta();
    }//GEN-LAST:event_itmEliminarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        this.dispose();
        Menu m = new Menu(usuario);
        m.setVisible(true);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String categoria = "" + cmbCategorias.getSelectedItem();
        if (categoria.equals("Todos los Productos")) {
            if (tblProductos.getRowCount() != productosData.size()) {
                filtro = productosData;
                actualizarTabla(productosData);
            }
        }
        else {
            filtro = new ArrayList<>();
            for (Producto p : productosData) {
                String pCat = categorias.getNombre(p.getIdCategoria());
                if (pCat.equals(categoria))
                    filtro.add(p);
            }
            
            actualizarTabla(filtro);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        if (ventaData.size() > 0) {
            Venta v = new Venta();
            v.setSubTotal(Double.parseDouble(lblSubTotal.getText()));
            v.setIva(Double.parseDouble(lblIVA.getText()));
            v.setDescuento(Double.parseDouble(lblDescuento.getText()));
            v.setTotal(Double.parseDouble(lblTotal.getText()));  
            v.setIdEmpleado(usuario.getId());
            v.setIdCliente(clientes.getIdCliente("" + cmbClientes.getSelectedItem()));

            int idVenta = ventas.agregarVenta(v, ventaData);
            ticket t = new ticket(usuario, idVenta);
            t.setVisible(true);
            
            ventaData.clear();
            actualizarVenta();
            lblSubTotal.setText("0.00");
            lblIVA.setText("0.00");
            lblDescuento.setText("0.00");
            lblTotal.setText("0.00");
            
            productosData = productos.getTodosLosProductos();
            filtro = productosData;
            actualizarTabla(productosData);
        }
    }//GEN-LAST:event_btnCobrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Caja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JMenuItem itmEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAtiende;
    private javax.swing.JLabel lblCantProductos;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
