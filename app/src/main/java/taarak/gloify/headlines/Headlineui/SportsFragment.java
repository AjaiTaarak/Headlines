package taarak.gloify.headlines.Headlineui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import taarak.gloify.headlines.Adapters.Adapter_headlinefrag_topics;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.Model.Model_News;
import taarak.gloify.headlines.R;
import taarak.gloify.headlines.Retrofit_Api.ApiClient;
import taarak.gloify.headlines.Retrofit_Api.ApiInterface;

public class SportsFragment extends Fragment {

    List<Model_Article> articles;
    RecyclerView recyclerView;
    BottomNavigationView navBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sports, container, false);

        recyclerView=root.findViewById(R.id.rv_businessfrag);
        navBar = getActivity().findViewById(R.id.nav_view);
        articles=new ArrayList<>();
        LoadJson("sports");

        return root;
    }
    public void LoadJson(final String keyword){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Model_News> call = null;
        call = apiInterface.getNewsCategry(keyword,15, AppConstants.APIKEY);


        call.enqueue(new Callback<Model_News>() {
            @Override
            public void onResponse(Call<Model_News> call, Response<Model_News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();

                    Adapter_headlinefrag_topics adapter_headlinefrag_topics=new Adapter_headlinefrag_topics(getActivity(),articles);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter_headlinefrag_topics);
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            if (dy > 0 && navBar.isShown()) {
                                navBar.setVisibility(View.GONE);
                            } else if (dy < 0 ) {
                                navBar.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
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
                    Toast.makeText(getActivity(),errorCode,Toast.LENGTH_LONG).show();
                    Log.e("SPORTS FRAG",response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Model_News> call, Throwable t) {
                Log.e("SPORTS FRAG",t.getLocalizedMessage());
            }
        });

    }
}
