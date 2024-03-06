package android.example.dai2;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Adicionar_horario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    EditText scan, entrada, saida, almoco;
    RadioGroup rg;
    RadioButton rb;
    String Entrda, Saida, Almoco, Folga, Scan, tipo;
    private boolean sucess;
    private ImageView adicionar;
    private boolean scanEnc = false, horAtrib = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_horario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scan = (EditText) findViewById(R.id.emailHor);
        entrada = (EditText) findViewById(R.id.horaentrada);
        saida = (EditText) findViewById(R.id.horasaida);
        almoco = (EditText) findViewById(R.id.almocoHor);
        rg = (RadioGroup) findViewById(R.id.folga);
        rb = (RadioButton) findViewById(R.id.segunda);
        adicionar = (ImageView) findViewById(R.id.imageView17);
/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_hor, R.id.nav_doc, R.id.nav_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        // myDialog2 = new Dialog(this);

    }


    public boolean validate() throws ParseException {
        boolean valid = true;
        if (Scan.isEmpty()){
            scan.setError("Introduz um Nome");
            valid = false;
        }
        if (Entrda.isEmpty() | Entrda.equals(":")){
            entrada.setError("Introduz uma hora Entrada");
            valid = false;
        }
        if (Saida.isEmpty() | Saida.equals(":")){
            saida.setError("Introduz uma hora de Saida");
            valid = false;
        }
        if (Almoco.isEmpty() | Almoco.equals(":")){
            rb.setError("Introduz uma hora de Almoço");
            valid = false;
        }
        int radiobuttonid = rg.getCheckedRadioButtonId();
        System.out.println(radiobuttonid);
        if (radiobuttonid == -1){
            rb.setError("Selecione o dia de Folga");
            valid = false;
        } else {
            RadioButton rb = (RadioButton) findViewById(radiobuttonid);
            tipo = rb.getText().toString().trim();
            System.out.println(tipo);
            if (tipo.equals("Segunda-feira")) {
                Folga = "Segunda";
            }
            if (tipo.equals("Terça-feira")) {
                Folga = "Terça";
            }
            if (tipo.equals("Quarta-feira")) {
                Folga = "Quarta";
            }
            if (tipo.equals("Quinta-feira")) {
                Folga = "Quinta";
            }
            if (tipo.equals("Sexta-feira")) {
                Folga = "Sexta";
            }
            if (tipo.equals("Sábado")) {
                Folga = "Sabado";
            }
            if (tipo.equals("Domingo")) {
                Folga = "Domingo";
            }
        }
        return valid;
    }

    public void register() throws ParseException {
        intialize();
        if (!validate()) {
            Toast.makeText(this, "Campos em falta", Toast.LENGTH_LONG).show();
        } else {
            AdicionarHor adicionarHor = new AdicionarHor();
            adicionarHor.execute();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.print("erro");
            }

            if (sucess == true) {
                Toast.makeText(this, "Horário criado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, android.example.dai2.inicio_diretor.class));

            } else {
                if (scanEnc == false) {
                    Toast.makeText(this, "Scan Inválido", Toast.LENGTH_SHORT).show();
                }
                if (horAtrib == true){
                    Toast.makeText(this, "Scan já com horário atribuído", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void intialize() {
        Entrda = entrada.getText().toString().trim();
        Saida = saida.getText().toString().trim();
        Scan = scan.getText().toString().trim();
        Almoco = almoco.getText().toString().trim();

    }
    private class AdicionarHor extends AsyncTask<String,String,String> {
        String msg = "";
        int valor = 0, idHor = 0, valorProc = 0, valorTerHor;



        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null){
                    msg = "Connection goes wrong";
                    sucess = false;
                } else {
                    String query3 = "SELECT COUNT(1) FROM Profile WHERE scan like '" + Scan + "' and deleted like 0;";
                    Statement statement3 = conn.createStatement();
                    ResultSet resultSet3 = statement3.executeQuery(query3);
                    while (resultSet3.next()) {
                        valorProc = resultSet3.getInt("COUNT(1)");
                    }
                    if (valorProc == 1) {
                        String query5 = "SELECT COUNT(1) FROM Profile WHERE scan like '" + Scan + "' and idSchedule is not null;";
                        Statement statement = conn.createStatement();
                        ResultSet resultSet1 = statement.executeQuery(query5);
                        while (resultSet1.next()) {
                            valorTerHor = resultSet1.getInt("COUNT(1)");
                        }
                        if (valorTerHor == 0) {
                            String query1 = "select count(1) from Schedule where Entrada like '" + Entrda + "'and  Saida like '" + Saida + "' and Almoco like '" + Almoco + "'and Folga like '" + Folga + "';";
                            Statement statement1 = conn.createStatement();
                            ResultSet resultSet = statement1.executeQuery(query1);
                            while (resultSet.next()) {
                                valor = resultSet.getInt("COUNT(1)");
                            }
                            if (valor == 0) {
                                String query = "insert into Schedule (`Entrada`, `Saida`, `Almoco`, `Folga`) Values ('" + Entrda + "', '" + Saida + "', '" + Almoco + "', '" + Folga + "');";
                                Statement stmt = conn.createStatement();
                                System.out.println(query);
                                stmt.executeUpdate(query);
                            }
                            String query2 = "select idSchedule from Schedule where Entrada like '" + Entrda + "'and  Saida like '" + Saida + "' and Almoco like '" + Almoco + "'and Folga like '" + Folga + "';";
                            System.out.println(query2);
                            Statement statement4 = conn.createStatement();
                            ResultSet resultSet4 = statement4.executeQuery(query2);
                            while (resultSet4.next()) {
                                idHor = resultSet4.getInt("idSchedule");
                            }
                            String query4 = "update suddenalert.Profile set idSchedule='" + idHor + "' where scan  like '" + Scan + "'";
                            Statement statement2 = conn.createStatement();
                            statement2.executeUpdate(query4);
                            msg = "Inserting Successfull!!!!";
                            sucess = true;
                            scanEnc = true;
                            horAtrib = false;
                        } else {
                            sucess = false;
                            scanEnc = true;
                            horAtrib = true;
                        }

                    } else {
                        sucess = false;
                        scanEnc = false;
                        horAtrib = false;
                    }
                }
                conn.close();
            } catch (Exception e){
                msg = "Connection goes wrong";
                e.printStackTrace();
                sucess = false;
            }
            System.out.println(scanEnc+"       "+horAtrib);
            return msg;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    /*
        @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajuda) {
            startActivity(new Intent(Adicionar_horario.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(Adicionar_horario.this,inicio_diretor.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            TextView txtclose;
            Button listahor;
            Button addhor;
            myDialog.setContentView(R.layout.horariospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listahor = (Button) myDialog.findViewById(R.id.listahor);
            addhor = (Button) myDialog.findViewById(R.id.addhor);
            listahor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, tabela_horario.class));
                }
            });
            addhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, Adicionar_horario.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_doc) {
            TextView txtclose;
            Button listarel;
            Button his;
            myDialog.setContentView(R.layout.relatoriospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarel = (Button) myDialog.findViewById(R.id.listarel);
            his = (Button) myDialog.findViewById(R.id.his);
            listarel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, documentos_diretor.class));
                }
            });
            his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, historico.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_entidades){
            TextView txtclose;
            Button listagem;
            Button registo;
            myDialog.setContentView(R.layout.entidadesinicio);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listagem = (Button) myDialog.findViewById(R.id.listagem);
            registo = (Button) myDialog.findViewById(R.id.registo);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            listagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirEntidades(v);
                }
            });
            registo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, Main3Activity.class));
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_reclusos){
            TextView txtclose;
            Button listarec;
            Button reg;
            myDialog.setContentView(R.layout.reclusospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarec = (Button) myDialog.findViewById(R.id.listarec);
            reg = (Button) myDialog.findViewById(R.id.reg);
            listarec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, tabela_reclusos.class));
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Adicionar_horario.this, Registar_Reclusos.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirEntidades(View v){
        TextView txtclose;
        Button guardas;
        Button psicologos;
        myDialog.setContentView(R.layout.entidadespopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        guardas = (Button) myDialog.findViewById(R.id.guardas);
        psicologos = (Button) myDialog.findViewById(R.id.psicologos);
        guardas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adicionar_horario.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adicionar_horario.this, tabela_psicologo.class));
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }


    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnSim;
        Button btnNao;
        myDialog.setContentView(R.layout.exitpopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        btnNao = (Button) myDialog.findViewById(R.id.btnNao);
        btnSim = (Button) myDialog.findViewById(R.id.btnSim);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sair(v);
                alert("Sessão terminada");
            }
        });
        myDialog.show();
    }

    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void sair(View v) {

        startActivity(new Intent(this, MainActivity.class));
    }



    public void entrare(View v) {
        startActivity(new Intent(this, Main3Activity.class));
    }

    public void rbclick(View v){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radiobuttonid);
        Toast.makeText(getBaseContext(), rb.getText(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
