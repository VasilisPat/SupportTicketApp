package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;

public class ViewTicketsActivity extends AppCompatActivity {

    private AsyncTask aSyncTask;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager layoutManager;
    private List<SupportTicket> supportTicketList;
    private RecyclerView ticketsRecyclerView;
    private SupportTicketsAdapter ticketsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3949AB")));

        //Not in AsyncTask as it's critical to get ticketList before loading window
        supportTicketList = DatabaseHelper.getInstance(getApplicationContext()).getAllTickets();
        ticketsRecyclerView = findViewById(R.id.recyclerView_support_tickets);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(ticketsRecyclerView.getContext(), layoutManager.getOrientation());

        ticketsRecyclerView.setLayoutManager(layoutManager);
        ticketsRecyclerView.addItemDecoration(dividerItemDecoration);

        ticketsAdapter = new SupportTicketsAdapter(this, supportTicketList,
                new SupportTicketsAdapter.OnItemClickListener() {
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
                        }
                    }
                });
        ticketsRecyclerView.setAdapter(ticketsAdapter);

    }
}