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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class fazer_documento_psi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText   relatorioo, tituloRel, identificacao;
    private String t_gravidade, t_relatorio, t_titulo, t_identificacao;
    private boolean sucess = false;
    Button regRelat;
   // Este relatório não provem de nenhuma situação de alerta
    private RadioGroup gravidade;
    private AppBarConfiguration mAppBarConfiguration;

    Dialog myDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fazer_documento_psi);
        myDialog = new Dialog(this);
        identificacao = (EditText) findViewById(R.id.numeroRecl);
        relatorioo = (EditText) findViewById(R.id.relatorio);
        tituloRel = (EditText) findViewById(R.id.tituloRel);
        gravidade= (RadioGroup) findViewById(R.id.rgroup);
        regRelat = (Button) findViewById(R.id.submeter);
        regRelat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajuda){
            startActivity(new Intent(fazer_documento_psi.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(fazer_documento_psi.this,inicio_psicologo.class);
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
                    startActivity(new Intent(fazer_documento_psi.this, tabela_horario_psi.class));
                }
            });
            meuhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fazer_documento_psi.this, horario_psicologo.class));
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
                    startActivity(new Intent(fazer_documento_psi.this, documentos_psicologo.class));
                }
            });
            fazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fazer_documento_psi.this, fazer_documento_psi.class));
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
            Intent intent = new Intent(fazer_documento_psi.this,perfil_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(fazer_documento_psi.this,tabela_psi_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void register(){
        intialize();
        if (!validate()){
            Toast.makeText(this, "Campos em falta!", Toast.LENGTH_SHORT).show();
        } else {
            CriarRelatorio criarRelatorio = new CriarRelatorio();
            criarRelatorio.execute();

            try {
                Thread.sleep(1000);
            }
            catch (Exception e){
                System.out.print("erro");
            }

            if (sucess == true) {
                Toast.makeText(this, "Relatório criado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, inicio_psicologo.class));

            } else {
                Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
                identificacao.setError("Número de recluso inexistente");
            }
        }
    }

    public  boolean validate(){
        boolean valid = true;
        if (gravidade.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Falta selecionar a gravidade", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            int radiobuttonid = gravidade.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) findViewById(radiobuttonid);
            t_gravidade = rb.getText().toString().trim();
        }
        if (t_relatorio.isEmpty()){
            relatorioo.setError("Introduza um relatorio");
            valid = false;
        }
        if (t_titulo.isEmpty()){
            tituloRel.setError("Introduz um titulo");
            valid = false;
        }
        if (t_identificacao.isEmpty()){
            identificacao.setError("Introduza um número de recluso");
            valid = false;
        }
        return valid;
    }
    public void intialize(){
        t_relatorio = relatorioo.getText().toString().trim();
        t_titulo = tituloRel.getText().toString().trim();
        t_identificacao = identificacao.getText().toString().trim();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class CriarRelatorio extends AsyncTask<String, String, String>{
        String msg = "";
        String scan = MainActivity.scanValor;
        int valor = 0;



        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (connection == null){
                    sucess = false;
                    msg = "Não foi possível realizar connection";
                } else {
                    String query2 = "SELECT COUNT(1) FROM Recluse WHERE numero_recluso like '" + t_identificacao + "' and deleted like 0;";
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                    while (resultSet2.next()) {
                        valor = resultSet2.getInt("COUNT(1)");
                    }
                    if (valor == 1) {
                        String query1 = "SELECT id_recluse FROM Recluse WHERE numero_recluso like '" + t_identificacao + "';";
                        Statement statement1 = connection.createStatement();
                        ResultSet resultSet1 = statement1.executeQuery(query1);
                        while (resultSet1.next()) {
                            valor = resultSet1.getInt("id_recluse");
                        }
                        String query = "INSERT INTO Report (`report`, `scan`, `id_recluse`, `title`, `gravidade`) VALUES ('" + t_relatorio + "', '" + scan + "', '" + valor + "', '" + t_titulo + "', '" + t_gravidade + "');";
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                        msg = "Inserido com sucesso";
                        sucess = true;
                    } else {
                        sucess =false;
                    }

                }

                connection.close();
            } catch (Exception e){
                msg = "Connection correu mal";
                sucess = false;
            }
            return msg;
        }
    }
    public void rbclick(View v){
        int radiobuttonid = gravidade.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radiobuttonid);
        Toast.makeText(getBaseContext(), rb.getText(), Toast.LENGTH_SHORT).show();
    }
}