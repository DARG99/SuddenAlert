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
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.text.Normalizer.Form;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static javax.swing.text.html.HTML.Tag.I;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Catarina
 */
public class Horario extends javax.swing.JFrame implements Serializable {
    private DefaultTableModel modeloTabela;
    private int tipoHor;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String inicio, folga, almoco, fim;
    /**
     * Creates new form Reclusos
     */
    public Horario() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        setIcon();
        nomex.setEditable(false);
        tipox.setEditable(false);
        jTable_h.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD,15));
        jTable_h.getTableHeader().setOpaque(false);
        jTable_h.getTableHeader().setBackground(new Color(176,2,37));
        jTable_h.getTableHeader().setForeground(new Color(255,255,255));
        jTable_h.setRowHeight(61);
        show_Horario();
    }
     public void show_Horario(){
         
       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            
            
            tipoHor = ListHorarios.id;
            System.out.println(tipoHor);
            
                
                
            
            String query2 = "Select Entrada, Saida, Almoco, Folga from Schedule where idSchedule like '"+tipoHor+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(query2);
            while (rs2.next()) {
               inicio = rs2.getString("Entrada");
               fim = rs2.getString("Saida");
               almoco = rs2.getString("Almoco");
               folga = rs2.getString("Folga");
              }
            if(folga.equals("Segunda")){
            jTable_h.getModel().setValueAt("Folga", 0, 0);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            
            }
            else if (folga.equals("Terça")){
            jTable_h.getModel().setValueAt("Folga", 1, 0);
            
             jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            }
            else if (folga.equals("Quarta")){
            jTable_h.getModel().setValueAt("Folga", 2, 0);
            
            jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            }
            else if (folga.equals("Quinta")){
            jTable_h.getModel().setValueAt("Folga", 3, 0);
             jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            }
            else if (folga.equals("Sexta")){
            jTable_h.getModel().setValueAt("Folga", 4, 0);
             jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            }
            else if (folga.equals("Sabado")){
            jTable_h.getModel().setValueAt("Folga", 5, 0);
             jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 6, 0);
            jTable_h.getModel().setValueAt(fim, 6, 1);
            jTable_h.getModel().setValueAt(almoco, 6, 2);
            }
            else if(folga.equals("Domingo")){
            jTable_h.getModel().setValueAt("Folga", 6, 0);
            jTable_h.getModel().setValueAt(inicio, 0, 0);
            jTable_h.getModel().setValueAt(fim, 0, 1);
            jTable_h.getModel().setValueAt(almoco, 0, 2);
            
            jTable_h.getModel().setValueAt(inicio, 1, 0);
            jTable_h.getModel().setValueAt(fim, 1, 1);
            jTable_h.getModel().setValueAt(almoco, 1, 2);
            
            jTable_h.getModel().setValueAt(inicio, 2, 0);
            jTable_h.getModel().setValueAt(fim, 2, 1);
            jTable_h.getModel().setValueAt(almoco, 2, 2);
            
            jTable_h.getModel().setValueAt(inicio, 3, 0);
            jTable_h.getModel().setValueAt(fim, 3, 1);
            jTable_h.getModel().setValueAt(almoco, 3, 2);
            
            jTable_h.getModel().setValueAt(inicio, 4, 0);
            jTable_h.getModel().setValueAt(fim, 4, 1);
            jTable_h.getModel().setValueAt(almoco, 4, 2);
            
            jTable_h.getModel().setValueAt(inicio, 5, 0);
            jTable_h.getModel().setValueAt(fim, 5, 1);
            jTable_h.getModel().setValueAt(almoco, 5, 2);     
            }   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_h = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tipox = new javax.swing.JTextField();
        nomex = new javax.swing.JTextField();

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
        label1.setText("Horário");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/hor.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/alterar (2).png"))); // NOI18N
        jButton2.setText("Atualizar");
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

        jTable_h.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Entrada", "Saída", "Almoço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_h.setFocusable(false);
        jTable_h.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable_h.setRowHeight(61);
        jTable_h.setSelectionBackground(new java.awt.Color(243, 243, 243));
        jTable_h.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_h.setShowGrid(true);
        jTable_h.getTableHeader().setReorderingAllowed(false);
        jTable_h.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(jTable_h);

        jPanel2.setBackground(new java.awt.Color(176, 2, 37));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Segunda"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Quarta"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Terça"); // NOI18N
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Quinta"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sexta"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Sábado"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Domingo"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addGap(65, 65, 65))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tipo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Nome:");

        tipox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tipox.setText("tipo");
        tipox.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nomex.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomex.setText("nome");
        nomex.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tipox, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nomex, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tipox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nomex, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
        EditarHorario x = new EditarHorario();
        x.almoco.setText(almoco);
        x.entrada.setText(inicio);
        x.nome.setText(nomex.getText());
        x.tipo.setText(tipox.getText());
        x.saida.setText(fim);
        x.folga1.setText(folga);
        String s = String.valueOf(tipoHor); 
        x.HorAtual.setText(s);
        x.setLocationRelativeTo(null);
        x.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        ListHorarios xListHorarios = new ListHorarios();
        xListHorarios.setLocationRelativeTo(null);
        xListHorarios.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_h;
    private java.awt.Label label1;
    public javax.swing.JTextField nomex;
    public javax.swing.JTextField tipox;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
