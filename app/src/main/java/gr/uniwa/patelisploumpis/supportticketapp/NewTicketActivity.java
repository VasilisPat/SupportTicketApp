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
import android.widget.Spinner;

public class NewTicketActivity extends AppCompatActivity {

    private Button cancelButton, saveButton;
    private Spinner laborTypeSpinner;
    private String laborType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "NewTicketActivity onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        cancelButton = findViewById(R.id.button_cancel_ticket);
        saveButton = findViewById(R.id.button_save_ticket);
        laborTypeSpinner = (Spinner) findViewById(R.id.spinner_labor_type);

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
                //TODO 2.1 Get text from all edit views
                //TODO 2.2 Toast message success/fail
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