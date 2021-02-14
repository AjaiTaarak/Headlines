package taarak.gloify.headlines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.Adapters.Adapter_topics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.Arrays;

public class SetupActivity2 extends AppCompatActivity {

    RecyclerView rv_toptopics;
    public static Button  btn_done;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initviews();
        Adapter_topics adapter_topics=new Adapter_topics(getApplicationContext(), Arrays.asList(AppConstants.toptopics));
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rv_toptopics.setLayoutManager(layoutManager);
        rv_toptopics.setNestedScrollingEnabled(false);
        rv_toptopics.setAdapter(adapter_topics);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                for(i=0;i<AppConstants.toptopics.length;i++){
                    if(sp.getBoolean(AppConstants.toptopics[i],false)){
                        edt.putString("SEL_TOPICS"+i,AppConstants.toptopics[i]);
                        edt.apply();
                    }
                }
                edt.putInt("SEL_TOPICS_SIZE",i);
                edt.putBoolean("IS_SETUP",true);
                edt.apply();
                edt.commit();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
    public void initviews(){
        rv_toptopics=findViewById(R.id.rv_setup2_topics);
        btn_done=findViewById(R.id.btn_setup2_done);
        sp=getApplicationContext().getSharedPreferences("Key",SetupActivity2.MODE_PRIVATE);
        edt=sp.edit();
    }
    public static void triggerButton(){

        int total_selcted=sp.getInt("SEL_TOPICS_COUNT",0);
        if(total_selcted>3){
            btn_done.setVisibility(View.VISIBLE);
        }else{
            btn_done.setVisibility(View.GONE);
        }
    }

}
