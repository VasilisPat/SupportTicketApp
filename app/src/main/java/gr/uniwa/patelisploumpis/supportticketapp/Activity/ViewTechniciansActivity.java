
package gr.uniwa.patelisploumpis.supportticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.Adapter.TechniciansRecyclerAdapter;
import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class ViewTechniciansActivity extends AppCompatActivity {

    private DividerItemDecoration dividerItemDecoration;
    private FloatingActionButton addTechnicianFloatActionButton;
    private final Handler handler = new Handler();
    private LinearLayoutManager layoutManager;
    private List<Technician> techniciansList = new ArrayList<>();
    private RecyclerView techniciansRecyclerView;
    private TechniciansRecyclerAdapter techniciansRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technicians);

        // Colorize action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.ticket_yellow)));

        addTechnicianFloatActionButton = findViewById(R.id.floatingActionButton_add_technician);
        techniciansRecyclerView = findViewById(R.id.recyclerView_technicians);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(techniciansRecyclerView.getContext(), layoutManager.getOrientation());

        techniciansRecyclerView.setLayoutManager(layoutManager);
        techniciansRecyclerView.addItemDecoration(dividerItemDecoration);

        techniciansRecyclerAdapter = new TechniciansRecyclerAdapter(this, techniciansList,
                new TechniciansRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Technician item) {

                    }
                });
        techniciansRecyclerView.setAdapter(techniciansRecyclerAdapter);

        handler.post(new Runnable() {
            @Override
            public void run() {
                techniciansList =  DatabaseHelper.getInstance(getApplicationContext()).getAllTechnicians();
                techniciansRecyclerAdapter.updateList(techniciansList);
            }
        });

        addTechnicianFloatActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addTechnicianFloatActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left));
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_technician,null);
                PopupWindow popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                Button addButton = popupView.findViewById(R.id.button_add_technician);
                Button cancelButton = popupView.findViewById(R.id.button_cancel_popup);
                TextInputLayout technicianNameTextInputLayout = popupView.findViewById(R.id.textInputLayout_technician_name);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Technician technician = new Technician();
                        technician.setName(technicianNameTextInputLayout.getEditText().getText().toString());
                        if(TextUtils.isEmpty(technician.getName())){
                            technicianNameTextInputLayout.setError("Technician Name Required");
                        }else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    DatabaseHelper.getInstance(getApplicationContext()).addTechnician(technician);
                                    techniciansList.add(technician);
                                    techniciansRecyclerAdapter.updateList(techniciansList);
                                }
                            });
                            popupWindow.dismiss();
                            addTechnicianFloatActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right));
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        addTechnicianFloatActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right));
                    }
                });
            }
        });

    }
}