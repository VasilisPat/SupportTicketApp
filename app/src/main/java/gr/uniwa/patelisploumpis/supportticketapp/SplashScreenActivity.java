package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler scHandler = new Handler();
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.imageView_support_ticket_logo_ss);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));

        scHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
                try{
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, 3000);

    }
}