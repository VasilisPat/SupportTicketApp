package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.Adapter.TechniciansRecyclerAdapter;
import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class ViewTechniciansActivity extends AppCompatActivity {

    private AsyncTask aSyncTask;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager layoutManager;
    private List<Technician> techniciansList = new ArrayList<>();
    private RecyclerView techniciansRecyclerView;
    private TechniciansRecyclerAdapter techniciansRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technicians);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.ticket_yellow)));

        techniciansRecyclerView = findViewById(R.id.recyclerView_technicians);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(techniciansRecyclerView.getContext(), layoutManager.getOrientation());

        techniciansRecyclerView.setLayoutManager(layoutManager);
        techniciansRecyclerView.addItemDecoration(dividerItemDecoration);

        techniciansRecyclerAdapter = new TechniciansRecyclerAdapter(this, techniciansList,
                new TechniciansRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Technician item) {

                    }
                });
        techniciansRecyclerView.setAdapter(techniciansRecyclerAdapter);

        aSyncTask = new AsyncTask<Void, Void, List<Technician>>() {
            @Override
            protected List<Technician> doInBackground(Void... voids) {
                techniciansList =  DatabaseHelper.getInstance(getApplicationContext()).getAllTechnicians();
                return techniciansList;
            }

            @Override
            protected void onPostExecute(List<Technician> techniciansList) {
                techniciansRecyclerAdapter.updateList(techniciansList);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }
}