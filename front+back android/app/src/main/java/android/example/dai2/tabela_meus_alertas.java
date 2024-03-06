package android.example.dai2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class tabela_meus_alertas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    public static ArrayList<meus_alertas> alertasArrayList;
    private SyncDataMeusAlertas.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean sucess = false;
    String scan;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.meus_alertas);
        listView = (ListView) findViewById(R.id.meu_alerta);
        scan = MainActivity.scanValor;
        alertasArrayList = new ArrayList<meus_alertas>();
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

        SyncDataMeusAlertas sincroniza = new SyncDataMeusAlertas();
        sincroniza.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    /*
        @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }*/


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
            startActivity(new Intent(tabela_meus_alertas.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(tabela_meus_alertas.this,inicio_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_hor) {
            Intent intent = new Intent(tabela_meus_alertas.this,horario_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_doc) {
            Intent intent = new Intent(tabela_meus_alertas.this, tabela_meus_alertas.class);
            startActivity(intent);
        }else if (id == R.id.nav_perfil){
            Intent intent = new Intent(tabela_meus_alertas.this, perfil_guarda.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(tabela_meus_alertas.this,tabela_gua_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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



    public void relatorios (View v) {
        startActivity(new Intent(this, android.example.dai2.documentos_guarda.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class SyncDataMeusAlertas extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Connection turn un error";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(tabela_meus_alertas.this, "Synchronising", "ListView Loading wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                System.out.println("qq1ui");
                if (conn == null) {
                    sucess = false;
                } else {
                    String query = "Select Recluse.name, AlertSituation.severity, AlertSituation.description from AlertSituation inner join Recluse on AlertSituation.id_recluse = Recluse.id_recluse where AlertSituation.scan = '"+scan+"';";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    System.out.println("ali");
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            try {
                                alertasArrayList.add(new meus_alertas(resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("description")));
                                System.out.println("1");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        sucess = true;
                    } else {
                        msg = "Data not found";
                        sucess = false;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Toast.makeText(tabela_meus_alertas.this, msg + "", Toast.LENGTH_LONG).show();
            if (sucess == false) {
            } else {
                try {
                    myAppAdapter = new tabela_meus_alertas.SyncDataMeusAlertas.MyAppAdapter(alertasArrayList, tabela_meus_alertas.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

        public class MyAppAdapter extends BaseAdapter {
            public class ViewHolder {
                TextView titulo, id, report;
            }

            public List<meus_alertas> reportList;

            public Context context;
            ArrayList<meus_alertas> arrayList;

            private MyAppAdapter(ArrayList<meus_alertas> apps, Context context) {
                this.reportList = apps;
                this.context = context;
                arrayList = new ArrayList<meus_alertas>();
                arrayList.addAll(reportList);
            }

            @Override
            public int getCount() {
                return reportList.size();
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
                    rowView = inflater.inflate(R.layout.lista_meus_alertas, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.titulo = (TextView) rowView.findViewById(R.id.severityS);
                     viewHolder.id = (TextView) rowView.findViewById(R.id.nomeRecluso);
                   viewHolder.report = (TextView) rowView.findViewById(R.id.descricaoS);
                    rowView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.titulo.setText(reportList.get(position).getSeverity());
                  viewHolder.id.setText(reportList.get(position).getNomeRec());
                viewHolder.report.setText(reportList.get(position).getDescricao());
                // viewHolder.email.setText(reportList.get(position).getemail());
                // viewHolder.gravidade.setText(reportList.get(position).getgravidade());
                return rowView;

            }
        }
    }
}
