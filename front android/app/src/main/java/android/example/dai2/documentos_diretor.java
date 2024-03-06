package android.example.dai2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class documentos_diretor extends AppCompatActivity {
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        setContentView(R.layout.activity_documentos_diretor);
        ListView lista = (ListView) findViewById(R.id.lvdoc);
        ArrayList<Documentos> documentos = adicionarDocumentos();
        ArrayAdapter adapter = new ListarDocumentos(this, documentos);
        lista.setAdapter(adapter);
    }

    private ArrayList<Documentos> adicionarDocumentos() {
        ArrayList<Documentos> documentos = new ArrayList<Documentos>();
        Documentos d = new Documentos("Incidente","Joao", "24/10/2019");
        documentos.add(d);
        d = new Documentos("Normal","Antonio","13/1/2020");
        documentos.add(d);
        return documentos;
    }
    public void ShowPopup3(View v){
        TextView txtclose;
        TextView pontos;
        TextView imprimir;
        TextView ala;
        ImageView imp;
        final RatingBar estrelas;
        ImageView submit;
        myDialog.setContentView(R.layout.acoesdoc);
        imp = (ImageView) myDialog.findViewById(R.id.imp);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        submit = (ImageView) myDialog.findViewById(R.id.check);
        pontos = (TextView) myDialog.findViewById(R.id.pontos);
        imprimir = (TextView) myDialog.findViewById(R.id.imprimir);
        estrelas = (RatingBar) myDialog.findViewById(R.id.ratingBar);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
       /* imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manda imprimir o relatorio
            }
        });*/
       estrelas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Toast.makeText(getApplicationContext(), String.valueOf(rating),Toast.LENGTH_LONG).show();
           }
       });
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //guardar o raking
               alert("Pontos atribuídos:" + String.valueOf(estrelas.getRating()));
           }
       });
        myDialog.show();
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
