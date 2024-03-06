package android.example.dai2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class tabela_gua_reclusos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<ClassListReclusos> itemArrayList;
    private SyncData.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean sucess = false;
    Dialog myDialog;
    Button verDados;
    int posicao;
    public static String numeorRec = "";
    public static int id_recluso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabela_ent_reclusos);
        //ArrayList<Reclusos> reclusos = adicionarReclusos();
        // ArrayAdapter adapter = new ListarReclusos(this, reclusos);
        //   lista.setAdapter(adapter);
        verDados = (Button) findViewById(R.id.button4);
        myDialog = new Dialog(this);

        listView = (ListView) findViewById(R.id.lvR);
        itemArrayList = new ArrayList<ClassListReclusos>();

        final SyncData orderData = new SyncData();
        orderData.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList =(String) (listView.getItemAtPosition(position).toString());
                System.out.println(selectedFromList );


                int selectedItemPosition = listView.getItemAtPosition(position).hashCode();

                System.out.println( selectedItemPosition);

            }
        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalles = new Intent(view.getContext(), Call.Details.class);
                visorDetalles.putExtra("ala", itemArrayList.get(position).alaRec);
                visorDetalles.putExtra("doenca", itemArrayList.get(position).doencaRec);
                visorDetalles.putExtra("piso", itemArrayList.get(position).pisoRec);
            }
        });*/



          /*      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(tabela_reclusos.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Dados")
                        .setMessage("Doenças:" + itemArrayList.get(which_item).doencaRec)
                        .setMessage("Ala:" + itemArrayList.get(which_item).alaRec)
                        .setMessage("Piso:" + itemArrayList.get(which_item).pisoRec);
                return true;
            }
        });*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    /*private ArrayList<Reclusos> adicionarReclusos() {
        ArrayList<Reclusos> reclusos = new ArrayList<Reclusos>();
        Reclusos r = new Reclusos("João","A", "Demência", "1" , R.mipmap.preso1);
                reclusos.add(r);
        r = new Reclusos("Antonio","B", "Demência", "2", R.mipmap.preso2);
        reclusos.add(r);
        return reclusos;
    }*/
    /*public void entrarr (View v) {
        startActivity(new Intent(this, android.example.dai2.Registar_Reclusos.class));
    }*/

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Connection turn un error";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(tabela_gua_reclusos.this, "Lista de reclusos", "A carregar...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null) {
                    sucess = false;
                } else {
                    String query = "SELECT id_recluse, name, disease, wing, floor, imagem, birthday, numero_recluso, date_entry FROM Recluse WHERE deleted like 0";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ClassListReclusos(rs.getInt("id_recluse"), rs.getString("name"), rs.getString("disease"), rs.getString("wing"), rs.getString("floor"), rs.getBytes("imagem"), rs.getString("birthday"), rs.getInt("numero_recluso"), rs.getString("date_entry")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        sucess = true;
                    } else {
                        msg = "No Data Found";
                        sucess = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                sucess = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            Toast.makeText(tabela_gua_reclusos.this, msg + "", Toast.LENGTH_LONG).show();
            if (sucess == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, tabela_gua_reclusos.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

        public class MyAppAdapter extends BaseAdapter {
            public class ViewHolder {
                TextView nome, numeroRec;
                ImageView imageView;

            }

            public List<ClassListReclusos> recluseList;

            public Context context;
            ArrayList<ClassListReclusos> arrayList;

            private MyAppAdapter(List<ClassListReclusos> apps, Context context) {
                this.recluseList = apps;
                this.context = context;
                arrayList = new ArrayList<ClassListReclusos>();
                arrayList.addAll(recluseList);

            }

            @Override
            public int getViewTypeCount() {
                return super.getViewTypeCount();
            }

            @Override
            public int getCount() {
                return recluseList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                ViewHolder viewHolder = null;


                if (rowView == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    rowView = inflater.inflate(R.layout.linha, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nome = (TextView) rowView.findViewById(R.id.nome);
                    viewHolder.numeroRec = (TextView) rowView.findViewById(R.id.numero);
                    viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imagem);
                    rowView.setTag(viewHolder);



                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.nome.setText(recluseList.get(position).getNomeRec() + "");
                viewHolder.numeroRec.setText(recluseList.get(position).getNumero_rec() + "");
                byte[] img = recluseList.get(position).getImg();
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    viewHolder.imageView.setImageBitmap(bitmap);
                } catch (Exception e){

                }
                return rowView;
            }
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                recluseList.clear();
                if(charText.length()==0){
                    recluseList.addAll(arrayList);
                }
                else{
                    for (ClassListReclusos nome : arrayList){
                        if(nome.getNomeRec().toLowerCase(Locale.getDefault())
                                .contains(charText)){
                            recluseList.add(nome);
                        }
                    }
                    for (ClassListReclusos numero : arrayList ){
                        if(Integer.toString(numero.getNumero_rec()).toLowerCase(Locale.getDefault())
                                .contains(charText)){
                            recluseList.add(numero);
                        }
                    }
                }
                notifyDataSetChanged();
            }
            private void sortArrayList(){
                Collections.sort(itemArrayList, new Comparator<ClassListReclusos>() {
                    @Override
                    public int compare(ClassListReclusos o1, ClassListReclusos o2) {
                        return o1.getNomeRec().compareTo(o2.getNomeRec());                    }
                });
                myAppAdapter.notifyDataSetChanged();
            }
            private void sortArrayList2(){
                Collections.sort(itemArrayList, new Comparator<ClassListReclusos>() {
                    @Override
                    public int compare(ClassListReclusos o1, ClassListReclusos o2) {
                        return o1.getNumero_rec() - o2.getNumero_rec();
                    }


                });
                myAppAdapter.notifyDataSetChanged();
            }
        }

    }

    public void entrarRegRec(View v) {
        startActivity(new Intent(this, android.example.dai2.Registar_Reclusos.class));
    }

    public void verData(View view) {

        posicao = listView.getPositionForView(view);
        System.out.println(posicao);
        TextView txtclose;
        TextView doencas;
        TextView piso;
        TextView ala;
        TextView nascimento;
        ImageView sino;
        TextView entrada;
        myDialog.setContentView(R.layout.vermais_gpopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        sino = (ImageView) myDialog.findViewById(R.id.sino);
        doencas = (TextView) myDialog.findViewById(R.id.doencas1);
        piso = (TextView) myDialog.findViewById(R.id.piso1);
        ala = (TextView) myDialog.findViewById(R.id.ala1);
        entrada = (TextView) myDialog.findViewById(R.id.entrada);
        nascimento = (TextView) myDialog.findViewById(R.id.nascimento1);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        sino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    iniciar_situacao(v);
            }});
        doencas.setText(itemArrayList.get(posicao).getDoencaRec());
        piso.setText(itemArrayList.get(posicao).getPisoRec());
        ala.setText(itemArrayList.get(posicao).getAlaRec());
        nascimento.setText(itemArrayList.get(posicao).getNascimento());
        entrada.setText(itemArrayList.get(posicao).getEntradaR());
        System.out.println(posicao);
        myDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        MenuItem sort = menu.findItem(R.id.filter);
        MenuItem numero = menu.findItem(R.id.numero);
        sort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myAppAdapter.sortArrayList();
                return false;
            }
        });
        numero.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myAppAdapter.sortArrayList2();
                return false;
            }
        });
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    myAppAdapter.filter("");
                    listView.clearTextFilter();
                }
                else{
                    myAppAdapter.filter(newText);
                }
                return false;
            }
        });        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajuda){
            startActivity(new Intent(tabela_gua_reclusos.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(tabela_gua_reclusos.this,inicio_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            Intent intent = new Intent(tabela_gua_reclusos.this,horario_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_doc) {
            Intent intent = new Intent(tabela_gua_reclusos.this,documentos_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_perfil){
            Intent intent = new Intent(tabela_gua_reclusos.this,perfil_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(tabela_gua_reclusos.this, tabela_gua_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void ShowPopup(View v){
        TextView txtclose;
        Button btnSim;
        Button btnNao;
        myDialog.setContentView(R.layout.exitpopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        btnNao = (Button) myDialog.findViewById(R.id.btnNao);
        btnSim = (Button) myDialog.findViewById(R.id.btnSim);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sair(v);
                alert("Sessão terminada");
            }
        });
        myDialog.show();
    }
    public void sair (View v) {

        startActivity(new Intent(this, MainActivity.class));
    }
    public void entrarr (View v) {
        startActivity(new Intent(this, Registar_Reclusos.class));
    }
    public void iniciar_situacao (View v) {
        numeorRec = String.valueOf(itemArrayList.get(posicao).getNumero_rec());
        id_recluso = itemArrayList.get(posicao).getId_recluse();
        System.out.println(numeorRec);
        startActivity(new Intent(this, Alerta_guarda.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}