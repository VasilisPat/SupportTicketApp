package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewTicketActivity extends AppCompatActivity {

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
        Log.i("INFO", "NewTicketActivity onCreate method called");
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

        // Set last support ticket number
        ticketIDEditText.setText(String.valueOf(DatabaseHelper.getInstance(this).getLastTicketID() + 1));

        // Fill technician name spinner with options
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.technician_names, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        technicianNameSpinner.setAdapter(adapter1);

        // Technician name spinner item selection listener
        technicianNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.i("INFO", "TechnicianNameSpinner's onItemSelected Listener called");
                technicianName = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Fill labor type spinner with options
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.labor_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laborTypeSpinner.setAdapter(adapter2);

        // Labor type spinner item selection listener
        laborTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.i("INFO", "LaborTypeSpinner's onItemSelected Listener called");
                laborType = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "Save ticket button onClick method called");
                ticketID = ticketIDEditText.getText().toString();
                clientName= clientNameEditText.getText().toString();
                clientAddress= clientAddressEditText.getText().toString();
                clientPhone= clientPhoneEditText.getText().toString();
                clientEmail = clientEmailEditText.getText().toString();
                laborDate= laborDateEditText.getText().toString();
                laborHours = Integer.parseInt(laborHoursEditText.getText().toString());
                laborDescription= laborDescriptionEditText.getText().toString();

                ticket = new SupportTicket(laborHours, ticketID, technicianName, clientName, clientAddress, clientPhone, clientEmail,
                        laborDate, laborType,laborDescription);

                DatabaseHelper.getInstance(NewTicketActivity.this).addSupportTicket(ticket);

                //TODO 1.1 PDF Creator Class or Method
                //TODO 1.2 Email to all
                //TODO 2.1 Toast message success/fail

                Intent intent = new Intent(NewTicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "Cancel ticket button onClick method called");
                Intent intent = new Intent(NewTicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}