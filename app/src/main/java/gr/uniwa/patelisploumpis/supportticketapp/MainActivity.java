package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newTicketButton, viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "MainActivity onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTicketButton = findViewById(R.id.button_new);
        viewButton = findViewById(R.id.button_view);

        newTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "NewTicket button onClick method called");
                Intent intent = new Intent(MainActivity.this, NewTicketActivity.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "View button onClick method called");
                Intent intent = new Intent(MainActivity.this, NewTicketActivity.class);
                startActivity(intent);
            }
        });
    }
}