package android.example.dai2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private Button btnScan;
    private Button btnGetLocation;
    private TextView txLocation, txtScan;
    private final int GPS_REQUEST = 200;
    private LocationManager locationManager;
    private ImageView imageView, imageView2;
    int[] imagens = {R.mipmap.aceite};
    public static String scanValor;
    private boolean sucess = false;
    private boolean isSucess = false;
    private String latitudePris;
    private String longitudePris;
    private String coordenadas;
    public static int estatuto;
    private ImageView imageView11;
    private ImageView imageView9;
    private ImageView imageView12;
    private ImageView imageView10;
    private  String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Camara");
                integrator.setCameraId(0);
                integrator.initiateScan();
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    System.out.print("erro");
                }
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, GPS_REQUEST);
                } else {
                    getLocation();
                }
            }


        });
        btnGetLocation = findViewById(R.id.btnGetLocation);
        txLocation = findViewById(R.id.txLocation);
        txtScan = findViewById(R.id.txScan);
        imageView = (ImageView) findViewById((R.id.imageView11));
        imageView2 = (ImageView) findViewById(R.id.imageView12);

        /*btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, GPS_REQUEST);
                } else {
                    getLocation();
                }

            }
        });*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        scanValor = result.getContents();
        if (result != null) {
            if (result.getContents() != null) {
                Login login = new Login();
                login.execute();
                try {
                    Thread.sleep(1200);
                }
                catch (Exception e){
                    System.out.print("erro");
                }
                if (sucess == true) {
                    Toast.makeText(getApplicationContext(), "Utilizador encontrado!", Toast.LENGTH_SHORT).show();
                    btnGetLocation.setEnabled(true);
                    imageView.setImageResource(imagens[0]);
                    txtScan.setText(name);
                } else {
                    Toast.makeText(getApplicationContext(), "Utilizador não encontrado!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"Scan Cancelado", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLocationChanged(Location location) {
        if (sucess == true) {
            txLocation.setText("Lat: " + location.getLatitude() + "\nLng: " + location.getLongitude());
            double latitude, longitude, longitudePrisao, latitudePrisao, distancia;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            longitudePrisao = Double.parseDouble(longitudePris);
            latitudePrisao = Double.parseDouble(latitudePris);
            if (getDistanciaEntrePontosEmKm(latitude, longitude, latitudePrisao, longitudePrisao) < 999999999.00) {
                imageView2.setImageResource(imagens[0]);
                isSucess = true;
            } else {
                alert("Localização inválida");

            }
        } else {
            Toast.makeText(getApplicationContext(), "Erro na Procura de Utilizador!", Toast.LENGTH_SHORT).show();
        }

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

    private class Login extends AsyncTask<String, String, String> {
        String msg = "Profile não encontrado";
        ProgressDialog progressDialog;


        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "Synchronising", "Searching for user...", true);
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null) {
                    sucess = false;
                } else {
                    String query = "SELECT name, location, id_type FROM Profile WHERE scan like '"+scanValor+"' AND deleted like 0;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        name = rs.getString("name");
                        coordenadas = rs.getString("location");
                        estatuto = rs.getInt("id_type");
                        String[] points = coordenadas.split("\\s*[,]\\s*");
                        latitudePris = points[0];
                        longitudePris = points[1];
                        if (name.equals(null) || coordenadas.equals(false)) {
                            sucess = false;
                        } else {
                            msg = "Utilizador encontrado!";
                            sucess = true;
                        }

                    }
                    rs.close();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                sucess = false;
            }
            return msg;

        }
    }

    /**Este método converte valores em graus para radianos
     * @param graus valor em graus a ser convertido
     * @return double valor em radianos
     * */
    private static double grausParaRadianos(double graus) {
        return graus * Math.PI / 180;
    }
    /**Dados dois pontos (coordenadas geográficas) calcula a distância entre eles em km
     * @param lat1 latitude ponto a
     * @param lon1 longitude ponto a
     * @param lat2 latitude ponto b
     * @param lon2 longitude ponto b
     * @return double distancia entre pontos em Km
     * */
    private static double getDistanciaEntrePontosEmKm(double lat1, double lon1, double lat2, double lon2) {
        int raioTerraKm = 6371;

        double dLat = grausParaRadianos(lat2-lat1);
        double dLon = grausParaRadianos(lon2-lon1);

        lat1 = grausParaRadianos(lat1);
        lat2 = grausParaRadianos(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distanciaEntrePontosKm= raioTerraKm * c;
        return distanciaEntrePontosKm;
    }

   /* private void imagem(String nome){  //mete os icones do visto e x visiveis ou invisiveis
        imageView11 = findViewById(R.id.imageView11);
        imageView9 = findViewById(R.id.imageView9);

        if (nome.equals("---------")){ //falta meter query para verificar se o nome pertence à base de dados
            imageView11.setVisibility(View.VISIBLE);
            imageView9.setVisibility(View.INVISIBLE);
        }
        else {
            imageView9.setVisibility(View.VISIBLE);
            imageView11.setVisibility(View.INVISIBLE);
        }
    }*/
    /*
    private void imagens(String localizacao){
        imageView12 = findViewById(R.id.imageView12);
        imageView10 = findViewById(R.id.imageView10);

        if (localizacao.equals("?")){ // ? -> coordenadas do estabelecimento prisional
            imageView12.setVisibility(View.VISIBLE);
            imageView10.setVisibility(View.INVISIBLE);
        }
        else {
            imageView10.setVisibility(View.VISIBLE);
            imageView12.setVisibility(View.INVISIBLE);
        }
    }
*/
    public void entrar(View v) {
         if (sucess == true && isSucess==true) {
            if (estatuto == 3) {
                startActivity(new Intent(this, inicio_diretor.class));
             }
             if (estatuto == 2) {
                startActivity(new Intent(this, inicio_psicologo.class));

             }
             if (estatuto == 1) {
                 startActivity(new Intent(this, inicio_guarda.class));
             }

            } else {
                 if (sucess==false) {
                    Toast.makeText(getApplicationContext(), "Erro na Procura de Utilizador!", Toast.LENGTH_SHORT).show();
                } else {
                     Toast.makeText(getApplicationContext(), "Erro na Localização de Utilizador!", Toast.LENGTH_SHORT).show();
                }
         }

    }
}

