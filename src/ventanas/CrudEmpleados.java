package ventanas;

import conexiones.accesoEmpleados;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import objetos.Empleado;

public class CrudEmpleados extends javax.swing.JFrame {
    
    private Empleado usuario;
    private accesoEmpleados empleados;
    private ArrayList<Empleado> empleadosData;
    private ArrayList<Empleado> filtro;
    
    private InputStream fotoEmpleado = null;
    private ImageIcon noDisponible = new ImageIcon("C:\\Users\\EliteBook\\Documents\\NetBeansProjects\\Proyecto_BD\\src\\images\\NoAvailable128x128.jpg");
    
    public CrudEmpleados() {
        config();
    }

    public CrudEmpleados(Empleado usuario) {
        config();
        
        this.usuario = usuario;
    }
    
    private void config() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Fondo.requestFocus();
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setBackground(new Color (230, 230, 230));
        Image imagen = noDisponible.getImage();
        lblFoto.setIcon(new ImageIcon(imagen));   
        
        empleados = new accesoEmpleados();
        empleadosData = empleados.getTodosLosEmpleados();
        filtro = empleadosData;
        actualizarTabla(empleadosData);
        
        for (Empleado e : empleadosData) {
            if (e.getFoto() != null) {
                if (e.getFotoLeida() == null) {
                    try {
                        BufferedImage foto = ImageIO.read(e.getFoto());
                        e.setFotoLeida(foto);
                    } catch (IOException ex) {
                        Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        }
    }
    
    public void actualizarTabla (ArrayList<Empleado> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("ID");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("PUESTO");
        tabla.addColumn("SEXO");
        tabla.addColumn("ULTIMA MODIFICACIÓN");
        
        for (Empleado e : lista) {
            Object[] Fila = new Object[5];
            Fila[0] = e.getId();
            Fila[1] = e.getNombre();
            Fila[2] = e.getPuesto();
            Fila[3] = e.getSexo();
            Fila[4] = e.getUltimaModificacion();
            
            tabla.addRow(Fila);
        }
        
        tblEmpleados.setModel(tabla);
        tblEmpleados.setRowHeight(30);
        tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(185);
        tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblEmpleados.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lblCantEmpleados.setText(lista.size() + " Empleado(s)");
    }
    
    private void mostrarInfo(Empleado e) {
        String datos = "ID: " + e.getId() + "\n" +
                       "RFC: : " + e.getRfc() + "\n" +
                       "NOMBRE: " + e.getNombre() + "\n" +
                       "EDAD: " + e.getEdad() + "\n" +
                       "SEXO: " + e.getSexo() + "\n" +
                       "DIRECCION: " + e.getDireccion() + "\n" +
                       "TELEFONO: " + e.getTelefono() + "\n" +
                       "CORREO: " + e.getCorreo() + "\n" +
                       "FECHA NACIMIENTO: " + e.getFecha_nac() + "\n" +
                       "FECHA CONTRATACION: " + e.getFecha_cont() + "\n" +
                       "PUESTO: " + e.getPuesto() + "\n";
        txtDatos.setText(datos);
        
        if (e.getFotoLeida() != null)
            lblFoto.setIcon(new ImageIcon(e.getFotoLeida().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
        else
            lblFoto.setIcon(new ImageIcon(noDisponible.getImage()));
    }
    
    private void hailitarTxtBuscar () {
        txtBuscar.selectAll();
        txtBuscar.setBackground(Color.WHITE);
        txtBuscar.setForeground(Color.BLACK);
        
        if (txtBuscar.getText().equals("Buscar..."))
            txtBuscar.setText("");
    }
    
    private void deshailitarTxtBuscar () {
        if (txtBuscar.getText().equals("")) {
            txtBuscar.setBackground(new Color (230, 230, 230));
            txtBuscar.setForeground(Color.GRAY);
            txtBuscar.setText("Buscar...");
        }
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
        modificar = new javax.swing.JMenuItem();
        eliminar = new javax.swing.JMenuItem();
        Fondo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDatos = new javax.swing.JTextArea();
        lblFoto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        btbAgregar = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        lblCantEmpleados = new javax.swing.JLabel();
        cmbPuesto = new javax.swing.JComboBox<>();
        tnBuscar = new javax.swing.JButton();

        modificar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(modificar);

        eliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Fondo.setBackground(new java.awt.Color(245, 173, 29));
        Fondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FondoMousePressed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("CONTROL EMPLEADOS");
        lblTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblTituloMousePressed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtBuscar.setText("Buscar...");
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtBuscarMousePressed(evt);
            }
        });

        txtDatos.setEditable(false);
        txtDatos.setColumns(20);
        txtDatos.setRows(5);
        txtDatos.setEnabled(false);
        txtDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDatosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtDatos);

        lblFoto.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFotoMousePressed(evt);
            }
        });

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmpleados.setComponentPopupMenu(jPopupMenu1);
        tblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEmpleadosMousePressed(evt);
            }
        });
        tblEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblEmpleadosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmpleados);

        btbAgregar.setBackground(new java.awt.Color(102, 252, 102));
        btbAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btbAgregar.setText("AGREGAR EMPLEADO...");
        btbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbAgregarActionPerformed(evt);
            }
        });

        btnMenu.setBackground(new java.awt.Color(102, 252, 102));
        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMenu.setText("MENU PRINCIPAL");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        lblCantEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCantEmpleados.setText("N Empleado(s)");
        lblCantEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCantEmpleadosMousePressed(evt);
            }
        });

        cmbPuesto.setBackground(new java.awt.Color(102, 255, 102));
        cmbPuesto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los Empleados", "Admin", "Cajero" }));

        tnBuscar.setBackground(new java.awt.Color(102, 255, 102));
        tnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        tnBuscar.setText("Buscar");
        tnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitulo)
                                    .addGroup(FondoLayout.createSequentialGroup()
                                        .addComponent(cmbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblCantEmpleados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btbAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(cmbPuesto)
                            .addComponent(txtBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(btbAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblCantEmpleados))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbAgregarActionPerformed
        if (usuario.getPuesto().toLowerCase().equals("admin")) {
            this.dispose();
            FormularioEmpleado fe = new FormularioEmpleado(usuario);
            fe.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden AGREGAR EMPLEADOS", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btbAgregarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        if (usuario.getPuesto().toLowerCase().equals("admin")) {
            int index = tblEmpleados.getSelectedRow();
            Empleado e = filtro.get(index);
            this.dispose();
            FormularioEmpleado fe = new FormularioEmpleado(usuario, e);
            fe.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden MODIFICAR EMPLEADOS", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if (usuario.getPuesto().toLowerCase().equals("admin")) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                                                     "¿Estás seguro de que quieres borrar este empleado?", 
                                                     "Confirmación", 
                                                     JOptionPane.YES_NO_OPTION, 
                                                     JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                int index = tblEmpleados.getSelectedRow();
                Empleado e = filtro.get(index);

                empleados.eliminarEmpleado(e.getId());
                empleadosData = empleados.getTodosLosEmpleados();
                filtro.remove(e);
                lblFoto.setIcon(noDisponible);
                txtDatos.setText("");
                actualizarTabla(filtro);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden ELIMINAR EMPLEADOS", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_eliminarActionPerformed

    private void tnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnBuscarActionPerformed
        String puesto = "" + cmbPuesto.getSelectedItem();
        String texto = txtBuscar.getText().toLowerCase();
        boolean flagCat = puesto.equals("Todos los Empleados");
        boolean flagTexto = texto.equals("buscar...");
        
        if (flagCat && flagTexto && tblEmpleados.getRowCount() != empleadosData.size()) {
            filtro = empleadosData;
            actualizarTabla(empleadosData);
        }
        else {
            int indexCat = cmbPuesto.getSelectedIndex();
            filtro = new ArrayList<>();
                    
            for(Empleado e : empleadosData) {
                String ePuesto = e.getPuesto();
                if (indexCat == 0 || ePuesto.equals(puesto)) {
                    if (flagTexto)
                        filtro.add(e);
                    else if (e.getNombre().toLowerCase().contains(texto) ||
                             e.getRfc().toLowerCase().contains(texto) ||
                             ePuesto.toLowerCase().contains(texto))
                        filtro.add(e);
                }
            }
            
            actualizarTabla(filtro);
        }  
    }//GEN-LAST:event_tnBuscarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        this.dispose();
        Menu m = new Menu(usuario);
        m.setVisible(true);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void tblEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosMousePressed
        deshailitarTxtBuscar();
        tblEmpleados.requestFocus();
        
        int index = tblEmpleados.rowAtPoint(tblEmpleados.getMousePosition());
        tblEmpleados.setRowSelectionInterval(index, index);
        Empleado e = filtro.get(index);
        mostrarInfo(e);
    }//GEN-LAST:event_tblEmpleadosMousePressed

    private void tblEmpleadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEmpleadosKeyReleased
        int index = tblEmpleados.getSelectedRow();
        Empleado e = filtro.get(index);
        mostrarInfo(e);
    }//GEN-LAST:event_tblEmpleadosKeyReleased

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        hailitarTxtBuscar();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        deshailitarTxtBuscar();
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMousePressed
        hailitarTxtBuscar();
        txtBuscar.requestFocus();
    }//GEN-LAST:event_txtBuscarMousePressed

    private void txtDatosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDatosMousePressed
        deshailitarTxtBuscar();
        txtDatos.requestFocus();
    }//GEN-LAST:event_txtDatosMousePressed

    private void lblFotoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMousePressed
        deshailitarTxtBuscar();
        lblFoto.requestFocus();
    }//GEN-LAST:event_lblFotoMousePressed

    private void FondoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FondoMousePressed
        deshailitarTxtBuscar();
        Fondo.requestFocus();
    }//GEN-LAST:event_FondoMousePressed

    private void lblTituloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTituloMousePressed
        deshailitarTxtBuscar();
        lblTitulo.requestFocus();
    }//GEN-LAST:event_lblTituloMousePressed

    private void lblCantEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCantEmpleadosMousePressed
        deshailitarTxtBuscar();
        lblCantEmpleados.requestFocus();
    }//GEN-LAST:event_lblCantEmpleadosMousePressed

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
            java.util.logging.Logger.getLogger(CrudEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrudEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrudEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrudEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton btbAgregar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cmbPuesto;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantEmpleados;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem modificar;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JButton tnBuscar;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDatos;
    // End of variables declaration//GEN-END:variables
}
