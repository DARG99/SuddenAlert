package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarGuarda extends ArrayAdapter<Entidades> {
    private  final Context context;
    private final ArrayList<Entidades> elementos;

    public ListarGuarda(Context context, ArrayList<Entidades> elementos) {
        super(context, R.layout.linha_guarda, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_guarda, parent, false);
        TextView nome = (TextView) rowView.findViewById(R.id.nome);
       TextView email = (TextView) rowView.findViewById(R.id.email);
      //  TextView funcao = (TextView) rowView.findViewById(R.id.funcao);
        TextView pontos = (TextView) rowView.findViewById(R.id.pontos) ;



        nome.setText(elementos.get(position).getNome());
        email.setText(elementos.get(position).getEmail());
     //   funcao.setText(elementos.get(position).getFuncao());
        pontos.setText(elementos.get(position).getPontos());
        return rowView;
    }
}
