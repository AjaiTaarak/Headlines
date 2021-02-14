package taarak.gloify.headlines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taarak.gloify.headlines.Adapters.Adapter_Stories;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.Model.Model_News;
import taarak.gloify.headlines.Retrofit_Api.ApiClient;
import taarak.gloify.headlines.Retrofit_Api.ApiInterface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class Storyactivity extends AppCompatActivity {

    TabLayout tab_story;
    ViewPager2 vp_story;
    List<Model_Article> articles;
    LinearLayout lay_left,lay_right;
    CardView crd_back;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyactivity);

        vp_story=findViewById(R.id.vp_storymode);
        tab_story=findViewById(R.id.tab_layout_story);
        lay_left=findViewById(R.id.lay_story_left);
        lay_right=findViewById(R.id.lay_story_right);
        sp=getApplicationContext().getSharedPreferences("Key",Storyactivity.MODE_PRIVATE);
        edt=sp.edit();

        articles=new ArrayList<>();
        crd_back=findViewById(R.id.crd_back);
        crd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        LoadJson(sp.getString("SEL_CHANNEL","bbc-news"));

    }
    public void LoadJson(final String keyword){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Model_News> call = null;
        call = apiInterface.getNewsHeadlines(keyword,5, AppConstants.APIKEY);


        call.enqueue(new Callback<Model_News>() {
            @Override
            public void onResponse(Call<Model_News> call, Response<Model_News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();


                    Adapter_Stories adapter_headlinefrag_topics=new Adapter_Stories(getApplicationContext(),articles);
                    vp_story.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                    vp_story.setAdapter(adapter_headlinefrag_topics);

                    TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_story, vp_story, true, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        }
                    });
                    tabLayoutMediator.attach();
                    lay_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int curr=vp_story.getCurrentItem();
                            if(curr!=0){
                                vp_story.setCurrentItem(curr-1);
                            }
                        }
                    });
                    lay_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int curr=vp_story.getCurrentItem();
                            if(curr!=articles.size()-1){
                                vp_story.setCurrentItem(curr+1);
                            }
                        }
                    });
                } else {

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }
                    Log.e("STORY",response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Model_News> call, Throwable t) {
                Log.e("STORY",t.getLocalizedMessage());
            }
        });

    }

}
