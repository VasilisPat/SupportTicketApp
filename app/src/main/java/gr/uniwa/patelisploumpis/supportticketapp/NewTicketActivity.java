package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;

public class NewTicketActivity extends AppCompatActivity {

    private AsyncTask aSyncTask;
    private Button cancelButton, saveButton;
    private EditText ticketIDEditText, clientNameEditText, clientAddressEditText,
            clientPhoneEditText, clientEmailEditText, laborDateEditText, laborHoursEditText, laborDescriptionEditText;
    private int laborHours;
    private Spinner technicianNameSpinner,laborTypeSpinner;
    private String ticketID, technicianName, clientName, clientAddress, clientPhone, clientEmail, laborDate,
            laborType, laborDescription;
    private SupportTicket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        ticketIDEditText = findViewById(R.id.editTextNumber_ticket_id);
        technicianNameSpinner = findViewById(R.id.spinner_technician_name);
        clientNameEditText = findViewById(R.id.editTextText_client_name);
        clientAddressEditText = findViewById(R.id.editText_client_address);
        clientPhoneEditText = findViewById(R.id.editTextPhone_client_phone);
        clientEmailEditText = findViewById(R.id.editTextEmail_client_email);
        laborDateEditText = findViewById(R.id.editTextDate_labor_date);
        laborTypeSpinner = findViewById(R.id.spinner_labor_type);
        laborHoursEditText = findViewById(R.id.editTextNumberDecimal_labor_hours);
        laborDescriptionEditText = findViewById(R.id.editTextMultiLine_labor_description);
        cancelButton = findViewById(R.id.button_cancel_ticket);
        saveButton = findViewById(R.id.button_save_ticket);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5131")));

        aSyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // Set last support ticket number
                ticketIDEditText.setText(String.valueOf(DatabaseHelper.getInstance(getApplicationContext()).getLastTicketID() + 1));
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        // Fill technician name spinner with options
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.technician_names, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        technicianNameSpinner.setAdapter(adapter1);

        // Technician name spinner item selection listener
        technicianNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                technicianName = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        laborDateEditText.setText(sdf.format(Calendar.getInstance().getTime()));

        // Fill labor type spinner with options
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.labor_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laborTypeSpinner.setAdapter(adapter2);

        // Labor type spinner item selection listener
        laborTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                laborType = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketID = ticketIDEditText.getText().toString();
                clientName= clientNameEditText.getText().toString();
                clientAddress= clientAddressEditText.getText().toString();
                clientPhone= clientPhoneEditText.getText().toString();
                clientEmail = clientEmailEditText.getText().toString();
                laborDate= laborDateEditText.getText().toString();
                laborHours = Integer.parseInt(laborHoursEditText.getText().toString());
                laborDescription= laborDescriptionEditText.getText().toString();

                if(checkRequiredEditText()){
                    ticket = new SupportTicket(laborHours, ticketID, technicianName, clientName, clientAddress, clientPhone, clientEmail,
                            laborDate, laborType,laborDescription);

                    aSyncTask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            DatabaseHelper.getInstance(getApplicationContext()).addSupportTicket(ticket);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null); //Parallel execution

                    //Generate PDF file based on ticketID passed as argument
                    new PDFGenerator().execute(new ATaskParams(getApplicationContext(), Integer.valueOf(ticket.getTicketID())));

                    //TODO 1.1 Email to all

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
        if(clientName.matches("")){
            clientNameEditText.setError("Client Name Required");
            checkFlag = false;
        }
        if(clientAddress.equals("")){
            clientAddressEditText.setError("Client Address Required");
            checkFlag = false;
        }
        if(clientPhone.equals("")){
            clientPhoneEditText.setError("Client Phone Required");
            checkFlag = false;
        }
        if(clientEmail.equals("")){
            clientEmailEditText.setError("Client Email Required");
            checkFlag = false;
        }
        if(String.valueOf(laborHours).matches("")){
            laborHoursEditText.setError("Labor Hours Required");
            checkFlag = false;
        }
        return checkFlag;
    }

}