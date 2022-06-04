package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import gr.uniwa.patelisploumpis.supportticketapp.R;

public class MainActivity extends AppCompatActivity {

    private Button newTicketButton, viewTicketsButton;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_technicians:
                Intent intent = new Intent(this, ViewTechniciansActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_exit:
                finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "MainActivity onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTicketButton = findViewById(R.id.button_new_ticket);
        viewTicketsButton = findViewById(R.id.button_view_tickets);

        newTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTicketActivity.class);
                startActivity(intent);
            }
        });

        viewTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewSupportTicketsActivity.class);
                startActivity(intent);
            }
        });

        // Check for required storage permissions, exit app if not granted
        if(checkPermission()){
            Log.i("INFO", "Checked permissions, already granted");
        }else{
            Log.i("INFO", "Checked permissions, not granted, requesting them");
            requestPermission();
            }
        }

    private boolean checkPermission() {
        int permissionR = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int permissionW = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return permissionR == PackageManager.PERMISSION_GRANTED && permissionW == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0){
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage){
                    Toast.makeText(this, "Required Permissions Granted", Toast.LENGTH_SHORT).show();
                }else{
                        Toast.makeText(this, "Required Permissions Denied", Toast.LENGTH_SHORT).show();
                        finish(); // Terminate application
                }
            }
        }
    }
}
