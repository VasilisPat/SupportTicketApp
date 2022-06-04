
package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.Adapter.TechniciansRecyclerAdapter;
import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class ViewTechniciansActivity extends AppCompatActivity {

    private DividerItemDecoration dividerItemDecoration;
    private final Handler handler = new Handler();
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

        handler.post(new Runnable() {
            @Override
            public void run() {
                techniciansList =  DatabaseHelper.getInstance(getApplicationContext()).getAllTechnicians();
                techniciansRecyclerAdapter.updateList(techniciansList);
            }
        });

    }
}