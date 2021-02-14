package taarak.gloify.headlines.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taarak.gloify.headlines.Adapters.Adapter_Briefnews;
import taarak.gloify.headlines.Adapters.Adapter_StoryLogo;
import taarak.gloify.headlines.Adapters.Adapter_homeheadlines;
import taarak.gloify.headlines.Adapters.Adapter_homelocal;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.Model.Model_News;
import taarak.gloify.headlines.R;
import taarak.gloify.headlines.Transitions.ZoomOutPageTransformer;
import taarak.gloify.headlines.Retrofit_Api.ApiClient;
import taarak.gloify.headlines.Retrofit_Api.ApiInterface;

public class HomeFragment extends Fragment {

    TextView txt_greeting;
    RecyclerView rv_worldwide,rv_stories,rv_local;
    SharedPreferences sp;
    NestedScrollView nesscroll;
    List<Model_Article> articles = new ArrayList<>();
    ViewPager2 vp_breifing;
    public static Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView img_greet;
    List<String> list_topics;
    SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);

        txt_greeting.setText(sp.getString("USER_NAME","")+"'s headlines");
        setGreeting();
        int x=sp.getInt("SEL_TOPICS_SIZE",0);
        for(int i=0;i<x;i++){
            list_topics.add(sp.getString("SEL_TOPICS"+i,""));
        }
        LoadJson(0);
        LoadJson(1);
        LoadJson(2);
        Adapter_StoryLogo adapter_storyLogo=new Adapter_StoryLogo(getActivity(),AppConstants.MEDIALOGOS,AppConstants.MEDIANAMES,getActivity());
        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_stories.setLayoutManager(HorizontalLayout1);
        rv_stories.setAdapter(adapter_storyLogo);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJson(0);
                LoadJson(1);
                LoadJson(2);
                Adapter_StoryLogo adapter_storyLogo=new Adapter_StoryLogo(getActivity(),AppConstants.MEDIALOGOS,AppConstants.MEDIANAMES,getActivity());
                LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rv_stories.setLayoutManager(HorizontalLayout1);
                rv_stories.setAdapter(adapter_storyLogo);
            }
        });

        return root;
    }
    public void LoadJson(final int key){


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = "in";
        String language = "en";

        Call<Model_News> call = null;
        Log.e("TES",country);
        if (key == 0 ){
            call = apiInterface.getNewsHeadlines("the-hindu",10, AppConstants.APIKEY);
        } else if(key == 1){
            call = apiInterface.getNews(country,10, AppConstants.APIKEY);
        }else if(key == 2){
            call = apiInterface.getNewsSearch(sp.getString("SEL_STATE","india"),language,"publishedAt",10, AppConstants.APIKEY);
        }

        call.enqueue(new Callback<Model_News>() {
            @Override
            public void onResponse(Call<Model_News> call, Response<Model_News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();

                    if (key == 1 ){
                        List<Model_Article> briefing=new ArrayList<>(articles);
                        Adapter_Briefnews adapter_breifing = new Adapter_Briefnews(HomeFragment.context, briefing);
                            vp_breifing.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                            vp_breifing.setPageTransformer(new ZoomOutPageTransformer());
                            vp_breifing.setAdapter(null);
                            vp_breifing.setAdapter(adapter_breifing);
                            RecyclerView rv = (RecyclerView) vp_breifing.getChildAt(0);
                            rv.setPadding(0, 0, 100, 0);
                            rv.setClipToPadding(false);


                    }else if(key ==0){
                        try {
                            List<Model_Article> headlines_world=new ArrayList<>(articles);
                            Adapter_homeheadlines adapter_trackMain = new Adapter_homeheadlines(HomeFragment.context, headlines_world);
                            rv_worldwide.setLayoutManager(new LinearLayoutManager(context));
                            rv_worldwide.setAdapter(adapter_trackMain);
                            swipeRefreshLayout.setRefreshing(false);
                        }catch (Exception e){
                            Log.e("Home World",e.getMessage());
                        }
                    }else if(key == 2){
                        try {
                            List<Model_Article> headlines_world=new ArrayList<>(articles);
                            Adapter_homelocal adapter_trackMain = new Adapter_homelocal(HomeFragment.context, headlines_world);
                            rv_local.setLayoutManager(new LinearLayoutManager(context));
                            rv_local.setAdapter(adapter_trackMain);
                        }catch (Exception e){
                            Log.e("Home Local",e.getMessage());
                        }
                    }

                } else {
                    swipeRefreshLayout.setRefreshing(false);

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
                    Toast.makeText(getActivity(),errorCode,Toast.LENGTH_LONG).show();
                    Log.e("HOME",response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Model_News> call, Throwable t) {
                Log.e("HOME",t.getLocalizedMessage());
            }
        });
    }

    public void initViews(View view){
        context=getActivity();

        list_topics=new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        vp_breifing=view.findViewById(R.id.vp_home_breifing);
        nesscroll=view.findViewById(R.id.nesscroll_homefrag);
        rv_stories=view.findViewById(R.id.rv_home_stories);
        rv_local=view.findViewById(R.id.rv_home_local);
        txt_greeting=view.findViewById(R.id.txt_home_greeting);
        img_greet=view.findViewById(R.id.img_home_greeting);
        rv_worldwide=view.findViewById(R.id.rv_home_worldwide);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        sp=getActivity().getSharedPreferences("Key",getActivity().MODE_PRIVATE);
        edt=sp.edit();
        edt.putInt("CURRENT_FRAG",1);
        edt.apply();
        edt.commit();
    }
    public void setGreeting(){

        Calendar c = Calendar.getInstance();

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12){
            img_greet.setBackgroundResource(R.drawable.sunrise);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            img_greet.setBackgroundResource(R.drawable.sun);
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            img_greet.setBackgroundResource(R.drawable.sunrise);
        }else {
            img_greet.setBackgroundResource(R.drawable.moon);
        }
    }

}
