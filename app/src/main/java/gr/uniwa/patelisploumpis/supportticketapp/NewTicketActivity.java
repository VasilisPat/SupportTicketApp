package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewTicketActivity extends AppCompatActivity {

    private Button cancelButton, saveButton;
    private EditText technicianIDEditText, technicianNameEditText, clientNameEditText, clientAddressEditText,
            clientPhoneEditText, clientEmailEditText, laborDateEditText, laborHoursEditText, laborDescriptionEditText;
    private int laborHours;
    private Spinner laborTypeSpinner;
    private String technicianID, technicianName, clientName, clientAddress, clientPhone, clientEmail, laborDate,
            laborType, laborDescription;
    private SupportTicket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "NewTicketActivity onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        technicianIDEditText = findViewById(R.id.editTextNumber_technician_id);
        technicianNameEditText = findViewById(R.id.editTextText_technician_name);
        clientNameEditText = findViewById(R.id.editTextText_client_name);
        clientAddressEditText = findViewById(R.id.editText_client_address);
        clientPhoneEditText = findViewById(R.id.editTextPhone_client_phone);
        clientEmailEditText = findViewById(R.id.editTextEmail_client_email);
        laborDateEditText = findViewById(R.id.editTextDate_labor_date);
        laborTypeSpinner = (Spinner) findViewById(R.id.spinner_labor_type);
        laborHoursEditText = findViewById(R.id.editTextNumberDecimal_labor_hours);
        laborDescriptionEditText = findViewById(R.id.editTextMultiLine_labor_description);
        cancelButton = findViewById(R.id.button_cancel_ticket);
        saveButton = findViewById(R.id.button_save_ticket);

        //Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5131")));

        //Fill spinner with options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.labor_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laborTypeSpinner.setAdapter(adapter);

        //Spinner item selection listener
        laborTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.i("INFO", "Spinner's onItemSelected Listener called");
                laborType = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "Save ticket button onClick method called");
                technicianID = technicianIDEditText.getText().toString();
                technicianName = technicianNameEditText.getText().toString();
                clientName= clientNameEditText.getText().toString();
                clientAddress= clientAddressEditText.getText().toString();
                clientPhone= clientPhoneEditText.getText().toString();
                clientEmail = clientEmailEditText.getText().toString();
                laborDate= laborDateEditText.getText().toString();
                laborHours = Integer.parseInt(laborHoursEditText.getText().toString());
                laborDescription= laborDescriptionEditText.getText().toString();

                ticket = new SupportTicket(laborHours, technicianID, technicianName, clientName, clientAddress, clientPhone, clientEmail,
                        laborDate, laborType,laborDescription);

                //TODO 1.1 PDF Creator Class or Method
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