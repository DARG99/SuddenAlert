/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.*;
import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static javax.swing.text.html.HTML.Tag.I;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Catarina
 */
public class ListHorarios extends javax.swing.JFrame implements Serializable {

    private DefaultTableModel modeloTabela;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public static int id;
    public static String scan;

    /**
     * Creates new form Reclusos
     */
    public ListHorarios() {
        initComponents();
        jComboP.setBackground(Color.white);
        modeloTabela = (DefaultTableModel) jTable_hor.getModel();
        jTable_hor.setRowSorter(new TableRowSorter(modeloTabela));
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
        jTable_hor.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 12));
        jTable_hor.getTableHeader().setOpaque(false);
        jTable_hor.getTableHeader().setBackground(new Color(176, 2, 37));
        jTable_hor.getTableHeader().setForeground(new Color(255, 255, 255));
        jTable_hor.setRowHeight(24);
        guardaList();
        show_Guarda();
    }

    public ArrayList<Entidade> guardaList() {
        ArrayList<Entidade> guardasList = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            String query1 = "SELECT * FROM Profile WHERE id_type not like 3 AND deleted like 0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Entidade guarda;
            while (rs.next()) {
                guarda = new Entidade(rs.getString("scan"), rs.getInt("id_type"), rs.getString("name"), rs.getString("location"), rs.getInt("points"), rs.getString("birthday"), rs.getString("email"), rs.getInt("idSchedule"));
                guardasList.add(guarda);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return guardasList;
    }

     public void show_Guarda() {
        ArrayList<Entidade> list = guardaList();
        DefaultTableModel model = (DefaultTableModel) jTable_hor.getModel();
        Object[] row = new Object[3];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getNome();
            row[1] = list.get(i).getEmail();
            row[2] = list.get(i).getIdSchedule();
            
            
            model.addRow(row);
        }
    }

    private void pesquisar() {
        String sql = "SELECT name as 'Nome', email as 'Email', idSchedule as 'Número do Horário' FROM Profile where deleted='0' and (id_type='1' or id_type='2') and name like ?";
        String sqlemail = "SELECT name as 'Nome', email as 'Email', idSchedule as 'Número do Horário' FROM Profile where deleted='0' and (id_type='1' or id_type='2') and email like ?";
        String sqlnum = "SELECT name as 'Nome', email as 'Email', idSchedule as 'Número do Horário' FROM Profile where deleted='0' and (id_type='1' or id_type='2') and idSchedule like ?";
        String itemText = (String) jComboP.getSelectedItem();
        if ("Nome".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sql);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_hor.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if ("Email".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqlemail);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_hor.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
       if ("Número do Horário".equals(itemText)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
                String user = "suddenalertuser";
                String pass = "Suddenalert.0";
                Connection con = DriverManager.getConnection(url, user, pass);

                pst = con.prepareStatement(sqlnum);
                pst.setString(1, jTextField1.getText() + "%");
                rs = pst.executeQuery();
                jTable_hor.setModel(DbUtils.resultSetToTableModel(rs));
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
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_hor = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboP = new javax.swing.JComboBox<>();
        BackButton = new javax.swing.JButton();
        hor_guarda = new javax.swing.JButton();
        sidepane9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        home = new rsbuttom.RSButtonMetro();
        ent = new rsbuttom.RSButtonMetro();
        doc = new rsbuttom.RSButtonMetro();
        hor = new rsbuttom.RSButtonMetro();
        recl = new rsbuttom.RSButtonMetro();
        jPanel19 = new javax.swing.JPanel();
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
        label1.setText("Listagem de Horários");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/hor.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable_hor.setAutoCreateRowSorter(true);
        jTable_hor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Email", "Número do Horário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_hor.setSelectionBackground(new java.awt.Color(255, 102, 102));
        jTable_hor.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(jTable_hor);

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
        jLabel3.setText("jLabel3");

        jComboP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Email", "Número do Horário" }));
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

        hor_guarda.setBackground(new java.awt.Color(255, 255, 255));
        hor_guarda.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        hor_guarda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_horario.png"))); // NOI18N
        hor_guarda.setText("Ver mais");
        hor_guarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hor_guardaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(hor_guarda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addGap(149, 149, 149))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jComboP, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hor_guarda, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        sidepane9.setBackground(new java.awt.Color(243, 243, 243));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/f.png"))); // NOI18N

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

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(0, 3));
        jPanel19.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidepane9Layout.createSequentialGroup()
                .addGroup(sidepane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(home, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(recl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ajuda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addGap(114, 114, 114))
        );
        sidepane9Layout.setVerticalGroup(
            sidepane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepane9Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(hor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(doc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidepane9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void entMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_entMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(true);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_entMousePressed

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

    private void docMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_docMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(true);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_docMousePressed

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

    private void horMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_horMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(true);
        this.home.setSelected(false);
    }//GEN-LAST:event_horMousePressed

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

    private void reclMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reclMousePressed
        this.recl.setSelected(true);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(false);
    }//GEN-LAST:event_reclMousePressed

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

    private void hor_guardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hor_guardaActionPerformed
        int row = jTable_hor.getSelectedRow();
        if (row >= 0) {
            id = (int) jTable_hor.getValueAt(row, 2);
            if(id == 0){
                JOptionPane.showMessageDialog(null, "Não tem horário atribuido");
                return;
            }
            
            ArrayList<Entidade> List = guardaList();
            Entidade e = List.get(row);
            scan = e.getScan();
            
            String nome = e.getNome();
            String tipo = null;
            int IDtipo = e.getId_type();
            if (IDtipo == 1) {
            tipo = "Guarda";
                    }
            else {
                tipo = "Psicólogo";
            }
            Horario xHorario = new Horario();
            xHorario.tipox.setText(tipo);
            xHorario.nomex.setText(nome);
            xHorario.setLocationRelativeTo(null);
            xHorario.setVisible(true);
            this.dispose();
            
       }
        else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_hor_guardaActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        pesquisar();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

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
    private javax.swing.JButton hor_guarda;
    private javax.swing.JComboBox<String> jComboP;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_hor;
    private javax.swing.JTextField jTextField1;
    private java.awt.Label label1;
    private rsbuttom.RSButtonMetro recl;
    private javax.swing.JPanel sidepane9;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
