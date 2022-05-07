package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewTicketActivity extends AppCompatActivity {

    private Spinner spinner;
    private String workType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "NewTicketActivity onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        //Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5131")));

        //Fill spinner with options
        spinner = (Spinner) findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.type_work, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.i("INFO", "Spinner's onItemSelected Listener called");
                workType = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}