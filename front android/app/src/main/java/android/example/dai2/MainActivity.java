package android.example.dai2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Button btnScan;
    private Button btnGetLocation;
    private TextView txLocation;
    private TextView txScan;
    private ImageView imageView11;
    private ImageView imageView9;
    private ImageView imageView12;
    private ImageView imageView10;
    private final int GPS_REQUEST = 200;
    private LocationManager locationManager;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.btnScan);
        txScan = findViewById(R.id.txScan);
        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Camara");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }


        });
        btnGetLocation      = findViewById(R.id.btnGetLocation);
        txLocation      = findViewById(R.id.txLocation);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
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

            }  });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                alert(result.getContents());
                txScan.setText(result.getContents());
                String nome = result.getContents();
                imagem(nome);
            }else{
                alert("Scan Cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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
        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        txLocation.setText("Lat: " + location.getLatitude()+"\nLng: " + location.getLongitude());
        String localizacao = (location.getLatitude() +""+ location.getLongitude());
        // imagens(localizacao);

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
    private void imagem(String nome){
        imageView11 = findViewById(R.id.imageView11);
        imageView9 = findViewById(R.id.imageView9);

        if (nome.equals("joao")){ //falta meter query para verificar se o nome pertence à base de dados
            imageView11.setVisibility(View.VISIBLE);
            imageView9.setVisibility(View.INVISIBLE);
        }
        else {
            imageView9.setVisibility(View.VISIBLE);
            imageView11.setVisibility(View.INVISIBLE);
        }
    }
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

    public void entrar (View v) {
        // if (imageView11.getVisibility() == View.VISIBLE){ //falta meter localização
        startActivity(new Intent(this, Main2Activity.class));
   /* }
    else {
            alert("Erro no login");
        }*/
    }
}
