package android.example.dai2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class tabela_reclusos extends AppCompatActivity {
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela_reclusos);
        myDialog = new Dialog(this);
        ListView lista = (ListView) findViewById(R.id.lvR);
        final ArrayList<Reclusos> reclusos = adicionarReclusos();
        ArrayAdapter adapter = new ListarReclusos(this, reclusos);
        lista.setAdapter(adapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }



    private ArrayList<Reclusos> adicionarReclusos() {
        ArrayList<Reclusos> reclusos = new ArrayList<Reclusos>();
        Reclusos r = new Reclusos("João Silva","A", "Demência", "1" , R.mipmap.preso1);
                reclusos.add(r);
        r = new Reclusos("Antonio Marco","B", "Demência", "2", R.mipmap.preso2);
        reclusos.add(r);
        return reclusos;
    }

    public void ShowPopup2(View v){
        TextView txtclose;
        TextView doencas;
        TextView piso;
        TextView ala;
        myDialog.setContentView(R.layout.vermaispopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        doencas = (TextView) myDialog.findViewById(R.id.doencas);
        piso = (TextView) myDialog.findViewById(R.id.piso);
        ala = (TextView) myDialog.findViewById(R.id.ala);
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
    public void entrarr (View v) {
        startActivity(new Intent(this, Registar_Reclusos.class));
    }
}
