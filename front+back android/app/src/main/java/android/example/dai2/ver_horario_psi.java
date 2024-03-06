package android.example.dai2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.sql.SQLException;
import java.sql.Statement;

public class ver_horario_psi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    private String inicio, fim, almoco, folga;
    private int tipoHor=tabela_horario_psi.id_schedule;
    private TextView s1, s2, s3, t1, t2, t3, q1, q2, q3, qi1, qi2, qi3, sx1, sx2, sx3, sa1, sa2, sa3, d1, d2, d3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_horariop);
        myDialog = new Dialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        s1 = (TextView) findViewById(R.id.seg0);
        s2 = (TextView) findViewById(R.id.seg6);
        s3 = (TextView) findViewById(R.id.seg12);
        t1 = (TextView) findViewById(R.id.ter0);
        t2 = (TextView) findViewById(R.id.ter6);
        t3 = (TextView) findViewById(R.id.ter12);
        q1 = (TextView) findViewById(R.id.qua0);
        q2 = (TextView) findViewById(R.id.qua6);
        q3 = (TextView) findViewById(R.id.qua12);
        qi1 = (TextView) findViewById(R.id.qui0);
        qi2 = (TextView) findViewById(R.id.qui6);
        qi3 = (TextView) findViewById(R.id.qui12);
        sx1 = (TextView) findViewById(R.id.sex0);
        sx2 = (TextView) findViewById(R.id.sex6);
        sx3 = (TextView) findViewById(R.id.sex12);
        sa1 = (TextView) findViewById(R.id.sab0);
        sa2 = (TextView) findViewById(R.id.sab6);
        sa3 = (TextView) findViewById(R.id.sab12);
        d1 = (TextView) findViewById(R.id.dom0);
        d2 = (TextView) findViewById(R.id.dom6);
        d3 = (TextView) findViewById(R.id.dom12);


        Horario horario = new Horario();
        horario.execute();
    }
    private class Horario extends AsyncTask<String, String, String> {
        String msg = "Horário não encontrado";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ver_horario_psi.this, "Sincronizar", "Há procura do horário", true);
        }

        @Override
        protected void onPostExecute(String s) {
            if (tipoHor == 0) {
                Toast.makeText(ver_horario_psi.this, "Sem horário atribuido", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            } else {
                if (folga.equals("Segunda")) {
                    s1.setText("Folga");
                    s2.setText("Folga");
                    s3.setText("Folga");
                    t1.setText(inicio);
                    q1.setText(inicio);
                    qi1.setText(inicio);
                    sx1.setText(inicio);
                    sa1.setText(inicio);
                    d1.setText(inicio);
                    t2.setText(fim);
                    q2.setText(fim);
                    qi2.setText(fim);
                    sx2.setText(fim);
                    sa2.setText(fim);
                    d2.setText(fim);
                    t3.setText(almoco);
                    q3.setText(almoco);
                    qi3.setText(almoco);
                    sx3.setText(almoco);
                    sa3.setText(almoco);
                    d3.setText(almoco);
                }
                if (folga.equals("Terça")) {
                    t1.setText("Folga");
                    t2.setText("Folga");
                    t3.setText("Folga");
                    s1.setText(inicio);
                    q1.setText(inicio);
                    qi1.setText(inicio);
                    sx1.setText(inicio);
                    sa1.setText(inicio);
                    d1.setText(inicio);
                    s2.setText(fim);
                    q2.setText(fim);
                    qi2.setText(fim);
                    sx2.setText(fim);
                    sa2.setText(fim);
                    d2.setText(fim);
                    s3.setText(almoco);
                    q3.setText(almoco);
                    qi3.setText(almoco);
                    sx3.setText(almoco);
                    sa3.setText(almoco);
                    d3.setText(almoco);

                }
                if (folga.equals("Quarta")) {
                    q1.setText("Folga");
                    q2.setText("Folga");
                    q3.setText("Folga");
                    t1.setText(inicio);
                    s1.setText(inicio);
                    qi1.setText(inicio);
                    sx1.setText(inicio);
                    sa1.setText(inicio);
                    d1.setText(inicio);
                    t2.setText(fim);
                    s2.setText(fim);
                    qi2.setText(fim);
                    sx2.setText(fim);
                    sa2.setText(fim);
                    d2.setText(fim);
                    t3.setText(almoco);
                    s3.setText(almoco);
                    qi3.setText(almoco);
                    sx3.setText(almoco);
                    sa3.setText(almoco);
                    d3.setText(almoco);
                }
                if (folga.equals("Quinta")) {
                    qi1.setText("Folga");
                    qi2.setText("Folga");
                    qi3.setText("Folga");
                    t1.setText(inicio);
                    s1.setText(inicio);
                    q1.setText(inicio);
                    sx1.setText(inicio);
                    sa1.setText(inicio);
                    d1.setText(inicio);
                    t2.setText(fim);
                    s2.setText(fim);
                    q2.setText(fim);
                    sx2.setText(fim);
                    sa2.setText(fim);
                    d2.setText(fim);
                    t3.setText(almoco);
                    s3.setText(almoco);
                    q3.setText(almoco);
                    sx3.setText(almoco);
                    sa3.setText(almoco);
                    d3.setText(almoco);
                }
                if (folga.equals("Sexta")) {
                    sx1.setText("Folga");
                    sx2.setText("Folga");
                    sx3.setText("Folga");
                    t1.setText(inicio);
                    s1.setText(inicio);
                    q1.setText(inicio);
                    qi1.setText(inicio);
                    sa1.setText(inicio);
                    d1.setText(inicio);
                    t2.setText(fim);
                    s2.setText(fim);
                    q2.setText(fim);
                    qi2.setText(fim);
                    sa2.setText(fim);
                    d2.setText(fim);
                    t3.setText(almoco);
                    s3.setText(almoco);
                    q3.setText(almoco);
                    qi3.setText(almoco);
                    sa3.setText(almoco);
                    d3.setText(almoco);

                }
                if (folga.equals("Sabado")) {
                    sa1.setText("Folga");
                    sa2.setText("Folga");
                    sa3.setText("Folga");
                    t1.setText(inicio);
                    s1.setText(inicio);
                    q1.setText(inicio);
                    qi1.setText(inicio);
                    sx1.setText(inicio);
                    d1.setText(inicio);
                    t2.setText(fim);
                    s2.setText(fim);
                    q2.setText(fim);
                    qi2.setText(fim);
                    sx2.setText(fim);
                    d2.setText(fim);
                    t3.setText(almoco);
                    s3.setText(almoco);
                    q3.setText(almoco);
                    qi3.setText(almoco);
                    sx3.setText(almoco);
                    d3.setText(almoco);

                }
                if (folga.equals("Domingo")) {
                    d1.setText("Folga");
                    d2.setText("Folga");
                    d3.setText("Folga");
                    t1.setText(inicio);
                    s1.setText(inicio);
                    q1.setText(inicio);
                    qi1.setText(inicio);
                    sa1.setText(inicio);
                    sx1.setText(inicio);
                    t2.setText(fim);
                    s2.setText(fim);
                    q2.setText(fim);
                    qi2.setText(fim);
                    sa2.setText(fim);
                    sx2.setText(fim);
                    t3.setText(almoco);
                    s3.setText(almoco);
                    q3.setText(almoco);
                    qi3.setText(almoco);
                    sa3.setText(almoco);
                    sx3.setText(almoco);
                }
                progressDialog.dismiss();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (connection == null){

                }else {

                    String query1 = "Select Entrada, Saida, Almoco, Folga from Schedule where idSchedule like '"+tipoHor+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query1);
                    while (resultSet.next()){
                        inicio = resultSet.getString("Entrada");
                        fim = resultSet.getString("Saida");
                        almoco = resultSet.getString("Almoco");
                        folga = resultSet.getString("Folga");
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
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

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajuda){
            startActivity(new Intent(ver_horario_psi.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(ver_horario_psi.this,inicio_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            TextView txtclose;
            Button listahor;
            Button meuhor;
            myDialog.setContentView(R.layout.horariospopup_psi);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listahor = (Button) myDialog.findViewById(R.id.listahor);
            meuhor = (Button) myDialog.findViewById(R.id.meuhor);
            listahor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ver_horario_psi.this, tabela_horario_psi.class));
                }
            });
            meuhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ver_horario_psi.this, horario_psicologo.class));
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
            Button fazer;
            myDialog.setContentView(R.layout.relatoriospopup_psi);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarel = (Button) myDialog.findViewById(R.id.listarel);
            fazer = (Button) myDialog.findViewById(R.id.fazer_relatorio);
            listarel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ver_horario_psi.this, documentos_psicologo.class));
                }
            });
            fazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ver_horario_psi.this, fazer_documento_psi.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_perfil){
            Intent intent = new Intent(ver_horario_psi.this,perfil_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(ver_horario_psi.this,tabela_psi_reclusos.class);
            startActivity(intent);
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
                startActivity(new Intent(ver_horario_psi.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ver_horario_psi.this, tabela_psicologo.class));
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void ShowPopup(View v){
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
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void sair (View v) {

        startActivity(new Intent(this, MainActivity.class));
    }
    public void alterarH(View v){
        startActivity(new Intent(this, Alterar_horario.class));
    }
}
