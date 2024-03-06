/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static javax.swing.text.html.HTML.Tag.I;
import net.proteanit.sql.DbUtils;
import java.util.Date;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Catarina
 */
public class Historico extends javax.swing.JFrame implements Serializable {
    private DefaultTableModel modeloTabela;
    Connection con = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    
    /**
     * Creates new form Reclusos
     */
    public Historico(){
        initComponents();
        jComboP.setBackground(Color.white);
        DefaultTableModel modelo = (DefaultTableModel) jTable_h.getModel();
        jTable_h.setRowSorter(new TableRowSorter(modelo));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            java.sql.Connection con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        setIcon();
        jTable_h.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD,12));
        jTable_h.getTableHeader().setOpaque(false);
        jTable_h.getTableHeader().setBackground(new Color(176,2,37));
        jTable_h.getTableHeader().setForeground(new Color(255,255,255));
        show_historico();
    }
    
     public ArrayList<HistoricoBack> historicoList() {
        ArrayList<HistoricoBack> historicosList = new ArrayList<>();
        ArrayList<HistoricoBack> historicosLista = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            String query1 = "SELECT Profile.name, Historico.acao, Historico.data, Historico.motivo, Historico.tipo from Historico inner join Profile  on Historico.scan = Profile.scan WHERE Historico.deleted = '0';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            HistoricoBack historico;
            while (rs.next()) {
                historico = new HistoricoBack (rs.getString("name"), rs.getString("motivo"), rs.getString("acao"), rs.getString("data"), rs.getString("tipo"));
                historicosList.add(historico);
            }
            String query2 = "SELECT Recluse.name, Historico.acao, Historico.data, Historico.motivo, Historico.tipo from Historico inner join Recluse  on Historico.id_recluse = Recluse.numero_recluso WHERE Historico.deleted = '0';";
            Statement sta = con.createStatement();
            ResultSet rsa = sta.executeQuery(query2);
            HistoricoBack historico1;
            while (rsa.next()) {
                historico1 = new HistoricoBack (rsa.getString("name"), rsa.getString("motivo"), rsa.getString("acao"), rsa.getString("data"), rsa.getString("tipo"));
                historicosLista.add(historico1); }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        historicosList.addAll(historicosLista);
        Collections.sort(historicosList, new Comparator<HistoricoBack>() {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @Override
        public int compare(HistoricoBack o1, HistoricoBack o2) {
            try {
                return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    });
        return historicosList;
    }   
    public void show_historico() {
        ArrayList<HistoricoBack> list = historicoList();
        DefaultTableModel model = (DefaultTableModel) jTable_h.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getDate();
            row[1] = list.get(i).getAcao();
            row[2] = list.get(i).getNome();
            row[3] = list.get(i).getTipo();
            row[4] = list.get(i).getMotivo();
            model.addRow(row);
        }
    }
    
    public void EliminarRowTabela(String i) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "UPDATE Historico SET deleted='1' WHERE data='" + i + "'";
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) jTable_h.getModel();
            model.setRowCount(0);
            show_historico();
            JOptionPane.showMessageDialog(null,"Eliminado com Sucesso");
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
            
        } catch(Exception e) {
           JOptionPane.showMessageDialog(null, e); 
        }
    }
    
        private void pesquisar() {
        String sqldata = "Select Historico.data as 'Data', Historico.acao as 'Ação', Profile.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Profile on Historico.scan = Profile.scan where Historico.data like ?";
        String sqldata1 = "Select Historico.data as 'Data', Historico.acao as 'Ação', Recluse.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Recluse on Historico.id_recluse = Recluse.id_recluse where Historico.data like ?";
        String sqlacao = "Select Historico.data as 'Data', Historico.acao as 'Ação', Profile.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Profile on Historico.scan = Profile.scan where Historico.acao like ?";
        String sqlacao1 = "Select Historico.data as 'Data', Historico.acao as 'Ação', Recluse.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Recluse on Historico.id_recluse = Recluse.id_recluse where Historico.acao like ?";
        String sqlname = "Select Historico.data as 'Data', Historico.acao as 'Ação', Profile.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Profile on Historico.scan = Profile.scan where Profile.name like ?";
        String sqlname1 = "Select Historico.data as 'Data', Historico.acao as 'Ação', Recluse.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Recluse on Historico.id_recluse = Recluse.id_recluse where Recluse.name like ?";
        String sqltipo = "Select Historico.data as 'Data', Historico.acao as 'Ação', Profile.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Profile on Historico.scan = Profile.scan where Historico.tipo like ?";
        String sqltipo1 = "Select Historico.data as 'Data', Historico.acao as 'Ação', Recluse.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Recluse on Historico.id_recluse = Recluse.id_recluse where Historico.tipo like ?";
        String sqlmotivo = "Select Historico.data as 'Data', Historico.acao as 'Ação', Profile.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Profile on Historico.scan = Profile.scan where Historico.motivo like ?";
        String sqlmotivo1 = "Select Historico.data as 'Data', Historico.acao as 'Ação', Recluse.name as 'Nome', Historico.tipo as 'Tipo', Historico.motivo as 'Motivo' from Historico inner join Recluse on Historico.id_recluse = Recluse.id_recluse where Historico.motivo like ?";
        String itemText = (String) jComboP.getSelectedItem();
        if ("Data".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqldata);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs));
                /*pst1 = con.prepareStatement(sqldata1);
                pst1.setString(1, jTextField1.getText() + "%");
                rs1 = pst1.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs1));*/
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if ("Ação".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqlacao);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if ("Nome".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqlname);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if ("Tipo".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqltipo);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if ("Motivo".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqlmotivo);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_h.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
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
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboP = new javax.swing.JComboBox<>();
        BackButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_h = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        sidepane9 = new javax.swing.JPanel();
        ent = new rsbuttom.RSButtonMetro();
        doc = new rsbuttom.RSButtonMetro();
        hor = new rsbuttom.RSButtonMetro();
        recl = new rsbuttom.RSButtonMetro();
        jLabel39 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        home = new rsbuttom.RSButtonMetro();
        jPanel18 = new javax.swing.JPanel();
        ajuda = new rsbuttom.RSButtonMetro();

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
        label1.setText("Histórico");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextField1.setText("Pesquisar...");
        jTextField1.setInheritsPopupMenu(true);
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/pesquisar.png"))); // NOI18N

        jComboP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data", "Ação", "Nome", "Tipo", "Motivo" }));
        jComboP.setInheritsPopupMenu(true);
        jComboP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPActionPerformed(evt);
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

        jTable_h.setAutoCreateRowSorter(true);
        jTable_h.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Ação", "Nome ", "Tipo", "Motivo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
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
        jTable_h.setRowHeight(20);
        jTable_h.setSelectionBackground(new java.awt.Color(255, 102, 102));
        jTable_h.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_h.setShowGrid(true);
        jTable_h.getTableHeader().setReorderingAllowed(false);
        jTable_h.setVerifyInputWhenFocusTarget(false);
        jTable_h.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable_hKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_h);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/delete.png"))); // NOI18N
        jButton3.setText("Remover");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addGap(128, 128, 128))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jComboP, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 975, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/historico.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(118, 118, 118))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        sidepane9.setBackground(new java.awt.Color(243, 243, 243));

        ent.setForeground(new java.awt.Color(153, 153, 153));
        ent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_ent.png"))); // NOI18N
        ent.setText("        Entidades");
        ent.setColorTextNormal(new java.awt.Color(153, 153, 153));
        ent.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                entMousePressed(evt);
            }
        });
        ent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entActionPerformed(evt);
            }
        });

        doc.setForeground(new java.awt.Color(153, 153, 153));
        doc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_doc.png"))); // NOI18N
        doc.setText("         Relatórios");
        doc.setColorTextNormal(new java.awt.Color(153, 153, 153));
        doc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        doc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        doc.setInheritsPopupMenu(true);
        doc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                docMousePressed(evt);
            }
        });
        doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docActionPerformed(evt);
            }
        });

        hor.setForeground(new java.awt.Color(153, 153, 153));
        hor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_horario.png"))); // NOI18N
        hor.setText("       Horários");
        hor.setColorTextNormal(new java.awt.Color(153, 153, 153));
        hor.setHideActionText(true);
        hor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        hor.setIconTextGap(9);
        hor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                horMousePressed(evt);
            }
        });
        hor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horActionPerformed(evt);
            }
        });

        recl.setForeground(new java.awt.Color(153, 153, 153));
        recl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_recluso.png"))); // NOI18N
        recl.setText("        Reclusos");
        recl.setColorTextNormal(new java.awt.Color(153, 153, 153));
        recl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        recl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        recl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                reclMousePressed(evt);
            }
        });
        recl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reclActionPerformed(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/f.png"))); // NOI18N

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(0, 3));
        jPanel17.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_home.png"))); // NOI18N
        home.setText("         Menu Principal");
        home.setColorTextNormal(new java.awt.Color(153, 153, 153));
        home.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        home.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                homeMousePressed(evt);
            }
        });
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(0, 3));
        jPanel18.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        ajuda.setForeground(new java.awt.Color(153, 153, 153));
        ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_ajuda.png"))); // NOI18N
        ajuda.setText("        Ajuda");
        ajuda.setColorTextNormal(new java.awt.Color(153, 153, 153));
        ajuda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ajuda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ajuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ajudaMousePressed(evt);
            }
        });
        ajuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajudaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidepane9Layout = new javax.swing.GroupLayout(sidepane9);
        sidepane9.setLayout(sidepane9Layout);
        sidepane9Layout.setHorizontalGroup(
            sidepane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(hor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addComponent(recl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ajuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        sidepane9Layout.setVerticalGroup(
            sidepane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepane9Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(hor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(doc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidepane9, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidepane9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        Menu xMenu = new Menu();
        xMenu.setLocationRelativeTo(null);
        xMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
        Menu xMenu = new Menu();
        xMenu.setLocationRelativeTo(null);
        xMenu.setVisible(true);
        this.dispose();
        if (!this.home.isSelected()) {
            this.home.setColorNormal(new Color(255, 102, 102));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(255, 102, 102));

            this.recl.setColorNormal(new Color(243, 243, 243));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(243, 243, 243));

            /*this.lrecl.setColorNormal(new Color(243, 243, 243));
            this.lrecl.setColorHover(new Color(255, 102, 102));
            this.lrecl.setColorPressed(new Color(243, 243, 243));*/

            this.ent.setColorNormal(new Color(243, 243, 243));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(243, 243, 243));

            /*this.lent.setColorNormal(new Color(243, 243, 243));
            this.lent.setColorHover(new Color(255, 102, 102));
            this.lent.setColorPressed(new Color(243, 243, 243));*/

            this.doc.setColorNormal(new Color(243, 243, 243));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(243, 243, 243));

            this.hor.setColorNormal(new Color(243, 243, 243));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(243, 243, 243));
        } else {
            this.home.setColorNormal(new Color(243, 243, 243));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(243, 243, 243));
        }
    }//GEN-LAST:event_homeActionPerformed

    private void homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(true);
    }//GEN-LAST:event_homeMousePressed

    private void reclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reclActionPerformed
        Reclusos_popup xReclusos_popup = new Reclusos_popup();
        xReclusos_popup.setLocationRelativeTo(null);
        xReclusos_popup.setVisible(true);
        this.dispose();
        if (!this.recl.isSelected()) {
            this.home.setColorNormal(new Color(243, 243, 243));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(243, 243, 243));

            this.recl.setColorNormal(new Color(255, 102, 102));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(255, 102, 102));

            /*this.lrecl.setColorNormal(new Color(243, 243, 243));
            this.lrecl.setColorHover(new Color(255, 102, 102));
            this.lrecl.setColorPressed(new Color(243, 243, 243));*/

            this.ent.setColorNormal(new Color(243, 243, 243));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(243, 243, 243));

            /*this.lent.setColorNormal(new Color(243, 243, 243));
            this.lent.setColorHover(new Color(255, 102, 102));
            this.lent.setColorPressed(new Color(243, 243, 243));*/

            this.doc.setColorNormal(new Color(243, 243, 243));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(243, 243, 243));

            this.hor.setColorNormal(new Color(243, 243, 243));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(243, 243, 243));

        } else {
            this.recl.setColorNormal(new Color(243, 243, 243));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(243, 243, 243));
        }
    }//GEN-LAST:event_reclActionPerformed

    private void reclMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reclMousePressed
        this.recl.setSelected(true);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_reclMousePressed

    private void horActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horActionPerformed
        Horarios_popup xHorarios = new Horarios_popup();
        xHorarios.setLocationRelativeTo(null);
        xHorarios.setVisible(true);
        this.dispose();
        if (!this.hor.isSelected()) {
            this.home.setColorNormal(new Color(243, 243, 243));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(243, 243, 243));

            this.recl.setColorNormal(new Color(243, 243, 243));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(243, 243, 243));

            /*this.lrecl.setColorNormal(new Color(243, 243, 243));
            this.lrecl.setColorHover(new Color(255, 102, 102));
            this.lrecl.setColorPressed(new Color(243, 243, 243));*/

            this.ent.setColorNormal(new Color(243, 243, 243));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(243, 243, 243));

            /*this.lent.setColorNormal(new Color(243, 243, 243));
            this.lent.setColorHover(new Color(255, 102, 102));
            this.lent.setColorPressed(new Color(243, 243, 243));*/

            this.doc.setColorNormal(new Color(243, 243, 243));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(243, 243, 243));

            this.hor.setColorNormal(new Color(255, 102, 102));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(255, 102, 102));
        } else {
            this.hor.setColorNormal(new Color(243, 243, 243));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(243, 243, 243));
        }
    }//GEN-LAST:event_horActionPerformed

    private void horMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_horMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(true);
        this.home.setSelected(false);
    }//GEN-LAST:event_horMousePressed

    private void docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docActionPerformed
        Relatorios_popup xRelatorios_popup = new Relatorios_popup();
        xRelatorios_popup.setLocationRelativeTo(null);
        xRelatorios_popup.setVisible(true);
        this.dispose();
        if (!this.doc.isSelected()) {
            this.home.setColorNormal(new Color(243, 243, 243));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(243, 243, 243));

            this.recl.setColorNormal(new Color(243, 243, 243));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(243, 243, 243));

            /*this.lrecl.setColorNormal(new Color(243, 243, 243));
            this.lrecl.setColorHover(new Color(255, 102, 102));
            this.lrecl.setColorPressed(new Color(243, 243, 243));*/

            this.ent.setColorNormal(new Color(243, 243, 243));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(243, 243, 243));

            /*this.lent.setColorNormal(new Color(243, 243, 243));
            this.lent.setColorHover(new Color(255, 102, 102));
            this.lent.setColorPressed(new Color(243, 243, 243));*/

            this.doc.setColorNormal(new Color(255, 102, 102));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(255, 102, 102));

            this.hor.setColorNormal(new Color(243, 243, 243));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(243, 243, 243));
        } else {
            this.doc.setColorNormal(new Color(243, 243, 243));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(243, 243, 243));
        }
    }//GEN-LAST:event_docActionPerformed

    private void docMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_docMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(true);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_docMousePressed

    private void entActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entActionPerformed
        Entidades_popup xEntidades_popup = new Entidades_popup();
        xEntidades_popup.setLocationRelativeTo(null);
        xEntidades_popup.setVisible(true);
        this.dispose();
        if (!this.ent.isSelected()) {
            this.home.setColorNormal(new Color(243, 243, 243));
            this.home.setColorHover(new Color(255, 102, 102));
            this.home.setColorPressed(new Color(243, 243, 243));

            this.recl.setColorNormal(new Color(243, 243, 243));
            this.recl.setColorHover(new Color(255, 102, 102));
            this.recl.setColorPressed(new Color(243, 243, 243));

            /*this.lrecl.setColorNormal(new Color(243, 243, 243));
            this.lrecl.setColorHover(new Color(255, 102, 102));
            this.lrecl.setColorPressed(new Color(243, 243, 243));*/

            this.ent.setColorNormal(new Color(255, 102, 102));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(255, 102, 102));

            /*this.lent.setColorNormal(new Color(243, 243, 243));
            this.lent.setColorHover(new Color(255, 102, 102));
            this.lent.setColorPressed(new Color(243, 243, 243));*/

            this.doc.setColorNormal(new Color(243, 243, 243));
            this.doc.setColorHover(new Color(255, 102, 102));
            this.doc.setColorPressed(new Color(243, 243, 243));

            this.hor.setColorNormal(new Color(243, 243, 243));
            this.hor.setColorHover(new Color(255, 102, 102));
            this.hor.setColorPressed(new Color(243, 243, 243));
        } else {
            this.ent.setColorNormal(new Color(243, 243, 243));
            this.ent.setColorHover(new Color(255, 102, 102));
            this.ent.setColorPressed(new Color(243, 243, 243));
        }
    }//GEN-LAST:event_entActionPerformed

    private void entMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_entMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(true);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_entMousePressed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTable_hKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_hKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_hKeyReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        pesquisar();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      int row_selecionada = jTable_h.getSelectedRow();
      if (row_selecionada >= 0) {  
        ArrayList<HistoricoBack> lista = historicoList();
        String i = lista.get(row_selecionada).getDate();
        EliminarRowTabela(i);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ajudaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ajudaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ajudaMousePressed

    private void ajudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajudaActionPerformed
        Ajuda xAjuda = new Ajuda();
        xAjuda.setLocationRelativeTo(null);
        xAjuda.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ajudaActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private rsbuttom.RSButtonMetro ajuda;
    private rsbuttom.RSButtonMetro doc;
    private rsbuttom.RSButtonMetro ent;
    private rsbuttom.RSButtonMetro home;
    private rsbuttom.RSButtonMetro hor;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboP;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_h;
    private javax.swing.JTextField jTextField1;
    private java.awt.Label label1;
    private rsbuttom.RSButtonMetro recl;
    private javax.swing.JPanel sidepane9;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
