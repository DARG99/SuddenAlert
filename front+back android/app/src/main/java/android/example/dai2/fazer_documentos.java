package android.example.dai2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class fazer_documentos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private EditText   relatorioo, tituloRel;
    private String t_gravidade, t_relatorio, t_titulo;
    private boolean sucess = false;
    Button regRelat;
    private int id_alerta = tabela_alert.id_alert;
    private TextView identificacao, nome;
    private RadioGroup gravidade;
    Dialog myDialog;
    private AppBarConfiguration mAppBarConfiguration;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fazer_documento);
        myDialog = new Dialog(this);
        identificacao = (TextView) findViewById(R.id.numeroRecl);
        relatorioo = (EditText) findViewById(R.id.relatorio);
        tituloRel = (EditText) findViewById(R.id.tituloRel);
        nome = (TextView) findViewById(R.id.identificacao);
        gravidade= (RadioGroup) findViewById(R.id.rgroup);
        regRelat = (Button) findViewById(R.id.submeter);
        regRelat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        nome.setText(tabela_alert.nomeRecluso);
        identificacao.setText(String.valueOf(tabela_alert.numeroRecluso));
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
            startActivity(new Intent(fazer_documentos.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
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
                startActivity(new Intent(this, android.example.dai2.inicio_psicologo.class));

            } else {
                Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
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
        return valid;
    }
    public void intialize(){
        t_relatorio = relatorioo.getText().toString().trim();
        t_titulo = tituloRel.getText().toString().trim();
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
                    String query1 = "SELECT id_recluse FROM Recluse WHERE numero_recluso like '" + tabela_alert.numeroRecluso + "';";
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(query1);
                    while (resultSet1.next()) {
                        valor = resultSet1.getInt("id_recluse");
                    }
                    String query = "INSERT INTO Report (`report`, `scan`, `id_recluse`, `title`, `gravidade`, `id_alertsituation`) VALUES ('" + t_relatorio + "', '" + scan + "', '" + valor + "', '" + t_titulo + "', '" + t_gravidade + "', '" + id_alerta + "');";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);

                    String query2 = "UPDATE AlertSituation SET relatorio ='1' WHERE id_alertsituation='" + id_alerta + "'";
                    Statement statement2 = connection.createStatement();
                    statement2.executeUpdate(query2);
                    msg = "Inserido com sucesso";
                    sucess = true;

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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(fazer_documentos.this,inicio_psicologo.class);
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
                    startActivity(new Intent(fazer_documentos.this, tabela_horario_psi.class));
                }
            });
            meuhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fazer_documentos.this, horario_psicologo.class));
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
                    startActivity(new Intent(fazer_documentos.this, documentos_psicologo.class));
                }
            });
            fazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fazer_documentos.this, fazer_documentos.class));
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
            Intent intent = new Intent(fazer_documentos.this,perfil_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(fazer_documentos.this,tabela_psi_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

}