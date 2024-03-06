/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer.Form;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.html.HTML.Tag.I;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Catarina
 */
public class EditarHorario extends javax.swing.JFrame implements Serializable {
    private DefaultTableModel modeloTabela;
     private String scan1; 
    /**
     * Creates new form Reclusos
     */
    public EditarHorario() {
        initComponents();
        setIcon();
        jComboBoxNumero.setBackground(Color.white);
        nome.setEditable(false);
        tipo.setEditable(false);
        entrada.setEditable(false);
        saida.setEditable(false);
        almoco.setEditable(false);
        folga1.setEditable(false);
        HorAtual.setEditable(false);
        Combo();
        int a = ListHorarios.id;
        Object b = (int) a;
        jComboBoxNumero.setSelectedItem(b);
        
    }
    
    public void Combo() {
            try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "SELECT idSchedule FROM Schedule";
            PreparedStatement pst = con.prepareStatement(query);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                jComboBoxNumero.addItem(rs.getString("idSchedule")); 
            }
             } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
    
          public void AtualizarCampos() {
            try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            Object idHorario = jComboBoxNumero.getSelectedItem();
            String n = (String) idHorario;           
            String query2 = "Select Entrada, Saida, Almoco, Folga from Schedule where idSchedule like '"+n+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(query2);
            while (rs2.next()) {
               entrada.setText(rs2.getString("Entrada"));
               saida.setText(rs2.getString("Saida"));
               almoco.setText(rs2.getString("Almoco"));
               folga1.setText(rs2.getString("Folga"));
              }
   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
 }
     public void Editar(){
try{
            scan1 = ListHorarios.scan;
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            Object idHor = jComboBoxNumero.getSelectedItem();
            String i = (String) idHor;
            String query = "UPDATE Profile SET idSchedule='"+i+"' where scan like '"+scan1+"'";
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();         
                   try{
        ProgressBar xProgress = new ProgressBar();
        xProgress.setLocationRelativeTo(null);
        xProgress.setVisible(true);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int num = 1; num<=100; num++) {
                    try {
                        xProgress.jp_progress.UpdateProgress(num);
                        xProgress.jp_progress.repaint();
                        Thread.sleep(7);
                        
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(RegistEnt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        }catch(Exception err){
            JOptionPane.showMessageDialog(null,"Ocorreu um erro","Aviso",JOptionPane.ERROR_MESSAGE);
        }
    }                                        
     catch(ClassNotFoundException | SQLException ed) {
           JOptionPane.showMessageDialog(null, "Ocorreu um erro","Aviso",JOptionPane.ERROR_MESSAGE);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tipo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        entrada = new javax.swing.JFormattedTextField();
        saida = new javax.swing.JFormattedTextField();
        almoco = new javax.swing.JFormattedTextField();
        jComboBoxNumero = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        folga1 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        HorAtual = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudden Alert");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Tw Cen MT", 1, 38)); // NOI18N
        label1.setForeground(new java.awt.Color(204, 204, 204));
        label1.setText("Atualizar Horário");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/hor.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/save.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        BackButton.setBackground(new java.awt.Color(176, 2, 37));
        BackButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BackButton.setForeground(new java.awt.Color(255, 255, 255));
        BackButton.setText("Cancelar");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(241, 241, 241));
        jPanel4.setForeground(new java.awt.Color(212, 13, 19));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setText("Tipo:");

        tipo.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tipoMouseClicked(evt);
            }
        });
        tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel11.setText("Associado a:");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        jLabel12.setText("Horário:");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel14.setText("Hora de saída:");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel15.setText("Hora de entrada:");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel16.setText("Hora de almoço:");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel17.setText("Folga:");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel13.setText("Nome:");

        nome.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        nome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nomeMouseClicked(evt);
            }
        });
        nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeActionPerformed(evt);
            }
        });

        entrada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        entrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        entrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        entrada.setSelectionColor(new java.awt.Color(255, 255, 255));
        entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                entradaMouseClicked(evt);
            }
        });
        entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradaActionPerformed(evt);
            }
        });

        saida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        saida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        saida.setSelectionColor(new java.awt.Color(255, 255, 255));
        saida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saidaMouseClicked(evt);
            }
        });
        saida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saidaActionPerformed(evt);
            }
        });

        almoco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        almoco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        almoco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        almoco.setSelectionColor(new java.awt.Color(255, 255, 255));
        almoco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                almocoMouseClicked(evt);
            }
        });

        jComboBoxNumero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxNumeroMouseClicked(evt);
            }
        });
        jComboBoxNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNumeroActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setText("Número de Horário:");

        folga1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        folga1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        folga1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        folga1.setSelectionColor(new java.awt.Color(255, 255, 255));
        folga1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                folga1MouseClicked(evt);
            }
        });
        folga1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folga1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel18.setText("Horário Atual:");

        HorAtual.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        HorAtual.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        HorAtual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HorAtualMouseClicked(evt);
            }
        });
        HorAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HorAtualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel18))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipo, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(nome)
                            .addComponent(HorAtual)))
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saida, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(almoco, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(folga1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBoxNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(HorAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saida, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(almoco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(folga1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BackButton))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Editar();
        ListHorarios lh = new ListHorarios(); 
        lh.setLocationRelativeTo(null);
        lh.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        ListHorarios lh = new ListHorarios();
        lh.setLocationRelativeTo(null);
        lh.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void tipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tipoMouseClicked
        
    }//GEN-LAST:event_tipoMouseClicked

    private void tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoActionPerformed

    private void nomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeMouseClicked

    private void nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeActionPerformed

    private void entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_entradaMouseClicked
       entrada.setDocument(new TeclasPermitHora());
        
    }//GEN-LAST:event_entradaMouseClicked

    private void saidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saidaMouseClicked
        
    }//GEN-LAST:event_saidaMouseClicked

    private void almocoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_almocoMouseClicked
        
    }//GEN-LAST:event_almocoMouseClicked

    private void saidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saidaActionPerformed

    private void jComboBoxNumeroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxNumeroMouseClicked

    }//GEN-LAST:event_jComboBoxNumeroMouseClicked

    private void jComboBoxNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNumeroActionPerformed
     AtualizarCampos();
    }//GEN-LAST:event_jComboBoxNumeroActionPerformed

    private void folga1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folga1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_folga1MouseClicked

    private void HorAtualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HorAtualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_HorAtualMouseClicked

    private void HorAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HorAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HorAtualActionPerformed

    private void entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entradaActionPerformed

    private void folga1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folga1ActionPerformed
        
    }//GEN-LAST:event_folga1ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    public javax.swing.JTextField HorAtual;
    public javax.swing.JFormattedTextField almoco;
    public javax.swing.JFormattedTextField entrada;
    public javax.swing.JFormattedTextField folga1;
    private javax.swing.JButton jButton2;
    public javax.swing.JComboBox<String> jComboBoxNumero;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private java.awt.Label label1;
    public javax.swing.JTextField nome;
    public javax.swing.JFormattedTextField saida;
    public javax.swing.JTextField tipo;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
