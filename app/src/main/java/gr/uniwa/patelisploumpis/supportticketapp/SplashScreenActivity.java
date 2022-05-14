package gr.uniwa.patelisploumpis.supportticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler scHandler = new Handler();
    private ImageView appLogo;
    private TextView appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appLogo = findViewById(R.id.imageView_support_ticket_logo_ss);
        appInfo = findViewById(R.id.textView_app_info);
        appLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        appInfo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));

        scHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
                try{
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                    Log.e("Error", "Error starting MainActivity");
                }
            }
        }, 3000);

    }
}