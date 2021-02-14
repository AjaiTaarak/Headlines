package taarak.gloify.headlines.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.R;

public class Adapter_headlinefrag_topics extends RecyclerView.Adapter<Adapter_headlinefrag_topics.SimpleViewHolder>{


    int flags;
    List<Model_Article> model_topics;
    LayoutInflater inflter;
    SharedPreferences sp;
    SharedPreferences.Editor edt;


    public Adapter_headlinefrag_topics(Context applicationContext, List<Model_Article> model_news) {


        this.model_topics =model_news;
        this.flags=model_news.size();

        inflter = (LayoutInflater.from(applicationContext));
        sp=inflter.getContext().getSharedPreferences("Key",inflter.getContext().MODE_PRIVATE);
        edt=sp.edit();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView txt_topic,txt_source;
        LinearLayout lay_itemnews_view;
        ImageView img_pic;


        public SimpleViewHolder(View view) {
            super(view);

            txt_topic=view.findViewById(R.id.txt_itemtopic_title);
            txt_source=view.findViewById(R.id.txt_itemtopic_source);
            img_pic=view.findViewById(R.id.img_itemtopic_pic);
            lay_itemnews_view=view.findViewById(R.id.lay_itemnews_view);

        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headlinefrag_topic, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int i) {

            holder.txt_topic.setText(model_topics.get(i).getTitle());
            holder.txt_source.setText(model_topics.get(i).getSource().getName());
            Picasso.get().load(model_topics.get(i).getUrlToImage()).error(R.drawable.icon).into(holder.img_pic);
            holder.lay_itemnews_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model_topics.get(i).getUrl()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inflter.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return flags;
    }
}

