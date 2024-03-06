package android.example.dai2;

import androidx.annotation.ContentView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Registar_Reclusos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imagem;
    private  final int GALERIA_IMAGENS = 1;
    private Button galeria;
    private final int PERMISSAO_REQUEST = 2;
    EditText nome, nascimento, piso, ala, doencas, entrada, numeroRec;
    private String nomeR,nascimentoR, pisoR, alaR, doencaR, entradaR, numeroR;
    Dialog myDialog,erro;
    private boolean sucess;
    Button regRecluso;
    byte[] imagemRec = null;
    ContentValues cv;
    private Date nascimentoRec, entredaRec;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar_recluso);
        nome = (EditText) findViewById(R.id.nomeRecluso);
        nascimento = (EditText) findViewById(R.id.nascimentoRecluso);
        piso = (EditText) findViewById(R.id.pisoRecluso);
        ala = (EditText) findViewById(R.id.alaRecluso);
        doencas = (EditText) findViewById(R.id.doencaRecluso);
        entrada = (EditText) findViewById(R.id.entradaRecluso);
        numeroRec = (EditText) findViewById(R.id.numeroReclusoS);
        regRecluso = (Button) findViewById(R.id.button2);

        imagem = (ImageView)findViewById(R.id.imageView2);
        Button galeria = (Button)findViewById(R.id.button3);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Registar_Reclusos.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(Registar_Reclusos.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                PERMISSAO_REQUEST);
                    }
                }
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);

            }
        });
        myDialog = new Dialog(this);
        erro = new Dialog(this);
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

        regRecluso.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(this, "Recluso criado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, android.example.dai2.inicio_diretor.class));

            } else {
                Erro();
                //Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean validate() throws ParseException {
        boolean valid = true;
        if (nomeR.isEmpty()){
            nome.setError("Introduz a Nome");
            valid = false;
        }
        if (nascimentoR.isEmpty()){
            nascimento.setError("Introduz data de nascimento");
            valid = false;
        }
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        nascimentoRec = dates.parse(nascimentoR);
        int ano = nascimentoRec.getYear()+1900;
        int mes = nascimentoRec.getMonth();
        int dia = nascimentoRec.getDay();
        if (getAge(ano, mes, dia)<18){
            nascimento.setError("Recluso ainda menor!");
            valid = false;
        }
        if (pisoR.isEmpty()){
            piso.setError("Introduz Piso");
            valid = false;
        }
        if (alaR.isEmpty()){
            ala.setError("Introduz Ala");
            valid = false;
        }
        if (numeroR.isEmpty()){
            numeroRec.setError("Introduz Nº Recluso");
            valid = false;
        }
        if (doencaR.isEmpty()){
            doencas.setError("Introduz as Doenças");
            valid = false;
        }
        if (entradaR.isEmpty()){
            entrada.setError("Introduz data de entrada");
            valid = false;
        }
        return valid;
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
        nomeR = nome.getText().toString().trim();
        alaR = ala.getText().toString().trim();
        pisoR = piso.getText().toString().trim();
        numeroR = numeroRec.getText().toString().trim();
        nascimentoR = nascimento.getText().toString().trim();
        entradaR = entrada.getText().toString().trim();
        doencaR = doencas.getText().toString().trim();



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(imagemGaleria);

            /*try {
                File image = new File(picturePath);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf))!= -1;){
                    bos.write(buf, 0,readNum);
                }
                imagemRec = bos.toByteArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// A permissão foi concedida. Pode continuar
            } else {
// A permissão foi negada. Precisa ver o que deve ser desabilitado
            }
            return;
        }
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
                    String query1 = "SELECT COUNT(1) FROM Recluse WHERE numero_recluso like '" + numeroR + "';";
                    Statement statement1 = conn.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(query1);
                    while (resultSet1.next()) {
                        valor = resultSet1.getInt("COUNT(1)");

                        System.out.println(valor);

                    }
                    if (valor == 0 ) {
                        String query = "INSERT INTO `Recluse` (`name`, `date_entry`, `birthday`, `floor`, `wing`, `disease`, `numero_recluso`, `imagem`) VALUES ('" + nomeR + "', '" + entradaR + "', '" + nascimentoR + "', '" + pisoR + "', '" + alaR + "', '" + doencaR + "', '" + numeroR + "', '"+cv+"');";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(query);
                        String query2 = "INSERT INTO Historico (`acao`, `motivo`, `id_recluse`, `tipo`) VALUES ('Inserção', '', '"+numeroR+"', 'Recluso');";
                        Statement statement = conn.createStatement();
                        statement.executeUpdate(query2);
                        msg = "Inserting Successfull!!!!";
                        sucess = true;
                    } else {
                        sucess = false;
                        msg = "Identificação de Recluso inválida!";
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
    public void Erro(){
        erro.setContentView(R.layout.erro_add_recluso);
        TextView txtclose;
        txtclose = (TextView) erro.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erro.dismiss();
            }
        });
        erro.show();
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
            startActivity(new Intent(Registar_Reclusos.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(Registar_Reclusos.this,inicio_diretor.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {

            startActivity(new Intent(Registar_Reclusos.this, tabela_horario.class));

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
                    startActivity(new Intent(Registar_Reclusos.this, documentos_diretor.class));
                }
            });
            his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Registar_Reclusos.this, historico.class));
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
            Intent intent = new Intent(Registar_Reclusos.this,perfil_diretor.class);
            startActivity(intent);
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
                    startActivity(new Intent(Registar_Reclusos.this, Main3Activity.class));
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
                    startActivity(new Intent(Registar_Reclusos.this, tabela_reclusos.class));
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Registar_Reclusos.this, Registar_Reclusos.class));
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
                startActivity(new Intent(Registar_Reclusos.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registar_Reclusos.this, tabela_psicologo.class));
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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
    public void sair (View v) {

        startActivity(new Intent(this, MainActivity.class));
    }

}
