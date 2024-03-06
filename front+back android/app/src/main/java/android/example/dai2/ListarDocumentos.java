package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarDocumentos extends ArrayAdapter<Documentos> {
    private  final Context context;
    private final ArrayList<Documentos> elementos;

    public ListarDocumentos(Context context, ArrayList<Documentos> elementos) {
        super(context, R.layout.linhadoc, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linhadoc, parent, false);
        TextView nomeRe = (TextView) rowView.findViewById(R.id.nomeRelatorio);
        TextView data = (TextView) rowView.findViewById(R.id.data);
      //  TextView nomeEnt = (TextView) rowView.findViewById(R.id.nomeEnt);
        TextView email = (TextView) rowView.findViewById(R.id.email);
     //   TextView gravidade = (TextView) rowView.findViewById(R.id.gravidade);



        nomeRe.setText(elementos.get(position).getNomeRel());
   //     nomeEnt.setText(elementos.get(position).getNomeEn());
        data.setText(elementos.get(position).getData());
        //email.setText(elementos.get(position).getemail());
      //  gravidade.setText(elementos.get(position).getgravidade());
        return rowView;
    }
}
