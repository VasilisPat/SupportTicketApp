package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.Adapter.SupportTicketsRecyclerAdapter;
import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.SupportTicket;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class ViewSupportTicketsActivity extends AppCompatActivity {

    private DividerItemDecoration dividerItemDecoration;
    private final Handler handler = new Handler();
    private LinearLayoutManager layoutManager;
    private List<SupportTicket> supportTicketList = new ArrayList<>();
    private RecyclerView supportTicketsRecyclerView;
    private SupportTicketsRecyclerAdapter supportTicketsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_support_tickets);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.indigo_600)));

        supportTicketsRecyclerView = findViewById(R.id.recyclerView_support_tickets);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(supportTicketsRecyclerView.getContext(), layoutManager.getOrientation());

        supportTicketsRecyclerView.setLayoutManager(layoutManager);
        supportTicketsRecyclerView.addItemDecoration(dividerItemDecoration);

        supportTicketsRecyclerAdapter = new SupportTicketsRecyclerAdapter(this, supportTicketList,
                new SupportTicketsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SupportTicket item) {
                        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SupportTickets", "ticket#" + item.getTicketID() + ".pdf");
                        Uri uriPDFPath = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", pdfFile);

                        Intent openPDFIntent = new Intent(Intent.ACTION_VIEW);
                        openPDFIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        openPDFIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        openPDFIntent.setDataAndType(uriPDFPath, "application/pdf");

                        try{
                            startActivity(openPDFIntent);
                        }catch(ActivityNotFoundException e) {
                            e.printStackTrace();
                            Log.e("ERROR", "PDF File can't be opened");
                            Toast.makeText(getApplicationContext(), "Couldn't Open PDF File", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        supportTicketsRecyclerView.setAdapter(supportTicketsRecyclerAdapter);

        handler.post(new Runnable() {
            @Override
            public void run() {
                supportTicketList =  DatabaseHelper.getInstance(getApplicationContext()).getAllTickets();
                supportTicketsRecyclerAdapter.updateList(supportTicketList);
            }
        });

    }
}