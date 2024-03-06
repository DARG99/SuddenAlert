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
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.html.HTML.Tag.I;
import java.text.SimpleDateFormat;  
import java.util.Calendar;
import java.util.Date;  
import javax.swing.JFileChooser;
 
/**
 *
 * @author Catarina
 */
public class RegistR extends javax.swing.JFrame implements Serializable {

    private DefaultTableModel modeloTabela;
    String filename = null;
    byte[] imagem = null;
    int valor = 0;
    Date nascimentoRec, entradaRec;

    /**
     * Creates new form Reclusos
     */
    public RegistR()  {
        initComponents();
        setIcon();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamTela = kit.getScreenSize();

//Pega largura e altura da tela 
        int larg = tamTela.width;
        int alt = tamTela.height;
        setSize(larg, alt);
 
        
    }
     private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        }
        else
        {
            if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageInt;
    }
    
    public void RegistarRecluso(String numero_recluso, String name, String birthday, String date_entry, String wing, String floor, String disease, byte[] imagem){
try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            String query2 = "SELECT COUNT(1) FROM Recluse WHERE numero_recluso like'" + numero_recluso + "'";
                Statement statement2 = con.createStatement();
                ResultSet resultSet = statement2.executeQuery(query2);
                while (resultSet.next()) {
                    valor = resultSet.getInt("COUNT(1)");
                }
            if(valor != 0 ){
                JOptionPane.showMessageDialog(null,"Já existe um recluso com este número","Aviso",JOptionPane.WARNING_MESSAGE);
                return;
            }
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
                        registar_nome.setText("");
                        registar_numero.setText("");
                        registar_ala.setText("");
                        registar_piso.setText("");
                        registar_data_nascimento.setText("");
                        registar_data_entrada.setText("");
                        registar_doenças.setText("");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RegistR.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        }catch(Exception err){
            JOptionPane.showMessageDialog(null,"Ocorreu um erro","Aviso",JOptionPane.ERROR_MESSAGE);
        }
            String query = "Insert into Recluse(numero_recluso, name, birthday, date_entry, wing, floor, disease,imagem)values(?,?,?,?,?,?,?,?)";          
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, numero_recluso);
            pst.setString(2, name);
            pst.setString(3, birthday);
            pst.setString(4, date_entry);
            pst.setString(5, wing);
            pst.setString(6, floor);
            pst.setString(7, disease);
            pst.setBytes(8, imagem);
            pst.executeUpdate();
            String query1 = "Insert into Historico (acao, motivo, id_recluse, tipo) values ('Inserção', '', '"+numero_recluso+"', 'Recluso')";          
            PreparedStatement psta = con.prepareStatement(query1);
            psta.executeUpdate();
            //JOptionPane.showMessageDialog(null,"Recluso registado com Sucesso");
    }                                        
     catch(Exception e) {
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
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        botao_photo = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        registar_numero = new javax.swing.JTextField();
        registar_nome = new javax.swing.JTextField();
        registar_piso = new javax.swing.JTextField();
        registar_ala = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        registar_doenças = new javax.swing.JTextField();
        registar_data_nascimento = new javax.swing.JFormattedTextField();
        registar_data_entrada = new javax.swing.JFormattedTextField();
        BackButton1 = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jLabel2 = new javax.swing.JLabel();
        sidepane4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        home = new rsbuttom.RSButtonMetro();
        ent = new rsbuttom.RSButtonMetro();
        doc = new rsbuttom.RSButtonMetro();
        hor = new rsbuttom.RSButtonMetro();
        recl = new rsbuttom.RSButtonMetro();
        jPanel17 = new javax.swing.JPanel();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/guardar.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        botao_photo.setBackground(new java.awt.Color(241, 241, 241));
        botao_photo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/fotoR.png"))); // NOI18N
        botao_photo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_photoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("Upload da fotografia");

        registar_numero.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        registar_numero.setText("Insira aqui o número");
        registar_numero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_numeroMouseClicked(evt);
            }
        });
        registar_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_numeroActionPerformed(evt);
            }
        });

        registar_nome.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        registar_nome.setText("Insira aqui o nome");
        registar_nome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_nomeMouseClicked(evt);
            }
        });
        registar_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_nomeActionPerformed(evt);
            }
        });

        registar_piso.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        registar_piso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        registar_piso.setText("Insira aqui o piso");
        registar_piso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_pisoMouseClicked(evt);
            }
        });
        registar_piso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_pisoActionPerformed(evt);
            }
        });

        registar_ala.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        registar_ala.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        registar_ala.setText("Insira aqui a ala");
        registar_ala.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_alaMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setText("Ala:");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setText("Data de entrada:");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setText("Data de nascimento:");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setText("Piso:");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setText("Número:");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setText("Nome:");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel9.setText("Doenças:");

        registar_doenças.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        registar_doenças.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        registar_doenças.setText("Doenças");
        registar_doenças.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_doençasMouseClicked(evt);
            }
        });
        registar_doenças.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_doençasActionPerformed(evt);
            }
        });

        registar_data_nascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("y/MM/dd"))));
        registar_data_nascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        registar_data_nascimento.setText("Ano/Mês/Dia");
        registar_data_nascimento.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        registar_data_nascimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_data_nascimentoMouseClicked(evt);
            }
        });
        registar_data_nascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_data_nascimentoActionPerformed(evt);
            }
        });

        registar_data_entrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("y/MM/dd"))));
        registar_data_entrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        registar_data_entrada.setText("Ano/Mês/Dia");
        registar_data_entrada.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        registar_data_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registar_data_entradaMouseClicked(evt);
            }
        });
        registar_data_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registar_data_entradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(registar_data_nascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(registar_data_entrada))
                                .addGap(46, 46, 46))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(registar_ala, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(registar_piso, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(registar_nome)
                                    .addComponent(registar_numero, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(25, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(registar_doenças, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(registar_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registar_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(registar_ala, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(registar_piso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(registar_data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(registar_data_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registar_doenças, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(36, 36, 36))
        );

        BackButton1.setBackground(new java.awt.Color(176, 2, 37));
        BackButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BackButton1.setForeground(new java.awt.Color(255, 255, 255));
        BackButton1.setText("Cancelar");
        BackButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(BackButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(134, 134, 134))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(botao_photo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(botao_photo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(207, 207, 207)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BackButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Tw Cen MT", 1, 38)); // NOI18N
        label1.setForeground(new java.awt.Color(204, 204, 204));
        label1.setText("Registo de Reclusos");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/recl.jpg"))); // NOI18N

        sidepane4.setBackground(new java.awt.Color(243, 243, 243));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/f.png"))); // NOI18N

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

        javax.swing.GroupLayout sidepane4Layout = new javax.swing.GroupLayout(sidepane4);
        sidepane4.setLayout(sidepane4Layout);
        sidepane4Layout.setHorizontalGroup(
            sidepane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addComponent(doc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(hor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addComponent(recl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ajuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        sidepane4Layout.setVerticalGroup(
            sidepane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidepane4Layout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
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
                .addGap(78, 78, 78)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ajuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sidepane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 442, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(61, 61, 61))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(sidepane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        if(registar_nome.getText().equals("") || registar_nome.getText().equals("Insira aqui o nome")){
            registar_nome.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Nome é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(registar_numero.getText().equals("") || registar_numero.getText().equals("Insira aqui o número")){
            registar_numero.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Número é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(registar_ala.getText().equals("") || registar_ala.getText().equals("Insira aqui a ala")){
            registar_ala.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Ala é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(registar_piso.getText().equals("") || registar_piso.getText().equals("Insira aqui o piso")){
            registar_piso.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Piso é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(registar_data_nascimento.getText().equals("") || registar_data_nascimento.getText().equals("Ano/Mês/Dia")){
            registar_data_nascimento.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Data de Nascimento é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(registar_data_entrada.getText().equals("") || registar_data_entrada.getText().equals("Ano/Mês/Dia")){
            registar_data_entrada.requestFocus();
            JOptionPane.showMessageDialog(null,"O campo Data de Entrega é obrigatório","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (imagem == null  || imagem.length == 0){
            registar_data_entrada.requestFocus();
            JOptionPane.showMessageDialog(null,"Por favor insira uma imagem","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String dn = registar_data_nascimento.getText();
        String de = registar_data_entrada.getText();
        SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
        try {
             nascimentoRec = dates.parse(dn);
             entradaRec = dates.parse(de);
             
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Formato de data invalido", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        int ano = nascimentoRec.getYear()+1900;
        int mes = nascimentoRec.getMonth();
        int dia = nascimentoRec.getDay();
        if (getAge(ano, mes, dia)<18){
            JOptionPane.showMessageDialog(null, "Recluso menor que 18 anos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
            
        }else{
            int anoE = entradaRec.getYear()+1900;
            int mesE = entradaRec.getMonth();
            int diaE = entradaRec.getDay();
            int diff =getAge(anoE, mesE, diaE)- getAge(ano, mes, dia);
            if(diff > 0){
                JOptionPane.showMessageDialog(null, "Data de entrada menor que data de nascimento", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
        }
        
        String s = registar_numero.getText();
        String no = registar_nome.getText();
        
        
        String a = registar_ala.getText();
        String p = registar_piso.getText();
        String d = registar_doenças.getText();
        String photo = filename;
        
        RegistarRecluso(s, no, dn, de, a, p, d, imagem);
        /*ListReclusos xListReclusos = new ListReclusos();
        xListReclusos.setLocationRelativeTo(null);
        xListReclusos.setVisible(true);
        this.dispose();*/
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void registar_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_numeroActionPerformed
        
    }//GEN-LAST:event_registar_numeroActionPerformed

    private void registar_pisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_pisoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registar_pisoActionPerformed

    private void registar_doençasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_doençasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registar_doençasActionPerformed

    private void homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMousePressed
        this.recl.setSelected(false);
        //this.lrecl.setSelected(false);
        this.ent.setSelected(false);
        //this.lent.setSelected(false);
        this.doc.setSelected(false);
        this.hor.setSelected(false);
        this.home.setSelected(true);
    }//GEN-LAST:event_homeMousePressed

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

    private void registar_data_nascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_data_nascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registar_data_nascimentoActionPerformed

    private void registar_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_nomeActionPerformed
        
    }//GEN-LAST:event_registar_nomeActionPerformed

    private void registar_nomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_nomeMouseClicked
        registar_nome.setText("");
        //jTextField3.setDocument(new TeclasPermitNome());
    }//GEN-LAST:event_registar_nomeMouseClicked

    private void registar_numeroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_numeroMouseClicked
        registar_numero.setText("");
        registar_numero.setDocument(new TeclasPermitNum());
    }//GEN-LAST:event_registar_numeroMouseClicked

    private void registar_alaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_alaMouseClicked
        registar_ala.setText("");
        registar_ala.setDocument(new TeclasPermitLetra());
    }//GEN-LAST:event_registar_alaMouseClicked

    private void registar_pisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_pisoMouseClicked
        registar_piso.setText("");
        registar_piso.setDocument(new TeclasPermitNum());
    }//GEN-LAST:event_registar_pisoMouseClicked

    private void registar_data_nascimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_data_nascimentoMouseClicked
        registar_data_nascimento.setText("");
    }//GEN-LAST:event_registar_data_nascimentoMouseClicked

    private void registar_data_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_data_entradaMouseClicked
        registar_data_entrada.setText("");
    }//GEN-LAST:event_registar_data_entradaMouseClicked

    private void registar_doençasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registar_doençasMouseClicked
        registar_doenças.setText("");
    }//GEN-LAST:event_registar_doençasMouseClicked

    private void registar_data_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registar_data_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registar_data_entradaActionPerformed

    private void botao_photoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_photoActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath(); 
        ImageIcon imagems = new ImageIcon(filename);
        Image im = imagems.getImage();
        Image myImg = im.getScaledInstance(botao_photo.getWidth(),botao_photo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImage = new ImageIcon(myImg);
        botao_photo.setIcon(newImage);
        
        try {
            File image = new File (filename);
            FileInputStream fis = new FileInputStream (image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf))!= -1;){
                bos.write(buf, 0,readNum);
            }
            imagem = bos.toByteArray();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botao_photoActionPerformed

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

    private void BackButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButton1ActionPerformed
        Menu xMenu = new Menu();
        xMenu.setLocationRelativeTo(null);
        xMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButton1ActionPerformed

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
    private javax.swing.JButton BackButton1;
    private rsbuttom.RSButtonMetro ajuda;
    private javax.swing.JButton botao_photo;
    private rsbuttom.RSButtonMetro doc;
    private rsbuttom.RSButtonMetro ent;
    private rsbuttom.RSButtonMetro home;
    private rsbuttom.RSButtonMetro hor;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private java.awt.Label label1;
    private rsbuttom.RSButtonMetro recl;
    private javax.swing.JTextField registar_ala;
    private javax.swing.JFormattedTextField registar_data_entrada;
    private javax.swing.JFormattedTextField registar_data_nascimento;
    private javax.swing.JTextField registar_doenças;
    private javax.swing.JTextField registar_nome;
    private javax.swing.JTextField registar_numero;
    private javax.swing.JTextField registar_piso;
    private javax.swing.JPanel sidepane4;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
