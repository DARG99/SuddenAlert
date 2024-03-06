package android.example.dai2;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Alterar_horario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    EditText entrada, saida, almoco;
    RadioGroup rg;
    TextView nome, tipoE;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
    private String Entrda, Saida, Almoco, Folga, tipo, folgaAL;
    private boolean sucess;
    private ImageView adicionar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterar_horario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tipoE = (TextView) findViewById(R.id.tipoHor);
        nome = (TextView) findViewById(R.id.NomeHor);
        entrada = (EditText) findViewById(R.id.horaentrada);
        saida = (EditText) findViewById(R.id.horasaida);
        almoco = (EditText) findViewById(R.id.almocoHor);
        rg = (RadioGroup) findViewById(R.id.folga);
        rb1 = (RadioButton) findViewById(R.id.segunda);
        rb2 = (RadioButton) findViewById(R.id.terca);
        rb3 = (RadioButton) findViewById(R.id.quarta);
        rb4 = (RadioButton) findViewById(R.id.quinta);
        rb5 = (RadioButton) findViewById(R.id.sexta);
        rb6 = (RadioButton) findViewById(R.id.sabado);
        rb7 = (RadioButton) findViewById(R.id.domingo);
        adicionar = (ImageView) findViewById(R.id.imageView17);
        nome.setText(tabela_horario.nomeAl);
        entrada.setText(ver_horario.inicio);
        saida.setText(ver_horario.fim);
        almoco.setText(ver_horario.almoco);
        folgaAL = ver_horario.folga;
        System.out.println(folgaAL);
        if (folgaAL.equals("Segunda")) {
            rg.check(rb1.getId());
        }
        if (folgaAL.equals("Terça")) {
            rg.check(rb2.getId());
        }
        if (folgaAL.equals("Quarta")) {
            rg.check(rb3.getId());
        }
        if (folgaAL.equals("Quinta")) {
            rg.check(rb4.getId());
        }
        if (folgaAL.equals("Sexta")) {
            rg.check(rb5.getId());
        }
        if (folgaAL.equals("Sabado")) {
            rg.check(rb6.getId());
        }
        if (folgaAL.equals("Domingo")) {
            rg.check(rb7.getId());
        }

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
        if (Entrda.isEmpty() | Entrda.equals(":")){
            entrada.setError("Introduz uma hora Entrada");
            valid = false;
        }
        if (Saida.isEmpty() | Saida.equals(":")){
            saida.setError("Introduz uma hora de Saida");
            valid = false;
        }
        if (Almoco.isEmpty() | Almoco.equals(":")){
            almoco.setError("Introduz uma hora de Almoço");
            valid = false;
        }
        int radiobuttonid = rg.getCheckedRadioButtonId();
        if (radiobuttonid == -1){
            rb1.setError("Selecione o dia de Folga");
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
                Toast.makeText(this, "Horário alterado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, android.example.dai2.inicio_diretor.class));

            } else {
                Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void intialize() {
        Entrda = entrada.getText().toString().trim();
        Saida = saida.getText().toString().trim();
        //Scan = scan.getText().toString().trim();
        Almoco = almoco.getText().toString().trim();

    }
    private class AdicionarHor extends AsyncTask<String,String,String> {
        String msg = "";
        int valor = 0, idHor = 0;



        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null){
                    msg = "Connection goes wrong";
                    sucess = false;
                } else {
                    String query1 = "select count(1) from Schedule where Entrada like '"+Entrda+"'and  Saida like '"+Saida+"' and Almoco like '"+Almoco+"'and Folga like '"+Folga+"';";
                    Statement statement1 = conn.createStatement();
                    ResultSet resultSet = statement1.executeQuery(query1);
                    while (resultSet.next()){
                        valor = resultSet.getInt("COUNT(1)");
                    }
                    if(valor == 0) {
                        String query = "insert into Schedule (`Entrada`, `Saida`, `Almoco`, `Folga`) Values ('" + Entrda + "', '" + Saida + "', '" + Almoco + "', '" + Folga + "');";
                        Statement stmt = conn.createStatement();
                        System.out.println(query);
                        stmt.executeUpdate(query);
                    }
                    String query2 = "select idSchedule from Schedule where Entrada like '"+Entrda+"'and  Saida like '"+Saida+"' and Almoco like '"+Almoco+"'and Folga like '"+Folga+"';";
                    System.out.println(query2);
                    Statement statement = conn.createStatement();
                    ResultSet resultSet1 = statement.executeQuery(query2);
                    System.out.println(resultSet1);
                    while (resultSet1.next()){
                        idHor = resultSet1.getInt("idSchedule");
                    }
                    String query3 = "update suddenalert.Profile set idSchedule='"+idHor+"' where scan  like '"+tabela_horario.scanAl+"'";
                    Statement statement2 = conn.createStatement();
                    statement2.executeUpdate(query3);
                    msg = "Inserting Successfull!!!!";
                    System.out.println("aqui");
                    sucess = true;

                }
                conn.close();
            } catch (Exception e){
                msg = "Connection goes wrong";
                e.printStackTrace();
                sucess = false;
            }
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
            startActivity(new Intent(Alterar_horario.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(Alterar_horario.this,inicio_diretor.class);
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
                    startActivity(new Intent(Alterar_horario.this, tabela_horario.class));
                }
            });
            addhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Alterar_horario.this, Adicionar_horario.class));
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
                    startActivity(new Intent(Alterar_horario.this, documentos_diretor.class));
                }
            });
            his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Alterar_horario.this, historico.class));
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
                    startActivity(new Intent(Alterar_horario.this, Main3Activity.class));
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
                    startActivity(new Intent(Alterar_horario.this, tabela_reclusos.class));
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Alterar_horario.this, Registar_Reclusos.class));
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
                startActivity(new Intent(Alterar_horario.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Alterar_horario.this, tabela_psicologo.class));
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
