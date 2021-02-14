package taarak.gloify.headlines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;

public class SetupActivity1 extends AppCompatActivity {

    AutoCompleteTextView auto_edt_state;
    EditText edt_name;
    Button btn_done;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initviews();
        ArrayAdapter state_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, AppConstants.statelist);
        auto_edt_state.setAdapter(state_adapter);
        auto_edt_state.setThreshold(1);
        auto_edt_state.setAdapter(state_adapter);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name=edt_name.getText().toString();
                String str_state=auto_edt_state.getText().toString();
                if(AppConstants.isNOTNullOrEmpty(str_state)){

                    int x=Arrays.binarySearch(AppConstants.statelist,str_state);
                    if(x>=0){
                        if(AppConstants.isNOTNullOrEmpty(str_name)) {
                            edt.putString("USER_NAME",str_name);
                            edt.putString("SEL_STATE",str_state);
                            edt.apply();
                            edt.commit();
                            startActivity(new Intent(getApplicationContext(), SetupActivity2.class));
                        }
                        else{
                            edt_name.setText("");
                            edt_name.setError("Enter a valid name");
                        }
                    }else{
                        auto_edt_state.setText("");
                        auto_edt_state.setError("Enter a valid State");
                    }
                }else{
                    auto_edt_state.setText("");
                    auto_edt_state.setError("Enter a valid State");
                }
            }
        });
    }
    public void initviews(){
        auto_edt_state=findViewById(R.id.edt_setup_state);
        edt_name=findViewById(R.id.edt_setup_name);
        btn_done=findViewById(R.id.btn_setup_done);
        sp=getApplicationContext().getSharedPreferences("Key", SetupActivity1.MODE_PRIVATE);
        edt=sp.edit();
    }

}
