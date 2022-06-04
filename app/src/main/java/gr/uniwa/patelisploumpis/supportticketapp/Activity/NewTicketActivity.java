package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.ASyncTaskParams;
import gr.uniwa.patelisploumpis.supportticketapp.Model.SupportTicket;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;
import gr.uniwa.patelisploumpis.supportticketapp.Task.PDFGenerator;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class NewTicketActivity extends AppCompatActivity {

    private AsyncTask aSyncTask;
    private AutoCompleteTextView technicianNameAutocompleteTextView, laborTypeAutocompleteTextView;
    private Button cancelButton, saveButton;
    private final Handler handler = new Handler();
    private int laborHours;
    private List<Technician> techniciansList = new ArrayList<>();
    private List<String> techniciansNamesList = new ArrayList<>();
    private String ticketID, technicianName, clientName, clientAddress, clientPhone, clientEmail, laborDate,
            laborType, laborDescription;
    private SupportTicket supportTicket;
    private TextInputLayout ticketIDTextInputLayout, technicianNameTextInputLayout, clientNameTextInputLayout, clientAddressTextInputLayout, clientPhoneTextInputLayout,
            clientEmailTextInputLayout, laborDateTextInputLayout, laborHoursTextInputLayout, laborTypeTextInputLayout, laborDescriptionTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        ticketIDTextInputLayout = findViewById(R.id.textInputLayout_ticket_id);
        technicianNameTextInputLayout = findViewById(R.id.textInputLayout_autocompleteTextView_technician_name);
        technicianNameAutocompleteTextView = findViewById(R.id.autocompleteTextView_technician_name);
        clientNameTextInputLayout = findViewById(R.id.textInputLayout_client_name);
        clientAddressTextInputLayout = findViewById(R.id.textInputLayout_client_address);
        clientPhoneTextInputLayout = findViewById(R.id.textInputLayout_client_phone);
        clientEmailTextInputLayout = findViewById(R.id.textInputLayout_client_email);
        laborDateTextInputLayout = findViewById(R.id.textInputLayout_labor_date);
        laborTypeTextInputLayout = findViewById(R.id.textInputLayout_autocompleteTextView_labor_type);
        laborTypeAutocompleteTextView = findViewById(R.id.autocompleteTextView_labor_type);
        laborHoursTextInputLayout = findViewById(R.id.textInputLayout_labor_hours);
        laborDescriptionTextInputLayout = findViewById(R.id.textInputLayout_labor_description);
        cancelButton = findViewById(R.id.button_cancel_ticket);
        saveButton = findViewById(R.id.button_save_ticket);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.primaryLightColor)));

        handler.post(new Runnable() {
            @Override
            public void run() {
                // Set last support ticket number
                ticketIDTextInputLayout.getEditText().setText(String.valueOf(DatabaseHelper.getInstance(getApplicationContext()).getLastTicketID() + 1));
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                // Retrieve techniciansList from DB
                techniciansList = DatabaseHelper.getInstance(getApplicationContext()).getAllTechnicians();
                for (Technician technician : techniciansList){
                    techniciansNamesList.add(technician.getName());
                }
                // Fill technician name autocompleteTextView with options
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, techniciansNamesList);
                technicianNameAutocompleteTextView.setAdapter(adapter1);
            }
        });

        // Technician name autocompleteTextView item selection listener
        technicianNameAutocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                technicianName = parent.getItemAtPosition(pos).toString();
            }
        });

        // Fill labor type autocompleteTextView with options
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.labor_type, android.R.layout.simple_dropdown_item_1line);
        laborTypeAutocompleteTextView.setAdapter(adapter2);

        // Labor type autocompleteTextView item selection listener
        laborTypeAutocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                laborType = parent.getItemAtPosition(pos).toString();
            }
        });

        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        laborDateTextInputLayout.getEditText().setText(sdf.format(Calendar.getInstance().getTime()));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketID = ticketIDTextInputLayout.getEditText().getText().toString();
                clientName= clientNameTextInputLayout.getEditText().getText().toString();
                clientAddress= clientAddressTextInputLayout.getEditText().getText().toString();
                clientPhone= clientPhoneTextInputLayout.getEditText().getText().toString();
                clientEmail = clientEmailTextInputLayout.getEditText().getText().toString();
                laborDate = laborDateTextInputLayout.getEditText().getText().toString();
                if(!TextUtils.isEmpty(laborHoursTextInputLayout.getEditText().getText().toString())){
                    laborHours = Integer.parseInt(laborHoursTextInputLayout.getEditText().getText().toString());
                }else{
                    laborHours = 0;
                }
                laborDescription = laborDescriptionTextInputLayout.getEditText().getText().toString();

                if(checkRequiredEditText()){
                    supportTicket = new SupportTicket(laborHours, ticketID, technicianName, clientName, clientAddress, clientPhone, clientEmail,
                            laborDate, laborType,laborDescription);

                    aSyncTask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            DatabaseHelper.getInstance(getApplicationContext()).addSupportTicket(supportTicket);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            new PDFGenerator().execute(new ASyncTaskParams(getApplicationContext(), supportTicket.getTicketID()));
                            //TODO 1.3 Email to all
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

                    Intent intent = new Intent(NewTicketActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkRequiredEditText(){
        boolean checkFlag = true;
        if(TextUtils.isEmpty(technicianName)){
            technicianNameTextInputLayout.setError("Technician Selection Required");
            checkFlag = false;
        }
        if(TextUtils.isEmpty(clientName)){
            clientNameTextInputLayout.setError("Client Name Required");
            checkFlag = false;
        }
        if(TextUtils.isEmpty(clientAddress)){
            clientAddressTextInputLayout.setError("Client Address Required");
            checkFlag = false;
        }
        if(TextUtils.isEmpty(clientPhone)){
            clientPhoneTextInputLayout.setError("Client Phone Required");
            checkFlag = false;
        }
        if(TextUtils.isEmpty(clientEmail)){
            clientEmailTextInputLayout.setError("Client Email Required");
            checkFlag = false;
        }
        if(TextUtils.isEmpty(laborType)){
            laborTypeTextInputLayout.setError("Technician Selection Required");
            checkFlag = false;
        }
        if(TextUtils.equals(String.valueOf(laborHours),String.valueOf(0))){
            laborHoursTextInputLayout.setError("Labor Hours Required");
            checkFlag = false;
        }
        return checkFlag;
    }

}