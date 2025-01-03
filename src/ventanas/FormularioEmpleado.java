package ventanas;

import conexiones.accesoEmpleados;
import fieldFormats.RangoDocumentFilter;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import objetos.Empleado;

public class FormularioEmpleado extends javax.swing.JFrame {
    
    private Empleado usuario;
    private Empleado empleado;
    private InputStream fotoEmpleado = null;
    
    private accesoEmpleados empleados;
    
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
    public FormularioEmpleado() {
        config();
    }
    public FormularioEmpleado(Empleado usuario) {
        config();
        
        this.usuario = usuario;
        
        lblUltimaModificacion.setVisible(false);
        jLabel4.setVisible(false);
        txtContratacion.setVisible(false); 
        txtFechaNacimiento.setDate(new Date());
    }
    
    public FormularioEmpleado(Empleado usuario, Empleado empleado) {
        config();
        
        this.usuario = usuario;
        this.empleado = empleado;
        
        txtContratacion.setText(empleado.getFecha_cont());
        txtRFC.setText(empleado.getRfc());
        txtNombre.setText(empleado.getNombre());
        txtEdad.setText("" + empleado.getEdad());
        cmbSexo.setSelectedItem(empleado.getSexo());
        txtDireccion.setText(empleado.getDireccion());
        txtTelefono.setText(empleado.getTelefono());
        txtCorreo.setText(empleado.getCorreo());
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date d = formato.parse(empleado.getFecha_nac());
            txtFechaNacimiento.setDate(d);
        } catch (ParseException ex) {
            Logger.getLogger(FormularioEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbPuesto.setSelectedItem(empleado.getPuesto());
        
        if (empleado.getFotoLeida() != null)
            lblFoto.setIcon(new ImageIcon(empleado.getFotoLeida().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
        lblUltimaModificacion.setText("Última Modificación: " + empleado.getUltimaModificacion());
    }
    
    private void config() {
        initComponents();
        
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ((AbstractDocument) txtEdad.getDocument()).setDocumentFilter(new RangoDocumentFilter(1, 100));
        try {
            Date fechaMinima = formatoFecha.parse("1924-01-01");
            txtFechaNacimiento.setMinSelectableDate(fechaMinima);
            txtFechaNacimiento.setMaxSelectableDate(new Date());
        } catch (ParseException ex) {
            Logger.getLogger(FormularioEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        empleados = new accesoEmpleados();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnQuitar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        txtContratacion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbPuesto = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cmbSexo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnSubir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblUltimaModificacion = new javax.swing.JLabel();
        txtFechaNacimiento = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 173, 29));

        lblFoto.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NoAvailable128x128.jpg"))); // NOI18N

        btnQuitar.setBackground(new java.awt.Color(102, 255, 102));
        btnQuitar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuitar.setText("QUITAR");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("AGREGAR/MODIFICAR EMPLEADO");

        txtRFC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtRFC.setNextFocusableComponent(txtNombre);
        txtRFC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRFCFocusGained(evt);
            }
        });
        txtRFC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtRFCMousePressed(evt);
            }
        });
        txtRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRFCKeyTyped(evt);
            }
        });

        txtContratacion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContratacion.setEnabled(false);
        txtContratacion.setNextFocusableComponent(txtRFC);
        txtContratacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContratacionFocusGained(evt);
            }
        });
        txtContratacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContratacionKeyTyped(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombre.setNextFocusableComponent(txtEdad);
        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreFocusGained(evt);
            }
        });
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreMousePressed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Contratación:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("* RFC:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("* Nombre:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("* Fecha Nacimiento:");

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCorreo.setNextFocusableComponent(txtFechaNacimiento);
        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCorreoFocusGained(evt);
            }
        });
        txtCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCorreoMousePressed(evt);
            }
        });
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Correo:");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelefono.setNextFocusableComponent(txtCorreo);
        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusGained(evt);
            }
        });
        txtTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTelefonoMousePressed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Teléfono:");

        txtDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDireccion.setNextFocusableComponent(txtTelefono);
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
        });
        txtDireccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDireccionMousePressed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Dirección:");

        cmbPuesto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Cajero" }));
        cmbPuesto.setNextFocusableComponent(txtDireccion);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Puesto:");

        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        cmbSexo.setNextFocusableComponent(cmbPuesto);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Sexo:");

        txtEdad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEdad.setNextFocusableComponent(cmbSexo);
        txtEdad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEdadFocusGained(evt);
            }
        });
        txtEdad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtEdadMousePressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("* Edad:");

        btnSubir.setBackground(new java.awt.Color(102, 255, 102));
        btnSubir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSubir.setText("SUBIR");
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(102, 255, 102));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setNextFocusableComponent(txtContratacion);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(102, 255, 102));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setNextFocusableComponent(btnGuardar);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblUltimaModificacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUltimaModificacion.setText("Última Modificación:");

        txtFechaNacimiento.setDateFormatString("yyyy-MM-dd");
        txtFechaNacimiento.setMinSelectableDate(new java.util.Date(-62135744292000L));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtRFC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                            .addComponent(txtContratacion, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNombre)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE))
                            .addComponent(lblUltimaModificacion))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbPuesto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUltimaModificacion)
                        .addGap(22, 22, 22)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8))
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        CrudEmpleados ce = new CrudEmpleados(usuario);
        ce.setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String rfc = txtRFC.getText();
        String nombre = txtNombre.getText();
        String edad = txtEdad.getText();
        Date fechaNacimiento = txtFechaNacimiento.getDate();
        
        String errorMessage = "FALTA INGRESAR:";
        if (rfc.equals(""))
            errorMessage += "\n - RFC";
        if (nombre.equals(""))
            errorMessage += "\n - Nombre";
        if (edad.equals(""))
            errorMessage += "\n - Edad";
        if (fechaNacimiento == null)
            errorMessage += "\n - Fecha de Nacimiento";
        
        if (errorMessage.equals("FALTA INGRESAR:")) {
            Empleado nuevo = new Empleado();
            if (empleado != null)
                nuevo.setId(empleado.getId());
            nuevo.setRfc(rfc);
            nuevo.setNombre(nombre);
            nuevo.setEdad(Integer.parseInt(edad));
            nuevo.setSexo("" + cmbSexo.getSelectedItem());
            nuevo.setDireccion(txtDireccion.getText());
            nuevo.setTelefono(txtTelefono.getText());
            nuevo.setCorreo(txtCorreo.getText());
            nuevo.setFecha_nac(formatoFecha.format(fechaNacimiento));
            nuevo.setPuesto("" + cmbPuesto.getSelectedItem());
            nuevo.setFoto(fotoEmpleado);

            boolean ok = empleados.guardarEmpleado(nuevo);
            if (ok) {
                JOptionPane.showMessageDialog(this, "El empleado se ha guardado", "", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                CrudEmpleados ce = new CrudEmpleados(usuario);
                ce.setVisible(true);
            }
            else
                JOptionPane.showMessageDialog(this, "El empleado no se pudo guardar", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(this, errorMessage, "", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        JFileChooser archivoSelector = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de imagen", "jpg", "png");
        archivoSelector.setFileFilter(filtro);
        
        int resultado = archivoSelector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File selectedFile = archivoSelector.getSelectedFile();
            ImageIcon imagenIcono = new ImageIcon(selectedFile.getAbsolutePath());
            Image imagen = imagenIcono.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagenRedimensionada));
            
            try {
                fotoEmpleado = new FileInputStream (selectedFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FormularioProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        ImageIcon imagenIcono = new ImageIcon("C:\\Users\\EliteBook\\Documents\\NetBeansProjects\\Proyecto_BD\\src\\images\\NoAvailable128x128.jpg");
        Image imagen = imagenIcono.getImage();   
        lblFoto.setIcon(new ImageIcon(imagen));
        
        fotoEmpleado = empleado.getFoto();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtContratacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContratacionFocusGained
        txtContratacion.selectAll();
    }//GEN-LAST:event_txtContratacionFocusGained

    private void txtRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRFCFocusGained
        txtRFC.selectAll();
    }//GEN-LAST:event_txtRFCFocusGained

    private void txtRFCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRFCMousePressed
        txtRFC.selectAll();
    }//GEN-LAST:event_txtRFCMousePressed

    private void txtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusGained
        txtNombre.selectAll();
    }//GEN-LAST:event_txtNombreFocusGained

    private void txtNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMousePressed
        txtNombre.selectAll();
    }//GEN-LAST:event_txtNombreMousePressed

    private void txtEdadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEdadFocusGained
        txtEdad.selectAll();
    }//GEN-LAST:event_txtEdadFocusGained

    private void txtEdadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEdadMousePressed
        txtEdad.selectAll();
    }//GEN-LAST:event_txtEdadMousePressed

    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained
        txtDireccion.selectAll();
    }//GEN-LAST:event_txtDireccionFocusGained

    private void txtDireccionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDireccionMousePressed
        txtDireccion.selectAll();
    }//GEN-LAST:event_txtDireccionMousePressed

    private void txtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusGained
        txtTelefono.selectAll();
    }//GEN-LAST:event_txtTelefonoFocusGained

    private void txtTelefonoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTelefonoMousePressed
        txtTelefono.selectAll();
    }//GEN-LAST:event_txtTelefonoMousePressed

    private void txtCorreoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusGained
        txtCorreo.selectAll();
    }//GEN-LAST:event_txtCorreoFocusGained

    private void txtCorreoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCorreoMousePressed
        txtCorreo.selectAll();
    }//GEN-LAST:event_txtCorreoMousePressed

    private void txtContratacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContratacionKeyTyped
        char key = evt.getKeyChar();
        String s = txtContratacion.getText() + key;
        if (s.length() > 13)
            evt.consume();
        else if (!(key >= 'a' && key <= 'z') && !(key >= 'A' && key <= 'Z') && !(key >= '0' && key <= '9'))
            evt.consume();
    }//GEN-LAST:event_txtContratacionKeyTyped

    private void txtRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFCKeyTyped
        char tecla = evt.getKeyChar();
        String s = txtRFC.getText() + tecla;
        if (s.length() > 50)
            evt.consume();
        else if (!(tecla >= 'a' && tecla <= 'z') && !(tecla >= 'A' && tecla <= 'Z') && tecla != ' ' && 
              tecla != 'á' && tecla != 'é' && tecla != 'í' && tecla != 'ó' && tecla != 'ú' &&
              tecla != 'Á' && tecla != 'É' && tecla != 'Í' && tecla != 'Ó' && tecla != 'Ú' &&
              tecla != 'ñ' && tecla != 'Ñ')
            evt.consume();
    }//GEN-LAST:event_txtRFCKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char tecla = evt.getKeyChar();
        String s = txtNombre.getText() + tecla;
        if (s.length() > 50)
            evt.consume();
        else if (!(tecla >= 'a' && tecla <= 'z') && !(tecla >= 'A' && tecla <= 'Z') && tecla != ' ' && 
              tecla != 'á' && tecla != 'é' && tecla != 'í' && tecla != 'ó' && tecla != 'ú' &&
              tecla != 'Á' && tecla != 'É' && tecla != 'Í' && tecla != 'Ó' && tecla != 'Ú' &&
              tecla != 'ñ' && tecla != 'Ñ')
            evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char tecla = evt.getKeyChar();
        String s = txtDireccion.getText() + tecla;
        if (s.length() > 100)
            evt.consume();
        else if (!(tecla >= 'a' && tecla <= 'z') && !(tecla >= 'A' && tecla <= 'Z') && tecla != ' ' && 
              tecla != 'á' && tecla != 'é' && tecla != 'í' && tecla != 'ó' && tecla != 'ú' &&
              tecla != 'Á' && tecla != 'É' && tecla != 'Í' && tecla != 'Ó' && tecla != 'Ú' &&
              !(tecla >= '0' && tecla <= '9') && tecla != ',' && tecla != '.' && tecla != '#')
            evt.consume();
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char tecla = evt.getKeyChar();
        String s = txtTelefono.getText() + tecla;
        if (s.length() > 10)
            evt.consume();
        if (!(tecla >= '0' && tecla <= '9'))
            evt.consume();
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped
        char key = evt.getKeyChar();
        String s = txtCorreo.getText() + key;
        if (s.length() > 30)
            evt.consume();
        else if (!(key >= 'a' && key <= 'z') && !(key >= 'A' && key <= 'Z') && !(key >= '0' && key <= '9')
                && key != '@' && key != '.' && key != '-' && key != '_' )
            evt.consume();
    }//GEN-LAST:event_txtCorreoKeyTyped

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
            java.util.logging.Logger.getLogger(FormularioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSubir;
    private javax.swing.JComboBox<String> cmbPuesto;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblUltimaModificacion;
    private javax.swing.JTextField txtContratacion;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEdad;
    private com.toedter.calendar.JDateChooser txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
