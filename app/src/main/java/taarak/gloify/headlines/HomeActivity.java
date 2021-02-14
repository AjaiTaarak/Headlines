package taarak.gloify.headlines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import taarak.gloify.headlines.UI.HeadlineFragment;
import taarak.gloify.headlines.UI.HomeFragment;
import taarak.gloify.headlines.UI.ShortsFragment;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor edt;
    BottomNavigationView navView;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navView = findViewById(R.id.nav_view);
        menu = navView.getMenu();
        navView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Fragment selectedFragment = null;
                        turntodef();
                        switch (item.getItemId()) {

                            case R.id.navigation_home:
                                menu.findItem(item.getItemId()).setIcon(R.drawable.ic_home);
                                selectedFragment = new HomeFragment();


                                break;
                            case R.id.navigation_headline:
                                menu.findItem(item.getItemId()).setIcon(R.drawable.ic_news);
                                selectedFragment = new HeadlineFragment();

                                break;
                            case R.id.navigation_shorts:
                                menu.findItem(item.getItemId()).setIcon(R.drawable.ic_shorts);
                                selectedFragment = new ShortsFragment();

                                break;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new HomeFragment());
        menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home);
        transaction.commit();





    }
    public void turntodef(){
        menu.findItem(R.id.navigation_home).setIcon(R.drawable.deff_home);
        menu.findItem(R.id.navigation_headline).setIcon(R.drawable.def_news);
        menu.findItem(R.id.navigation_shorts).setIcon(R.drawable.def_play);
    }

    @Override
    public void onBackPressed() {
        sp=getApplicationContext().getSharedPreferences("Key",HomeActivity.MODE_PRIVATE);
        edt=sp.edit();
        int cur_frag=sp.getInt("CURRENT_FRAG",1);
        if(cur_frag>=1){
            Fragment selectedFragment=new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, selectedFragment);
            transaction.commit();
        }else{
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        if(cur_frag==3){
            navView.setVisibility(View.VISIBLE);
            turntodef();
            menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home);
        }

    }
}
