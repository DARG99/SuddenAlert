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
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author Catarina
 */
public class RegistEnt extends javax.swing.JFrame implements Serializable {

    private DefaultTableModel modeloTabela;
    int valor = 0;
    ArrayList<String> emailV = new ArrayList<>();
    private String e;

    /**
     * Creates new form Reclusos
     */
    public RegistEnt() {
        initComponents();
        setIcon();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamTela = kit.getScreenSize();

//Pega largura e altura da tela 
        int larg = tamTela.width;
        int alt = tamTela.height;
        setSize(larg, alt);
        /*ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("delete.png"))); 
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(jButton1.getWidth(), jButton1.getWidth(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        jButton1.setIcon(i);*/
    }
    public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
    

    public void RegistarEntidade(String scan, int id_type, String nome, String location, int points, String dataNascimento, String email) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            Connection con = DriverManager.getConnection(url, user, pass);
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro com a conexão", "Aviso", JOptionPane.ERROR_MESSAGE);
            } else {
                String query1 = "SELECT COUNT(1) FROM Profile WHERE scan like'" + scan + "'";
                Statement statement1 = con.createStatement();
                ResultSet resultSet = statement1.executeQuery(query1);
                while (resultSet.next()) {
                    valor = resultSet.getInt("COUNT(1)");
                }
                if(valor != 0){
                JOptionPane.showMessageDialog(null, "Scan já usado por outra Entidade", "Aviso", JOptionPane.ERROR_MESSAGE);
                return;
                }
                String query4="Select email FROM Profile";
                Statement statement4 = con.createStatement();
                ResultSet resultSet4 = statement4.executeQuery(query4);
                while (resultSet4.next()){
                    emailV.add(resultSet4.getString("email"));
                }
                for(int counter = 0; counter < emailV.size(); counter++){
                    
                    if(e.equals(emailV.get(counter))){
                        JOptionPane.showMessageDialog(null, "Este email já existe", "Aviso", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                if (valor == 0) {
                    String query = "Insert into Profile(scan, id_type, name, location, points, birthday, email)values(?,?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, scan);
                    pst.setInt(2, id_type);
                    pst.setString(3, nome);
                    pst.setString(4, location);
                    pst.setInt(5, points);
                    pst.setString(6, dataNascimento);
                    pst.setString(7, email);
                    pst.executeUpdate();
                    if (id_type == 1) {
                        String query2 = "Insert into Historico (acao, motivo, scan, tipo) values ('Inserção', '', '" + scan + "', 'Guarda')";
                        PreparedStatement psta = con.prepareStatement(query2);
                        psta.executeUpdate();
                    } else {
                        String query3 = "Insert into Historico (acao, motivo, scan, tipo) values ('Inserção', '', '" + scan + "', 'Psicólogo')";
                        PreparedStatement psta = con.prepareStatement(query3);
                        psta.executeUpdate();
                    }
                    try {
                        ProgressBar xProgress = new ProgressBar();
                        xProgress.setLocationRelativeTo(null);
                        xProgress.setVisible(true);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int num = 1; num <= 100; num++) {
                                    try {
                                        xProgress.jp_progress.UpdateProgress(num);
                                        xProgress.jp_progress.repaint();
                                        Thread.sleep(7);
                                        scan1.setText("");
                                        nome1.setText("");
                                        data_nascimento.setText("");
                                        email1.setText("");
                                        buttonGroup1.clearSelection();
                                        latitude.setText("");
                                        longitude.setText("");
                                    } catch (InterruptedException ex) {
                                        java.util.logging.Logger.getLogger(RegistEnt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }).start();
                    } catch (Exception err) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro", "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } 
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        nome1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Psicólogo = new javax.swing.JRadioButton();
        Guarda = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        data_nascimento = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        email1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        latitude = new javax.swing.JTextField();
        longitude = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        scan1 = new javax.swing.JTextField();
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

        jPanel4.setBackground(new java.awt.Color(241, 241, 241));
        jPanel4.setForeground(new java.awt.Color(212, 13, 19));

        nome1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        nome1.setText("Insira aqui o nome");
        nome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nome1MouseClicked(evt);
            }
        });
        nome1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nome1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setText("Data de nascimento:");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setText("Tipo:");

        Psicólogo.setBackground(new java.awt.Color(241, 241, 241));
        buttonGroup1.add(Psicólogo);
        Psicólogo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Psicólogo.setForeground(new java.awt.Color(153, 0, 0));
        Psicólogo.setText("Psicólogo");
        Psicólogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PsicólogoActionPerformed(evt);
            }
        });

        Guarda.setBackground(new java.awt.Color(241, 241, 241));
        buttonGroup1.add(Guarda);
        Guarda.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Guarda.setForeground(new java.awt.Color(153, 0, 0));
        Guarda.setText("Guarda");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setText("Nome:");

        data_nascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("y/MM/dd"))));
        data_nascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        data_nascimento.setText("Ano/Mês/Dia");
        data_nascimento.setToolTipText("");
        data_nascimento.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        data_nascimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_nascimentoMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setText("Email:");

        email1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        email1.setText("Insira aqui o email");
        email1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                email1MouseClicked(evt);
            }
        });
        email1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setText("Localização:");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("Latitude:");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Longitude:");

        latitude.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        latitude.setText("Insira aqui a latitude");
        latitude.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                latitudeMouseClicked(evt);
            }
        });
        latitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latitudeActionPerformed(evt);
            }
        });

        longitude.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        longitude.setText("Insira aqui a longitude");
        longitude.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                longitudeMouseClicked(evt);
            }
        });
        longitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                longitudeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(latitude)
                    .addComponent(longitude, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(latitude, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(longitude, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ic_localizacao.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setText("Scan");

        scan1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        scan1.setText("Insira aqui o valor do Scan");
        scan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scan1MouseClicked(evt);
            }
        });
        scan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scan1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scan1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(email1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nome1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Psicólogo)
                            .addComponent(Guarda, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nome1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(email1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(Psicólogo)
                                .addGap(18, 18, 18)
                                .addComponent(Guarda))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scan1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(222, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(BackButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Tw Cen MT", 1, 38)); // NOI18N
        label1.setForeground(new java.awt.Color(204, 204, 204));
        label1.setText("Registo de Entidade");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagens/ent.png"))); // NOI18N

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
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
            .addComponent(doc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(hor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
            .addComponent(recl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ajuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
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
                .addGap(71, 71, 71)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(63, 63, 63))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
        if (nome1.getText().equals("") || nome1.getText().equals("Insira aqui o nome")) {
            nome1.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Nome é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (data_nascimento.getText().equals("") || data_nascimento.getText().equals("Ano/Mês/Dia")) {
            data_nascimento.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Data de Nascimento é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (email1.getText().equals("") || email1.getText().equals("Insira aqui o email")) {
            email1.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Email é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        e = email1.getText();
       if(!isValid(e)){
       JOptionPane.showMessageDialog(null, "O Email não é válido", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
       }
        if (latitude.getText().equals("") || latitude.getText().equals("Insira aqui a latitude")) {
            latitude.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Latitude é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (longitude.getText().equals("") || longitude.getText().equals("Insira aqui a longitude")) {
            longitude.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Longitude é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (scan1.getText().equals("") || scan1.getText().equals("Insira aqui o valor do Scan")) {
            scan1.requestFocus();
            JOptionPane.showMessageDialog(null, "O campo Scan é obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String s = scan1.getText();
        String no = nome1.getText();
        String dn = data_nascimento.getText();
        int id_type = 0;
        if (Psicólogo.isSelected()) {
            id_type = 2;
        }
        if (Guarda.isSelected()) {
            id_type = 1;
        }
        String la = latitude.getText();
        String lo = longitude.getText();
        String location = la + "," + lo;
        
        int p = 0;

        if (Psicólogo.isSelected()) {
        RegistarEntidade(s, id_type, no, location, p, dn, e);
        ListPsicologos x = new ListPsicologos();
        x.setLocationRelativeTo(null);
        x.setVisible(true);
        this.dispose();
        }
        if (Guarda.isSelected()) {
        RegistarEntidade(s, id_type, no, location, p, dn, e);
        ListGuardas y = new ListGuardas();
        y.setLocationRelativeTo(null);
        y.setVisible(true);
        this.dispose();
        } 
        else {
            JOptionPane.showMessageDialog(null, "Por favor selecione um tipo", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void PsicólogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PsicólogoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PsicólogoActionPerformed

    private void nome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nome1MouseClicked
        nome1.setText("");
    }//GEN-LAST:event_nome1MouseClicked

    private void data_nascimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_nascimentoMouseClicked
        data_nascimento.setText("");
    }//GEN-LAST:event_data_nascimentoMouseClicked

    private void nome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nome1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nome1ActionPerformed

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

    private void email1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_email1MouseClicked
        email1.setText("");
    }//GEN-LAST:event_email1MouseClicked

    private void email1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email1ActionPerformed

    private void latitudeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_latitudeMouseClicked
        latitude.setText("");
        latitude.setDocument(new TeclasPermitLocalizacao());
    }//GEN-LAST:event_latitudeMouseClicked

    private void latitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_latitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_latitudeActionPerformed

    private void longitudeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_longitudeMouseClicked
        longitude.setText("");
        longitude.setDocument(new TeclasPermitLocalizacao());
    }//GEN-LAST:event_longitudeMouseClicked

    private void longitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_longitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_longitudeActionPerformed

    private void scan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scan1ActionPerformed

    private void scan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scan1MouseClicked
        scan1.setText("");
        scan1.setDocument(new TeclasPermitNum());
    }//GEN-LAST:event_scan1MouseClicked

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
    private javax.swing.JRadioButton Guarda;
    private javax.swing.JRadioButton Psicólogo;
    private rsbuttom.RSButtonMetro ajuda;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField data_nascimento;
    private rsbuttom.RSButtonMetro doc;
    private javax.swing.JTextField email1;
    private rsbuttom.RSButtonMetro ent;
    private rsbuttom.RSButtonMetro home;
    private rsbuttom.RSButtonMetro hor;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private java.awt.Label label1;
    private javax.swing.JTextField latitude;
    private javax.swing.JTextField longitude;
    private javax.swing.JTextField nome1;
    private rsbuttom.RSButtonMetro recl;
    private javax.swing.JTextField scan1;
    private javax.swing.JPanel sidepane4;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("alert.png")));
    }
}
