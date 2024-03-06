package android.example.dai2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class tabela_alert extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog, eliminarAlert;
    ProgressBar progressBar;
    private ListView listView;
    public static ArrayList<AlertSituation> alertSituationArrayList;
    private SyncData.MyAppAdapter myAppAdapter;
    private boolean success;
    public static String nomeRecluso;
    public static int numeroRecluso, posicao, id_alert;
    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.alerta_psicologo);
        listView = (ListView) findViewById(R.id.alertas);
        alertSituationArrayList = new ArrayList<AlertSituation>();
        myDialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SyncData syncData = new SyncData();
        syncData.execute();

    }

    private class SyncData extends AsyncTask<String, String, String>{
        String msg = "Internet/DB_Connection turn un error";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(tabela_alert.this, "Lista de situações de alerta", "A carregar...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null){
                    success = false;
                } else {
                    String query = "SELECT AlertSituation.id_alertsituation, Recluse.name, Recluse.numero_recluso, AlertSituation.severity, AlertSituation.description FROM AlertSituation INNER JOIN Recluse ON AlertSituation.id_recluse = Recluse.id_recluse WHERE AlertSituation.relatorio like 0 AND AlertSituation.deleted like 0;";
                    Statement statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                alertSituationArrayList.add(new AlertSituation(rs.getInt("id_alertsituation"), rs.getString("name"), rs.getInt("numero_recluso"), rs.getString("severity"), rs.getString("description")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data Found";
                        success = false;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();
            Toast.makeText(tabela_alert.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(alertSituationArrayList, tabela_alert.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

        public class MyAppAdapter extends BaseAdapter{
            public class ViewHolder{
                TextView nome, numero, gravidade, descricao;
            }

            public List<AlertSituation> alertSituations;
            public Context context;
            ArrayList<AlertSituation> arrayList;

            public MyAppAdapter(List<AlertSituation> alertSituations, Context context) {
                this.alertSituations = alertSituations;
                this.context = context;
                arrayList = new ArrayList<AlertSituation>();
                arrayList.addAll(alertSituations);
            }

            @Override
            public int getCount() {
                return alertSituations.size();
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
                if (rowView == null){
                    LayoutInflater inflater = getLayoutInflater();
                    rowView = inflater.inflate(R.layout.lista_situacoes, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.nome = (TextView) rowView.findViewById(R.id.nomeReclusoS);
                    viewHolder.numero = (TextView) rowView.findViewById(R.id.numeroReclusoS);
                    viewHolder.gravidade= (TextView) rowView.findViewById(R.id.severityS);
                    viewHolder.descricao = (TextView) rowView.findViewById(R.id.descricaoS);
                    rowView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.nome.setText(alertSituations.get(position).getNome());
                viewHolder.numero.setText(String.valueOf(alertSituations.get(position).getNumero()));
                viewHolder.gravidade.setText(alertSituations.get(position).getSeverity());
                viewHolder.descricao.setText(alertSituations.get(position).getDescricao());


                return rowView;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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
            startActivity(new Intent(tabela_alert.this, ajuda.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(tabela_alert.this,inicio_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            TextView txtclose;
            Button listahor;
            Button meuhor;
            myDialog.setContentView(R.layout.horariospopup_psi);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listahor = (Button) myDialog.findViewById(R.id.listahor);
            meuhor = (Button) myDialog.findViewById(R.id.meuhor);
            listahor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_alert.this, tabela_horario_psi.class));
                }
            });
            meuhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_alert.this, horario_psicologo.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_doc) {
            TextView txtclose;
            Button listarel;
            Button fazer;
            myDialog.setContentView(R.layout.relatoriospopup_psi);
            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
            listarel = (Button) myDialog.findViewById(R.id.listarel);
            fazer = (Button) myDialog.findViewById(R.id.fazer_relatorio);
            listarel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_alert.this, documentos_psicologo.class));
                }
            });
            fazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(tabela_alert.this, fazer_documento_psi.class));
                }
            });
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }else if (id == R.id.nav_perfil){
            Intent intent = new Intent(tabela_alert.this, perfil_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(tabela_alert.this, tabela_psi_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void eliminar(View v){
        //elimna a situacao de alerta
        TextView txtclose;
        posicao = listView.getPositionForView(v);
        myDialog.setContentView(R.layout.eliminarpopup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                EliminarAlerta eliminarRecluso = new EliminarAlerta();
                eliminarRecluso.execute();
                alert("Sucesso!!");
            }
        }, 7000);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void aceitar (View v) {
        posicao = listView.getPositionForView(v);
        nomeRecluso = alertSituationArrayList.get(posicao).getNome();
        numeroRecluso = alertSituationArrayList.get(posicao).getNumero();
        id_alert = alertSituationArrayList.get(posicao).getId_alert();
        TextView txtclose;
        Button elabora;
        Button nao_elabora;
        myDialog.setContentView(R.layout.aceitar_situcaopopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        elabora = (Button) myDialog.findViewById(R.id.elabora);
        nao_elabora = (Button) myDialog.findViewById(R.id.nao_elabora);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        elabora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elabora(v);
            }
        });
        nao_elabora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
        }

        public void elabora(View v){
        startActivity(new Intent(this, fazer_documentos.class));
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
    private class EliminarAlerta extends AsyncTask<String, String, String>{
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
                    String query = "UPDATE AlertSituation SET deleted='1' where id_alertsituation = '"+ alertSituationArrayList.get(posicao).getId_alert()+ "'";
                    Statement preparedStatement = connection.createStatement();
                    preparedStatement.executeUpdate(query);

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

}
