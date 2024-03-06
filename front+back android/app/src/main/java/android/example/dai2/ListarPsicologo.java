package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarPsicologo extends ArrayAdapter<Entidades> {
    private  final Context context;
    private final ArrayList<Entidades> elementos;

    public ListarPsicologo(Context context, ArrayList<Entidades> elementos) {
        super(context, R.layout.linha_psicologo, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_psicologo, parent, false);
        TextView nome = (TextView) rowView.findViewById(R.id.nome);
       TextView email = (TextView) rowView.findViewById(R.id.email);
      //  TextView funcao = (TextView) rowView.findViewById(R.id.funcao);



        nome.setText(elementos.get(position).getNome());
        email.setText(elementos.get(position).getEmail());
     //   funcao.setText(elementos.get(position).getFuncao());
        return rowView;
    }
}
