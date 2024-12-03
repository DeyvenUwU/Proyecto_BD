package ventanas;

import conexiones.accesoVentas;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import objetos.Empleado;
import objetos.Venta;

public class visorTickets extends javax.swing.JFrame {

    private Empleado usuario;
    private accesoVentas ventas;
    private ArrayList<Venta> ventasData;
    private ArrayList<Venta> filtro;
    
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
    public visorTickets() {
        config();
    }
    
    public visorTickets(Empleado usuario) {
        config();
        this.usuario = usuario;
    }
    
    private void config() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBackground(new Color (230, 230, 230));
        try {
            Date d1 = formatoFecha.parse("2000-01-01");
            fechaInicio.setDate(d1);
            Date d2 = formatoFecha.parse("2025-01-01");
            fechaFin.setDate(d2);
        } catch (ParseException ex) {
            Logger.getLogger(FormularioEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cmbTrimestre.setVisible(false);
        anoTrimestre.setVisible(false);
        btnBuscar.setVisible(false);
        txtSearch.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        
        ventas = new accesoVentas();
        ventasData = ventas.getTodasLasVentas();
        filtro = ventasData;
        actualizarTabla(ventasData);
    }
    
    /*private void deshabilitarTodos() {
        fechaInicio.setEnabled(false);
        fechaFin.setEnabled(false);
        mesMes.setEnabled(false);
        anoMes.setEnabled(false);
        cmbTrimestre.setEnabled(false);
        anoTrimestre.setEnabled(false);
    }*/
    
    public void actualizarTabla (ArrayList<Venta> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        
        tabla.addColumn("ID");
        tabla.addColumn("FECHA");
        tabla.addColumn("TOTAL");
        tabla.addColumn("EMPLEADO");
        tabla.addColumn("CLIENTE");
        
        for (Venta v : lista) {
            Object[] Fila = new Object[5];
            Fila[0] = v.getId();
            Fila[1] = v.getFecha();
            Fila[2] = v.getTotal();
            Fila[3] = v.getIdEmpleado();
            Fila[4] = v.getIdCliente();

            tabla.addRow(Fila);
        }
        
        tblTickets.setModel(tabla);
        tblTickets.setRowHeight(20);
        tblTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTickets.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblTickets.rowAtPoint(e.getPoint());
                    String id = "" + tblTickets.getValueAt(row, 0);
                    
                    ticket t = new ticket(usuario, Integer.parseInt(id));
                    t.setVisible(true);
                }
            }
        });
    }
    
    public void actualizarEmpleado (ArrayList<Object[]> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("NOMBRE");
        tabla.addColumn("TOTAL");
        tabla.addColumn("CANTIDAD");
        
        for (Object[] o : lista) {
            Object[] Fila = new Object[3];
            Fila[0] = o[0];
            Fila[1] = o[1];
            Fila[2] = o[2];

            tabla.addRow(Fila);
        }
        
        tblTickets.setModel(tabla);
        tblTickets.setRowHeight(20);
        tblTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void actualizarTrimestre (ArrayList<Object[]> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("PRODUCTO");
        tabla.addColumn("TRIMESTRE 1");
        tabla.addColumn("TRIMESTRE 2");
        tabla.addColumn("TRIMESTRE 3");
        tabla.addColumn("TRIMESTRE 4");
        
        for (Object[] o : lista) {
            Object[] Fila = new Object[5];
            Fila[0] = o[0];
            Fila[1] = o[1];
            Fila[2] = o[2];
            Fila[3] = o[3];
            Fila[4] = o[4];

            tabla.addRow(Fila);
        }
        
        tblTickets.setModel(tabla);
        tblTickets.setRowHeight(20);
        tblTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void actualizarMes (ArrayList<Object[]> lista) {
        DefaultTableModel tabla = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        tabla.addColumn("ID");
        tabla.addColumn("FECHA");
        tabla.addColumn("CLIENTE");
        tabla.addColumn("EMPLEADO");
        tabla.addColumn("TOTAL");
        tabla.addColumn("CANT. DETALLES");
        
        for (Object[] o : lista) {
            Object[] Fila = new Object[6];
            Fila[0] = o[0];
            Fila[1] = o[1];
            Fila[2] = o[2];
            Fila[3] = o[3];
            Fila[4] = o[4];
            Fila[5] = o[5];

            tabla.addRow(Fila);
        }
        
        tblTickets.setModel(tabla);
        tblTickets.setRowHeight(20);
        tblTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTickets = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        rbtFecha = new javax.swing.JRadioButton();
        rbtMes = new javax.swing.JRadioButton();
        rbtTrimestre = new javax.swing.JRadioButton();
        rbtEmpleado = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        fechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        fechaFin = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        anoMes = new com.toedter.calendar.JYearChooser();
        cmbTrimestre = new javax.swing.JComboBox<>();
        anoTrimestre = new com.toedter.calendar.JYearChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        mesMes = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 173, 29));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("TICKETS");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearch.setText("Buscar...");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtSearchMousePressed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        tblTickets.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTickets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTicketsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblTickets);

        btnVolver.setBackground(new java.awt.Color(102, 255, 102));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnVolver.setText("VOLVER");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtFecha);
        rbtFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbtFecha.setText("Por fecha");
        rbtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtFechaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtMes);
        rbtMes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbtMes.setText("Por Mes");
        rbtMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtMesActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtTrimestre);
        rbtTrimestre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbtTrimestre.setText("Por Trimestre");
        rbtTrimestre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtTrimestreActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtEmpleado);
        rbtEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rbtEmpleado.setText("Por Empleado");
        rbtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtEmpleadoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("De:");

        fechaInicio.setDateFormatString("yyyy-MM--dd");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("A:");

        fechaFin.setDateFormatString("yyyy-MM-dd");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Mes:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Año:");

        cmbTrimestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Año:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Trim:");

        btnBuscar.setBackground(new java.awt.Color(102, 255, 102));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        btnBuscar.setText("Buscar");

        mesMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtEmpleado)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbtFecha)
                                        .addGap(79, 79, 79)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbtMes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbtTrimestre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7)))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbTrimestre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(anoTrimestre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(mesMes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel5)))
                                            .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(anoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtFecha)
                        .addComponent(jLabel2))
                    .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtMes)
                            .addComponent(jLabel4)
                            .addComponent(mesMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(anoMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(anoTrimestre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtTrimestre)
                        .addComponent(cmbTrimestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
        Menu m = new Menu(usuario);
        m.setVisible(true);
    }//GEN-LAST:event_btnVolverActionPerformed

    private void tblTicketsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTicketsMousePressed
        int index = tblTickets.rowAtPoint(tblTickets.getMousePosition());
        tblTickets.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_tblTicketsMousePressed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        txtSearch.selectAll();
        txtSearch.setBackground(Color.WHITE);
        txtSearch.setForeground(Color.BLACK);
        
        if (txtSearch.getText().equals("Buscar..."))
            txtSearch.setText("");
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        if (txtSearch.getText().equals("")) {
            txtSearch.setBackground(new Color (230, 230, 230));
            txtSearch.setForeground(Color.GRAY);
            txtSearch.setText("Buscar...");
        }
    }//GEN-LAST:event_txtSearchFocusLost

    private void txtSearchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMousePressed
        txtSearch.requestFocus();
        txtSearch.selectAll();
        txtSearch.setBackground(Color.WHITE);
        txtSearch.setForeground(Color.BLACK);
        
        if (txtSearch.getText().equals("Buscar..."))
            txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMousePressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        /*String text = txtSearch.getText().toLowerCase();
        ArrayList<Object[]> filter = new ArrayList<> ();
            
        for (Object[] o : tickets) {
            if (o[0].toString().toLowerCase().contains(text) ||
                o[1].toString().toLowerCase().contains(text) ||
                o[2].toString().toLowerCase().contains(text))
                    filter.add(o);
            }
        
        refreshTable(filter);*/
    }//GEN-LAST:event_txtSearchKeyReleased

    private void rbtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtFechaActionPerformed
        //deshabilitarTodos();
        fechaInicio.setEnabled(true);
        fechaFin.setEnabled(true);
        

        Date fecha1 = fechaInicio.getDate();
        String inicio = formatoFecha.format(fecha1);
        Date fecha2 = fechaFin.getDate();
        String fin = formatoFecha.format(fecha2);
        
        ArrayList<Venta> resultados = ventas.getVentasPorFecha(inicio, fin);
        actualizarTabla(resultados);
    }//GEN-LAST:event_rbtFechaActionPerformed

    private void rbtMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtMesActionPerformed
        //deshabilitarTodos();
        mesMes.setEnabled(true);
        anoMes.setEnabled(true);
        
        int mes = mesMes.getSelectedIndex()+1;
        int ano = anoMes.getYear();
        
        System.out.println(mes + " " + ano);
        
        ArrayList<Object[]> datos = ventas.getVentasPorMes(mes, ano);
        actualizarMes(datos);
    }//GEN-LAST:event_rbtMesActionPerformed

    private void rbtTrimestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtTrimestreActionPerformed
        //deshabilitarTodos();
        cmbTrimestre.setEnabled(true);
        anoTrimestre.setEnabled(true);
        ArrayList<Object[]> datos = ventas.getVentasPorTimestre();
        actualizarTrimestre(datos);
    }//GEN-LAST:event_rbtTrimestreActionPerformed

    private void rbtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtEmpleadoActionPerformed
        //deshabilitarTodos();
        ArrayList<Object[]> datos = ventas.getVentasPorEmpleado();
        actualizarEmpleado(datos);
    }//GEN-LAST:event_rbtEmpleadoActionPerformed

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
            java.util.logging.Logger.getLogger(visorTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(visorTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(visorTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(visorTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new visorTickets().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser anoMes;
    private com.toedter.calendar.JYearChooser anoTrimestre;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbTrimestre;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> mesMes;
    private javax.swing.JRadioButton rbtEmpleado;
    private javax.swing.JRadioButton rbtFecha;
    private javax.swing.JRadioButton rbtMes;
    private javax.swing.JRadioButton rbtTrimestre;
    private javax.swing.JTable tblTickets;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
