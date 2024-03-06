package android.example.dai2;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VerDetalhesRec extends AppCompatActivity {
    //private ArrayList<ClassListReclusos> dados = tabela_reclusos.itemArrayList;
    ListView listView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vermaispopup);
    }
}
