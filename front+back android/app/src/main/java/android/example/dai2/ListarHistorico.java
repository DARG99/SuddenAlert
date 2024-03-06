package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarHistorico extends ArrayAdapter<Historicos> {
    private  final Context context;
    private final ArrayList<Historicos> elementos;

    public ListarHistorico(Context context, ArrayList<Historicos> elementos) {
        super(context, R.layout.linhahist, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linhahist, parent, false);
        TextView nomePessoa = (TextView) rowView.findViewById(R.id.nomePessoa);
        TextView data = (TextView) rowView.findViewById(R.id.datahis);
        TextView tipo = (TextView) rowView.findViewById(R.id.tipo);
        TextView acao = (TextView) rowView.findViewById(R.id.acao);
        TextView motivo = (TextView) rowView.findViewById(R.id.motivo);



        nomePessoa.setText(elementos.get(position).getNomePessoa());
        data.setText(elementos.get(position).getData());
        tipo.setText(elementos.get(position).getTipo());
        acao.setText(elementos.get(position).getAcao());
        motivo.setText(elementos.get(position).getMotivo());
        return rowView;
    }
}
