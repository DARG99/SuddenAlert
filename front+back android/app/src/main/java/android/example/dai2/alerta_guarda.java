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

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Alerta_guarda extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
   private  EditText  descricao, numeroR;
   private  String numeroRec, gravidadeA, descricaoA;
   private int id_recluso;
           //= tabela_gua_reclusos.id_recluso;
    Button criarSitução;
    private boolean sucess;
    RadioGroup gravidade;
    RadioButton rb;
    private int estatuto = MainActivity.estatuto;



    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.alerta_guarda);

        myDialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        numeroR = (EditText) findViewById(R.id.textView12);
        gravidade= (RadioGroup) findViewById(R.id.rgroup);
        descricao = (EditText) findViewById(R.id.textView14);
        criarSitução = (Button) findViewById(R.id.btnadicionar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (tabela_psi_reclusos.numeorRec.equals("")) {
            numeroR.setText(tabela_gua_reclusos.numeorRec);
        }
        if (tabela_gua_reclusos.numeorRec.equals("")){
            numeroR.setText(tabela_psi_reclusos.numeorRec);
        }

        criarSitução.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }
    public void register(){
        intialize();
        if (!validate()){
            Toast.makeText(this, "Campos em falta!", Toast.LENGTH_SHORT).show();
        } else {
            CriarAlerta criarRelatorio = new CriarAlerta();
            criarRelatorio.execute();

            try {
                Thread.sleep(2000);
            }
            catch (Exception e){
                System.out.print("erro");
            }

            if (sucess == true) {
                Toast.makeText(this, "Situação criada com sucesso!", Toast.LENGTH_SHORT).show();
                numeroR.setText("");
                if (estatuto == 1) {
                    startActivity(new Intent(this, android.example.dai2.inicio_guarda.class));
                }
                if (estatuto == 2){
                    startActivity(new Intent(this, android.example.dai2.inicio_psicologo.class));
                }

            } else {
                Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public  boolean validate(){
        boolean valid = true;
        if (numeroRec.isEmpty()){
            numeroR.setError("Introduz a identificação do Recluso");
            valid = false;
        }
        if (gravidade.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Falta selecionar a gravidade", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            int radiobuttonid = gravidade.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) findViewById(radiobuttonid);
            gravidadeA = rb.getText().toString().trim();
        }
        if (descricaoA.isEmpty()){
            descricao.setError("Introduz uma descrição");
            valid = false;
        }
        return valid;
    }
    public void intialize(){
        numeroRec = numeroR.getText().toString().trim();
        descricaoA = descricao.getText().toString().trim();
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
            startActivity(new Intent(Alerta_guarda.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(Alerta_guarda.this,inicio_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            Intent intent = new Intent(Alerta_guarda.this,horario_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_doc) {
            Intent intent = new Intent(Alerta_guarda.this, tabela_meus_alertas.class);
            startActivity(intent);
        }else if (id == R.id.nav_perfil){
            Intent intent = new Intent(Alerta_guarda.this, perfil_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(Alerta_guarda.this, tabela_gua_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class CriarAlerta extends AsyncTask<String, String, String> {
        String msg = "";
        String scan = MainActivity.scanValor;
        //String identificacao1 = identificacao.getText().toString();
        // String relatorio = relatorioo.getText().toString();
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
                    System.out.println(numeroRec);
                    String query1 = "SELECT COUNT(1) FROM Recluse WHERE numero_recluso like '" + numeroRec + "' and deleted like 0;";
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(query1);
                    while (resultSet1.next()) {
                        valor = resultSet1.getInt("COUNT(1)");
                    }
                    if (valor == 1 ) {
                        String query2 = "SELECT id_recluse FROM Recluse WHERE numero_recluso like '"+numeroRec+"';";
                        System.out.println(query2);
                        Statement statement2 = connection.createStatement();
                        ResultSet resultSet = statement2.executeQuery(query2);
                        while (resultSet.next()){
                            id_recluso = resultSet.getInt("id_recluse");
                        }
                       String query = "INSERT INTO AlertSituation (`description`, `scan`, `id_recluse`, `severity`) VALUES ('" + descricaoA + "', '" + scan + "', '" + id_recluso + "', '"+ gravidadeA +"');";
                       Statement statement = connection.createStatement();
                        statement.executeUpdate(query);
                        String query4 = "UPDATE Profile SET points=points+10 where scan = '"+scan+"'";
                        Statement statement3 = connection.createStatement();
                        statement3.executeUpdate(query4);
                        msg = "Inserido com sucesso";
                        sucess = true;

                    } else {
                        sucess = false;
                        msg = "Identificação de Recluso inválida!";
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
