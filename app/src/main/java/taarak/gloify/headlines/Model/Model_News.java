package taarak.gloify.headlines.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_News {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResult")
    @Expose
    private int totalResult;

    @SerializedName("articles")
    @Expose
    private List<Model_Article> article;

    public String getStatus() {
        return status;
    }


    public int getTotalResult() {
        return totalResult;
    }

    public List<Model_Article> getArticle() {
        return article;
    }

}
