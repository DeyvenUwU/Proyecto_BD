package ventanas;

import conexiones.accesoClientes;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import objetos.Cliente;
import objetos.Empleado;

public class CrudClientes extends javax.swing.JFrame {
    
    private Empleado usuario;
    private accesoClientes clientes;
    private ArrayList<Cliente> clientesData;
    private ArrayList<Cliente> filtro;
    
    public CrudClientes() {
        config();
    }

    public CrudClientes(Empleado usuario) {
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
        
        clientes = new accesoClientes();
        clientesData = clientes.getTodosLosClientes();
        filtro = clientesData;
        actualizarTabla(clientesData);
    }
    
    public void actualizarTabla (ArrayList<Cliente> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("ID");
        tabla.addColumn("RFC");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("DIRECCION");
        tabla.addColumn("TELEFONO");
        tabla.addColumn("CORREO");
        tabla.addColumn("ÚLTIMA MODIFICACIÓN");
        
        for (Cliente c : lista) {
            Object[] Fila = new Object[7];
            Fila[0] = c.getId();
            Fila[1] = c.getRfc();
            Fila[2] = c.getNombre();
            Fila[3] = c.getDireccion();
            Fila[4] = c.getTelefono();
            Fila[5] = c.getCorreo();
            Fila[6] = c.getUltimaModificacion();
            
            tabla.addRow(Fila);
        }
        
        tblClientes.setModel(tabla);
        tblClientes.setRowHeight(30);
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(110);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(166);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(166);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(92);
        tblClientes.getColumnModel().getColumn(5).setPreferredWidth(166);
        tblClientes.getColumnModel().getColumn(6).setPreferredWidth(148);
        tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lblCantEmpleados.setText(lista.size() + " Cliente(s)");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btbAgregar = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        lblCantEmpleados = new javax.swing.JLabel();
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
        lblTitulo.setText("CONTROL CLIENTES");
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

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.setComponentPopupMenu(jPopupMenu1);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClientesMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        btbAgregar.setBackground(new java.awt.Color(102, 252, 102));
        btbAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btbAgregar.setText("AGREGAR CLIENTE...");
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
        lblCantEmpleados.setText("N Cliente(s)");
        lblCantEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCantEmpleadosMousePressed(evt);
            }
        });

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
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblCantEmpleados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btbAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCantEmpleados)
                            .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btbAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(tnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            FormularioCliente fc = new FormularioCliente(usuario);
            fc.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden AGREGAR CLIENTES", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btbAgregarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        if (usuario.getPuesto().toLowerCase().equals("admin")) {
            int index = tblClientes.getSelectedRow();
            Cliente c = filtro.get(index);
            this.dispose();
            FormularioCliente fc = new FormularioCliente(usuario, c);
            fc.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden MODIFICAR CLIENTES", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if (usuario.getPuesto().toLowerCase().equals("admin")) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                                                     "¿Estás seguro de que quieres borrar este empleado?", 
                                                     "Confirmación", 
                                                     JOptionPane.YES_NO_OPTION, 
                                                     JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                int index = tblClientes.getSelectedRow();
                Cliente c = filtro.get(index);
                clientes.eliminarCliente(c.getId());
                clientesData = clientes.getTodosLosClientes();
                filtro.remove(c);
                actualizarTabla(filtro);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Los cajeros no pueden ELIMINAR CLIENTES", "Sin permisos", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_eliminarActionPerformed

    private void tnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnBuscarActionPerformed
        String texto = txtBuscar.getText().toLowerCase();
        boolean flagTexto = texto.equals("buscar...");
        
        if (flagTexto && tblClientes.getRowCount() != clientesData.size()) {
            filtro = clientesData;
            actualizarTabla(clientesData);
        }
        else {
            filtro = new ArrayList<>();
            for(Cliente c : clientesData) {
                if (c.getNombre().toLowerCase().contains(texto) ||
                    c.getRfc().toLowerCase().contains(texto) ||
                    c.getDireccion().toLowerCase().contains(texto) ||
                    c.getTelefono().toLowerCase().contains(texto) ||
                    c.getCorreo().toLowerCase().contains(texto) ) 
                        filtro.add(c);
            }
            
            actualizarTabla(filtro);
        } 
    }//GEN-LAST:event_tnBuscarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        this.dispose();
        Menu m = new Menu(usuario);
        m.setVisible(true);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void tblClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMousePressed
        deshailitarTxtBuscar();
        tblClientes.requestFocus();
        
        int index = tblClientes.rowAtPoint(tblClientes.getMousePosition());
        tblClientes.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_tblClientesMousePressed

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
            java.util.logging.Logger.getLogger(CrudClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrudClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrudClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrudClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton btbAgregar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantEmpleados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem modificar;
    private javax.swing.JTable tblClientes;
    private javax.swing.JButton tnBuscar;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
