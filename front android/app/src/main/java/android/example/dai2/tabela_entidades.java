package android.example.dai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class tabela_entidades extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela_entidades);
        ListView lista = (ListView) findViewById(R.id.lvE);
        ArrayList<Entidades> entidades = adicionarEntidades();
        ArrayAdapter adapter = new ListarEntidades(this, entidades);
        lista.setAdapter(adapter);
    }


    private ArrayList<Entidades> adicionarEntidades() {
        ArrayList<Entidades> entidades = new ArrayList<Entidades>();
        Entidades e = new Entidades("João","joao@gmail.com", "Psicólogo", "21");
        entidades.add(e);
        e = new Entidades("Antonio","Antonio@gmail.com","Guarda", "32");
        entidades.add(e);
        return entidades;
    }
    public void entrare (View v) {
        startActivity(new Intent(this, android.example.dai2.Main3Activity.class));
    }
}
