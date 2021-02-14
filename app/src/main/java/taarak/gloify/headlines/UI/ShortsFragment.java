package taarak.gloify.headlines.UI;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taarak.gloify.headlines.Adapters.Adapter_Shorts;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Transitions.DepthPageTransformer;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.Model.Model_News;
import taarak.gloify.headlines.R;
import taarak.gloify.headlines.Transitions.ZoomOutPageTransformer;
import taarak.gloify.headlines.Retrofit_Api.ApiClient;
import taarak.gloify.headlines.Retrofit_Api.ApiInterface;

public class ShortsFragment extends Fragment {

    ViewPager2 vp_shorts;
    List<Model_Article> articles;
    SharedPreferences sp;
    Parcelable recyclerViewState;;
    List<String> list_topics;
    CardView crd_back;
    SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shorts, container, false);
        vp_shorts=root.findViewById(R.id.vp_shorts);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        articles=new ArrayList<>();

        sp=getActivity().getSharedPreferences("Key",getActivity().MODE_PRIVATE);
        edt=sp.edit();
        edt.putInt("CURRENT_FRAG",3);
        edt.apply();
        edt.commit();
        crd_back=root.findViewById(R.id.crd_back);
        crd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        int x=sp.getInt("SEL_TOPICS_SIZE",0);
        list_topics=new ArrayList<>();
        for(int i=0;i<x;i++){
            if(AppConstants.isNOTNullOrEmpty(sp.getString("SEL_TOPICS"+i,""))) {
                list_topics.add(sp.getString("SEL_TOPICS" + i, "").trim().toLowerCase());
            }
        }

        LoadJson(0,list_topics.size());

        return root;
    }
    public void LoadJson(final int pos, final int x){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String language = "en";

        Call<Model_News> call = null;
        if(pos < x) {

            call = apiInterface.getNewsSearch(list_topics.get(pos), language, "popularity",5, AppConstants.APIKEY);
        }
        else{
            return;
        }

        call.enqueue(new Callback<Model_News>() {
            @Override
            public void onResponse(Call<Model_News> call, Response<Model_News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    int lastindex=articles.size();

                    articles.addAll(response.body().getArticle());

                    Adapter_Shorts adapter_shorts=new Adapter_Shorts(HomeFragment.context,articles);
                    vp_shorts.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        vp_shorts.setPageTransformer(new DepthPageTransformer());
                    }else{
                        vp_shorts.setPageTransformer(new ZoomOutPageTransformer());
                    }
                    vp_shorts.setAdapter(adapter_shorts);
                    adapter_shorts.notifyItemRangeInserted(lastindex,articles.size());
                    vp_shorts.setCurrentItem(lastindex-1,false);
                    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            Log.e("S","CALL"+pos);
                            if(position==articles.size()-1){
                                LoadJson((pos+1),x);
                            }
                        }
                    };
                    vp_shorts.registerOnPageChangeCallback(pageChangeCallback);
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
                    Toast.makeText(getActivity(),errorCode,Toast.LENGTH_LONG).show();
                    Log.e("SHORTS",response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Model_News> call, Throwable t) {
                Log.e("SHORTS",t.getLocalizedMessage());
            }
        });

    }

}
