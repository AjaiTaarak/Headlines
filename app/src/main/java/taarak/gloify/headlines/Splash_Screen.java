package taarak.gloify.headlines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {

    Button btn_login;
    LinearLayout lay_splash;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        btn_login =findViewById(R.id.btn_splash_login);
        lay_splash=findViewById(R.id.lay_splash);
        lay_splash.setVisibility(View.GONE);
        sp=getApplicationContext().getSharedPreferences("Key",Splash_Screen.MODE_PRIVATE);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }
        new CountDownTimer(1000, 500) {

            @Override
            public void onTick(long millsUntilFinished) {

            }
            @Override
            public void onFinish() {
                Boolean is_setup=sp.getBoolean("IS_SETUP",false);
                if(isNetworkconnected() && is_setup) {
                    Intent log = new Intent(Splash_Screen.this,HomeActivity.class);
                    startActivity(log);

                }else {
                    lay_splash.setVisibility(View.VISIBLE);
                }
            }
        }.start();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash_Screen.this, SetupActivity1.class));
            }
        });
    }
    private boolean isNetworkconnected(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo()!=null;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }
}
