package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlertAdapter extends ArrayAdapter<AlertSituation> {
    private final Context context;
    private final ArrayList<AlertSituation> elementos;

    public AlertAdapter(Context context, ArrayList<AlertSituation> elementos){
        super(context, R.layout.lista_situacoes, elementos);
        this.context = context;
        this.elementos = elementos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.lista_situacoes, parent, false);

        //TextView nomeRecluso = (TextView) rowView.findViewById(R.id.nomeRecluso);
        TextView severity = (TextView) rowView.findViewById(R.id.severityS);
        TextView descricao = (TextView) rowView.findViewById(R.id.descricaoS);
        //ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);

        //nomeRecluso.setText(reclusos.get(position).getNome());
        severity.setText(elementos.get(position).getSeverity());
        descricao.setText(elementos.get(position).getDescricao());
        //imagem.setImageResource(reclusos.get(position).getImagem());

        return rowView;
    }
}
