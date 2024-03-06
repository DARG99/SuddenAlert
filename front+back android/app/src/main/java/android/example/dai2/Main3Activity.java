package android.example.dai2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegrator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    RadioGroup rg;
    RadioButton rb;
    EditText nome, nascimento, scan, email;
    TextView localização;
    private String nomeE, nascimentoE, localizaçãoE = "", tipoE, scanE, emailE;
    Dialog myDialog;
    private Button registar, btnGetLocation;
    ProgressBar progressBar;
    private boolean sucess, maior;
    private final int GPS_REQUEST = 200;
    private LocationManager locationManager;
    String tipo;
    Date nascimentoEnt, dataHoje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar_ent);
        myDialog = new Dialog(this);
        rg= (RadioGroup) findViewById(R.id.rgroup);
        email= (EditText) findViewById(R.id.email2);
        nome = (EditText) findViewById(R.id.editText);
        nascimento = (EditText) findViewById(R.id.data);
        registar = (Button) findViewById(R.id.button10);
        localização = (TextView) findViewById(R.id.txLocation);
        rb = (RadioButton) findViewById(R.id.radioButton2);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        scan = (EditText) findViewById(R.id.scanEnt);
      //  data.addTextChangedListener(Mask.insert("##/##/####", data));
      /*  data.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    data.setText("");
                }
                return false;
            }

        });
        nome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    nome.setText("");
                }
                return false;
            }
        });*/
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
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, GPS_REQUEST);
                } else {
                    getLocation();
                }
            }
        });

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void register() throws ParseException {
        intialize();
        if (!validate()){
            Toast.makeText(this, "Campos em falta", Toast.LENGTH_LONG).show();
        } else {
            Send send = new Send();
            send.execute();

            try {
                Thread.sleep(1000);
            }
            catch (Exception e){
                System.out.print("erro");
            }

            if (sucess == true) {
                Toast.makeText(this, "Entidade criada com sucesso!", Toast.LENGTH_SHORT).show();
                if(tipo.equals("Guarda")){
                    startActivity(new Intent(this, android.example.dai2.tabela_guarda.class));
                }
                else {
                    startActivity(new Intent(this, android.example.dai2.tabela_psicologo.class));
                }
                Main3Activity.this.finish();
            }
               else {
                Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public boolean validate() throws ParseException {
        boolean valid = true;
        if (nomeE.isEmpty()){
            nome.setError("Introduz um Nome");
            valid = false;
        }
        if (nascimentoE.isEmpty()){
            nascimento.setError("Introduz uma data de Nascimento");
            valid = false;
        }
        if (emailE.isEmpty()){
            email.setError("Introduz um Email");
            valid = false;
        }
        if (localizaçãoE.equals("")){
            localização.setError("Localização inválida");
            valid = false;
        }
        if (scanE.isEmpty()){
            scan.setError("Tem de preencher scan");
            valid = false;
        }
        int radiobuttonid = rg.getCheckedRadioButtonId();
        RadioButton rv = (RadioButton) findViewById(radiobuttonid);
        if (radiobuttonid == -1){
            rb.setError("Selecione um tipo de entidade");
            valid = false;
        } else {
            tipo = rv.getText().toString().trim();
            System.out.println(tipo);
            if (tipo.equals("Guarda")) {
                tipoE = "1";
            } else {
                tipoE = "2";
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailE).matches()) {
            email.setError("Email inválido");
            valid = false;
        }
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        nascimentoEnt = dates.parse(nascimentoE);
        if (nascimentoEnt.equals("")){
            nascimento.setText("Introduza uma data de nascimento");
            valid = false;
        }
        int ano = nascimentoEnt.getYear()+1900;
        int mes = nascimentoEnt.getMonth();
        int dia = nascimentoEnt.getDay();
        System.out.println(ano);
        if (getAge(ano, mes, dia)<18){
            nascimento.setError("Data de Nascimento inválida");
            valid = false;
        }

        return valid;
       //6564;
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

    public void intialize(){
        nomeE = nome.getText().toString().trim();
        nascimentoE = nascimento.getText().toString().trim();
        emailE = email.getText().toString().trim();
        scanE = scan.getText().toString().trim();
       // localizaçãoE = localização.getText().toString().trim();
    }

    public void ShowPopup3(View v){
        TextView txtclose;
        myDialog.setContentView(R.layout.registopopup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                alert("Sucesso!!");
            }
        }, 7000);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


    public void rbclick(View v){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radiobuttonid);
        Toast.makeText(getBaseContext(), rb.getText(), Toast.LENGTH_SHORT).show();
    }
   /* public void registar (View v) {
       nome = (EditText) findViewById(R.id.editText);
        data = (EditText) findViewById(R.id.data);
        if (nome.getText().length() == 0) {//como o tamanho é zero é nulla aresposta
            nome.setError("Campo vazio");}
        else {
            if (data.getText().length() == 0 || data.getText().length() < 8) {//como o tamanho é zero é nulla aresposta
                data.setError("Preencha corretamente");}
            else{
                //startActivity(new Intent(this, Main2Activity.class));
                ShowPopup3(v);}
        }

    }*/
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
            startActivity(new Intent(Main3Activity.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(Main3Activity.this,inicio_diretor.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            TextView txtclose;
            Button listahor;
            Button addhor;
            myDialog.setContentView(R.layout.horariospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listahor = (Button) myDialog.findViewById(R.id.listahor);
            addhor = (Button) myDialog.findViewById(R.id.meuhor);
            listahor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Main3Activity.this, tabela_horario.class));
                }
            });
            addhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Main3Activity.this, Adicionar_horario.class));
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
                    startActivity(new Intent(Main3Activity.this, documentos_diretor.class));
                }
            });
            his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Main3Activity.this, historico.class));
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
                    startActivity(new Intent(Main3Activity.this, Main3Activity.class));
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
                    startActivity(new Intent(Main3Activity.this, tabela_reclusos.class));
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Main3Activity.this, Registar_Reclusos.class));
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
                startActivity(new Intent(Main3Activity.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, tabela_psicologo.class));
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    public void sair (View v) {

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onLocationChanged(Location location) {
        localizaçãoE =""+location.getLatitude()+","+location.getLongitude()+"";
        localização.setText("Lat: " + location.getLatitude() + "\nLng: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class Send extends AsyncTask<String,String,String> {
        String msg = "";
        int valor = 0;



        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null){
                    msg = "Connection goes wrong";
                    sucess = false;
                } else {
                    String query1 = "SELECT COUNT(1) FROM Profile WHERE scan like '"+scanE+"';";
                    Statement statement1 = conn.createStatement();
                    ResultSet resultSet = statement1.executeQuery(query1);
                    while (resultSet.next()){
                        valor = resultSet.getInt("COUNT(1)");
                    }
                    if(valor == 0) {
                        String query = "INSERT INTO `Profile` (`id_type`, `name`, `location`, `birthday`, `scan`, `email`) VALUES ('" + tipoE + "', '" + nomeE + "', '" + localizaçãoE + "', '" + nascimentoE + "', '"+scanE+"','" +emailE+"');";
                        Statement stmt = conn.createStatement();
                        System.out.println(query);
                        stmt.executeUpdate(query);
                        String query2 = "INSERT INTO Historico (`acao`, `motivo`, `scan`, `tipo`) VALUES ('Inserção Entidade', '', '"+scanE+"', '" + tipo + "');";
                        Statement statement = conn.createStatement();
                        statement.executeUpdate(query2);
                        msg = "Inserting Successfull!!!!";
                        sucess = true;
                    } else {
                        sucess = false;
                        msg = "Scan já utilizado por outra Entidade";
                    }
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
}