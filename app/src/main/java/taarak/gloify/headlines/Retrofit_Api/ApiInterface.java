package taarak.gloify.headlines.Retrofit_Api;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import taarak.gloify.headlines.Model.Model_News;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Model_News> getNews(

            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey

    );
    @GET("top-headlines")
    Call<Model_News> getNewsHeadlines(

            @Query("sources") String sources,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey

    );
    @GET("top-headlines")
    Call<Model_News> getNewsCategry(

            @Query("category") String sources,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<Model_News> getNewsSearch(

            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey

    );


}
