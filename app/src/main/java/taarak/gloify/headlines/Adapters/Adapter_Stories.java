package taarak.gloify.headlines.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.R;

public class Adapter_Stories extends RecyclerView.Adapter<Adapter_Stories.SimpleViewHolder>{



    List<Model_Article> articles;
    LayoutInflater inflter;
    SharedPreferences sp;
    SharedPreferences.Editor edt;


    public Adapter_Stories(Context applicationContext, List<Model_Article> articles) {

        this.articles=articles;

        inflter = (LayoutInflater.from(applicationContext));
        sp=inflter.getContext().getSharedPreferences("Key",inflter.getContext().MODE_PRIVATE);
        edt=sp.edit();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,txt_time,txt_content;
        ImageView img_pic;
        Button btn_readmore;

        public SimpleViewHolder(View view) {
            super(view);

            txt_title=itemView.findViewById(R.id.txt_itemstory_title);
            txt_time=itemView.findViewById(R.id.txt_itemstory_date);
            img_pic=itemView.findViewById(R.id.img_itemstory_pic);
            btn_readmore=itemView.findViewById(R.id.btn_itemstory_readmore);
            txt_content=itemView.findViewById(R.id.txt_itemstory_content);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storymain, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {

        holder.txt_title.setText(articles.get(position).getTitle());
        holder.txt_content.setText(articles.get(position).getDescription());

        holder.txt_time.setText(AppConstants.getDateToTimeFormat(articles.get(position).getPublishedAt()));
        Picasso.get().load(articles.get(position).getUrlToImage()).error(R.drawable.icon).into(holder.img_pic);
        holder.btn_readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(position).getUrl()));
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
        return articles.size();
    }
}

