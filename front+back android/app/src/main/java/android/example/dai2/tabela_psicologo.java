package android.example.dai2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class tabela_psicologo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog,eliminarPsi;
    public static  ArrayList<Entidades> entidadesArrayList;
    private SyncDataPsico.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean sucess = false;
    private int posicao;
    private String motivoE;
    EditText motivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabela_psicologo);
        listView = (ListView) findViewById(R.id.lvE);
        myDialog = new Dialog(this);
        eliminarPsi = new Dialog(this);



        entidadesArrayList = new ArrayList<Entidades>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final SyncDataPsico syncDataPsico = new SyncDataPsico();
        syncDataPsico.execute();

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) (listView.getItemAtPosition(position).toString());
                System.out.println(selectedFromList);


                int selectedItemPosition = listView.getItemAtPosition(position).hashCode();

                System.out.println(selectedItemPosition);

            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.letras, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        MenuItem sort = menu.findItem(R.id.filter);
        sort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myAppAdapter.sortArrayList();
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
        });
        return true;
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
            startActivity(new Intent(tabela_psicologo.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(tabela_psicologo.this,inicio_diretor.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {

                    startActivity(new Intent(tabela_psicologo.this, tabela_horario.class));

        }else if (id == R.id.nav_doc) {
            TextView txtclose;
            Button listarel;
            Button his;
            myDialog.setContentView(R.layout.relatoriospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarel = (Button) myDialog.findViewById(R.id.listarel);
            his = (Button) myDialog.findViewById(R.id.his);
            listarel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_psicologo.this, documentos_diretor.class));
                }
            });
            his.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_psicologo.this, historico.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_entidades){
            TextView txtclose;
            Button listagem;
            Button registo;
            myDialog.setContentView(R.layout.entidadesinicio);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listagem = (Button) myDialog.findViewById(R.id.listagem);
            registo = (Button) myDialog.findViewById(R.id.registo);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            listagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirEntidades(v);
                }
            });
            registo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_psicologo.this, Main3Activity.class));
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_reclusos){
            TextView txtclose;
            Button listarec;
            Button reg;
            myDialog.setContentView(R.layout.reclusospopup);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarec = (Button) myDialog.findViewById(R.id.listarec);
            reg = (Button) myDialog.findViewById(R.id.reg);
            listarec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_psicologo.this, tabela_reclusos.class));
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_psicologo.this, Registar_Reclusos.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirEntidades(View v){
        TextView txtclose;
        Button guardas;
        Button psicologos;
        myDialog.setContentView(R.layout.entidadespopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        guardas = (Button) myDialog.findViewById(R.id.guardas);
        psicologos = (Button) myDialog.findViewById(R.id.psicologos);
        guardas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tabela_psicologo.this, tabela_guarda.class));
            }
        });
        psicologos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tabela_psicologo.this, tabela_psicologo.class));
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
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
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void sair (View v) {

        startActivity(new Intent(this, MainActivity.class));
    }


    public void entrare (View v) {
        startActivity(new Intent(this, Main3Activity.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class SyncDataPsico extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        String msg = "Internet/DB_Connection turn un error";

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(tabela_psicologo.this, "Lista de psicólogos", "A carregar ...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null) {
                    sucess = false;
                } else {
                    String query = "SELECT name, email, points, scan FROM Profile WHERE id_type like 2 AND deleted like 0";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                entidadesArrayList.add(new Entidades(rs.getString("name"), rs.getString("email"), rs.getString("scan"), rs.getString("points")));
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
            Toast.makeText(tabela_psicologo.this, msg + "", Toast.LENGTH_LONG).show();
            if (sucess == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(entidadesArrayList, tabela_psicologo.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

        public class MyAppAdapter extends BaseAdapter {
            public class ViewHolder {
                TextView nome, email;
            }

            public List<Entidades> entidadesList;
            public Context context;
            ArrayList<Entidades> arrayList;

            public MyAppAdapter(List<Entidades> entidadesList, Context context) {
                this.entidadesList = entidadesList;
                this.context = context;
                arrayList = new ArrayList<Entidades>();
                arrayList.addAll(entidadesList);
            }

            @Override
            public int getCount() {
                return entidadesList.size();
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
                    rowView = inflater.inflate(R.layout.linha_psicologo, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nome = (TextView) rowView.findViewById(R.id.nome);
                    viewHolder.email = (TextView) rowView.findViewById(R.id.email);
                    rowView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.nome.setText(entidadesList.get(position).getNome());
                viewHolder.email.setText(entidadesList.get(position).getEmail());

                return rowView;
            }
             public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                entidadesList.clear();
                if(charText.length()==0){
                    entidadesList.addAll(arrayList);
                }
                else{
                    for (Entidades nome : arrayList ){
                        if(nome.getNome().toLowerCase(Locale.getDefault())
                                .contains(charText)){
                            entidadesList.add(nome);
                        }
                    }
                }
                notifyDataSetChanged();
            }
            private void sortArrayList(){
                Collections.sort(entidadesArrayList, new Comparator<Entidades>() {
                    @Override
                    public int compare(Entidades o1, Entidades o2) {
                        return o1.getNome().compareTo(o2.getNome());                    }
                });
                myAppAdapter.notifyDataSetChanged();
            }

        }
    }
            public void eliminarPsi(View view){
              //  motivoE = motivo.getText().toString().trim();
                EliminarPsicologo eliminarPsi =new EliminarPsicologo();
                eliminarPsi.execute();
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e);
                }

                startActivity(new Intent(tabela_psicologo.this, tabela_psicologo.class));
                tabela_psicologo.this.finish();
            }
            private class EliminarPsicologo extends AsyncTask<String, String, String>{
                String msg;
                @Override
                protected String doInBackground(String... strings) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                        if (connection == null){
                            msg = "Connect failed";
                        } else {
                            System.out.println(posicao);
                            String query = "UPDATE Profile SET deleted='1' where scan = '"+ entidadesArrayList.get(posicao).getScan()+ "'";
                            Statement preparedStatement = connection.createStatement();
                            preparedStatement.executeUpdate(query);
                            String query2 = "INSERT INTO Historico (`acao`, `motivo`, `scan`, `tipo`) VALUES ('Remoção', '', '"+entidadesArrayList.get(posicao).getScan()+"', 'Psicólogo');";
                            Statement statement = connection.createStatement();
                            statement.executeUpdate(query2);
                        }
                        connection.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return msg;
                }
            }
    public void EliminarPsicologo(View view){
        posicao = listView.getPositionForView(view);
        TextView txtclose, numeroRec;
        ImageView eliminar;
        eliminarPsi.setContentView(R.layout.eliminar_p);
        txtclose = (TextView) eliminarPsi.findViewById(R.id.txtclose);
        numeroRec = (TextView) eliminarPsi.findViewById(R.id.nomeP);
        motivo = (EditText) eliminarPsi.findViewById(R.id.motivo);
        eliminar = (ImageView) eliminarPsi.findViewById(R.id.imageView20);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPsi.dismiss();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPsi(view);
            }
        });
        numeroRec.setText(String.valueOf(entidadesArrayList.get(posicao).getNome()));
        eliminarPsi.show();
    }
    public void verhorarioP(View v){
        startActivity(new Intent(this, ver_horario.class));
    }
}
