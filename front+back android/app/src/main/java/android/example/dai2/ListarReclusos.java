package android.example.dai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListarReclusos extends ArrayAdapter<Reclusos> {
    private  final Context context;
    private final ArrayList<Reclusos> elementos;

    public ListarReclusos(Context context, ArrayList<Reclusos> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView nomeRecluso = (TextView) rowView.findViewById(R.id.nome);
       // TextView ala = (TextView) rowView.findViewById(R.id.alaLRec);
       // TextView doenca = (TextView) rowView.findViewById(R.id.doencasLRec);
       // TextView piso = (TextView) rowView.findViewById(R.id.pisoLRec);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);


        nomeRecluso.setText(elementos.get(position).getNome());
      //  ala.setText(elementos.get(position).getAla());
       // doenca.setText(elementos.get(position).getDoencas());
        //piso.setText(elementos.get(position).getPiso());
       imagem.setImageResource(elementos.get(position).getImagem());
        return rowView;
    }
}
