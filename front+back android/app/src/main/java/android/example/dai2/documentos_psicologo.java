package android.example.dai2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class documentos_psicologo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    private ListView listView;
    public static ArrayList<Documentos> documentosArrayList;
    private documentos_psicologo.SyncDataDoc.MyAppAdapter myAppAdapter;
    private boolean sucess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        setContentView(R.layout.documentos_entidades);
        listView = (ListView) findViewById(R.id.lvdoc);
        documentosArrayList = new ArrayList<Documentos>();

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

        SyncDataDoc syncDataDoc = new SyncDataDoc();
        syncDataDoc.execute();



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
            startActivity(new Intent(documentos_psicologo.this, ajuda.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(documentos_psicologo.this,inicio_psicologo.class);
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
                    startActivity(new Intent(documentos_psicologo.this, tabela_horario_psi.class));
                }
            });
            meuhor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(documentos_psicologo.this, horario_psicologo.class));
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
                    startActivity(new Intent(documentos_psicologo.this, documentos_psicologo.class));
                }
            });
            fazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(documentos_psicologo.this, fazer_documento_psi.class));
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
            Intent intent = new Intent(documentos_psicologo.this,perfil_psicologo.class);
            startActivity(intent);
        }else if (id == R.id.nav_reclusos){
            Intent intent = new Intent(documentos_psicologo.this, tabela_psi_reclusos.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }


   /* public void ShowPopup3(View v){
        TextView txtclose;
        TextView pontos;
        TextView imprimir;
        TextView ala;
        ImageView imp;
        final RatingBar estrelas;
        ImageView submit;
        myDialog.setContentView(R.layout.acoesdoc);
        imp = (ImageView) myDialog.findViewById(R.id.imp);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        submit = (ImageView) myDialog.findViewById(R.id.check);
        pontos = (TextView) myDialog.findViewById(R.id.pontos);
        imprimir = (TextView) myDialog.findViewById(R.id.imprimir);
        estrelas = (RatingBar) myDialog.findViewById(R.id.ratingBar);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manda imprimir o relatorio
            }
        });
       estrelas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Toast.makeText(getApplicationContext(), String.valueOf(rating),Toast.LENGTH_LONG).show();
           }
       });

      submit.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               //guardar o raking
               alert("Pontos atribuídos:" + String.valueOf(estrelas.getRating()));
           }
       });
        myDialog.show();
    }*/
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
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class SyncDataDoc extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Connection turn un error";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(documentos_psicologo.this, "Listagem de relatórios", "A carregar...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(BD.getBdUrl(), BD.getUSER(), BD.getPASS());
                if (conn == null) {
                    sucess = false;
                } else {
                    String query = "Select  Report.idReport, Report.title, Profile.name, Report.date, Report.gravidade, Profile.email , Report.report from Report inner join Profile on Report.scan = Profile.scan where Report.deleted like '0';";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            try {
                                documentosArrayList.add(new Documentos(resultSet.getInt("idReport"), resultSet.getString("title"), resultSet.getString("name"), resultSet.getString("date"), resultSet.getString("gravidade"), resultSet.getString("email"), resultSet.getString("report")));
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
            Toast.makeText(documentos_psicologo.this, msg + "", Toast.LENGTH_LONG).show();
            if (sucess == false) {
            } else {
                try {
                    myAppAdapter = new documentos_psicologo.SyncDataDoc.MyAppAdapter(documentosArrayList, documentos_psicologo.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

        public class MyAppAdapter extends BaseAdapter {
            public class ViewHolder {
                TextView titulo, id, data, email, gravidade;
            }

            public List<Documentos> reportList;

            public Context context;
            ArrayList<Documentos> arrayList;

            private MyAppAdapter(List<Documentos> apps, Context context) {
                this.reportList = apps;
                this.context = context;
                arrayList = new ArrayList<Documentos>();
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
                    rowView = inflater.inflate(R.layout.linhadoc, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.titulo = (TextView) rowView.findViewById(R.id.nomeRelatorio);
                    // viewHolder.id = (TextView) rowView.findViewById(R.id.nomeEnt);
                    viewHolder.data = (TextView) rowView.findViewById(R.id.dataRelatorio);
                    // viewHolder.email = (TextView) rowView.findViewById(R.id.email);
                    // viewHolder.gravidade = (TextView) rowView.findViewById(R.id.gravidade);
                    rowView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.titulo.setText(reportList.get(position).getNomeRel());
                //  viewHolder.id.setText(reportList.get(position).getNomeEn());
                viewHolder.data.setText(reportList.get(position).getData());
                // viewHolder.email.setText(reportList.get(position).getemail());
                // viewHolder.gravidade.setText(reportList.get(position).getgravidade());
                return rowView;

            }
            public void filter(String charText) {
                charText = charText.toLowerCase(Locale.getDefault());
                reportList.clear();
                if(charText.length()==0){
                    reportList.addAll(arrayList);
                }
                else{
                    for (Documentos nome : arrayList ){
                        if(nome.getNomeRel().toLowerCase(Locale.getDefault())
                                .contains(charText)){
                            reportList.add(nome);
                        }
                    }
                    for (Documentos data : arrayList ){
                        if(data.getData().toLowerCase(Locale.getDefault())
                                .contains(charText)){
                            reportList.add(data);
                        }
                    }
                }
                notifyDataSetChanged();
            }
            private void sortArrayList(){
                Collections.sort(documentosArrayList, new Comparator<Documentos>() {
                    @Override
                    public int compare(Documentos o1, Documentos o2) {
                        return o1.getNomeRel().compareTo(o2.getNomeRel());                    }
                });
                myAppAdapter.notifyDataSetChanged();
            }
            private void sortArrayList2(){
                Collections.sort(documentosArrayList, new Comparator<Documentos>() {
                    @Override
                    public int compare(Documentos o1, Documentos o2) {
                        return o1.getData().compareTo(o2.getData());                    }
                });
                myAppAdapter.notifyDataSetChanged();
            }
        }
    }
    public void verRelatorio(View view){
        int posicao = listView.getPositionForView(view);
        TextView txtclose;
        TextView nomeEntRel;
        TextView gravidadeRel;
        TextView emailRel;
        TextView nomeRel;
        TextView dataRel;
        TextView relato;
        myDialog.setContentView(R.layout.maisdoc);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        nomeEntRel = (TextView) myDialog.findViewById(R.id.nomeEntidR);
        gravidadeRel = (TextView) myDialog.findViewById(R.id.gravidadeRel);
        emailRel = (TextView) myDialog.findViewById(R.id.emailEntidR);
        nomeRel = (TextView) myDialog.findViewById(R.id.nomeRelato);
        dataRel = (TextView) myDialog.findViewById(R.id.dataRelato);
        relato = (TextView) myDialog.findViewById(R.id.relatoRelat);
        nomeEntRel.setText(documentosArrayList.get(posicao).getNomeEn());
        gravidadeRel.setText(documentosArrayList.get(posicao).getGravidade());
        emailRel.setText(documentosArrayList.get(posicao).getEmail());
        nomeRel.setText(documentosArrayList.get(posicao).getNomeRel());
        dataRel.setText(documentosArrayList.get(posicao).getData());
        relato.setText(documentosArrayList.get(posicao).getRelatorio());
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


}
