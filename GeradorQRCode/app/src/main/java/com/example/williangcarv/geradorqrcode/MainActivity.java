package com.example.williangcarv.geradorqrcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    EditText edtTexto;
    Button btnGerar;
    ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciliarComponentes();
        clickButton();
    }

    private void clickButton() {
        btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarQRCode();
            }
        });
    }

    private void gerarQRCode() {
        String texto = edtTexto.getText().toString();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQRCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    private void iniciliarComponentes() {
        edtTexto = (EditText) findViewById(R.id.edtTexto);
        btnGerar = (Button) findViewById(R.id.btnGerar);
        ivQRCode = (ImageView) findViewById(R.id.ivQRCode);
    }
}
