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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.R;

public class Adapter_homeheadlines extends RecyclerView.Adapter<Adapter_homeheadlines.SimpleViewHolder>{



    List<Model_Article> model_headlines;
    LayoutInflater inflter;
    SharedPreferences sp;
    SharedPreferences.Editor edt;


    public Adapter_homeheadlines(Context applicationContext, List<Model_Article> model_headlines) {


        this.model_headlines =model_headlines;
        inflter = (LayoutInflater.from(applicationContext));
        sp=inflter.getContext().getSharedPreferences("Key",inflter.getContext().MODE_PRIVATE);
        edt=sp.edit();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,txt_source,txt_time;
        ImageView img_toppic,img_pic,img_bookmark,img_share;
        LinearLayout lay_side_img;
        CardView crd_top;

        public SimpleViewHolder(View view) {
            super(view);

            txt_source=view.findViewById(R.id.txt_itemheadline_source);
            txt_title=view.findViewById(R.id.txt_itemheadline_title);
            txt_time=view.findViewById(R.id.txt_itemheadline_time);
            img_bookmark=view.findViewById(R.id.img_itemheadline_bookmark);
            img_share=view.findViewById(R.id.img_itemheadline_share);
            img_toppic=view.findViewById(R.id.img_itemheadline_topimg);
            img_pic=view.findViewById(R.id.img_itemheadline);
            crd_top=view.findViewById(R.id.crd_itemheadline_top);
            lay_side_img=view.findViewById(R.id.lay_itemtophead);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topheadline, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int i) {

        holder.txt_title.setText(String.valueOf(model_headlines.get(i).getTitle()));
        holder.txt_time.setText(AppConstants.getDateToTimeFormat(model_headlines.get(i).getPublishedAt()));
        holder.txt_source.setText(String.valueOf(model_headlines.get(i).getSource().getName()));
        if(i==0){
            Picasso.get().load(model_headlines.get(i).getUrlToImage()).error(R.drawable.icon).into(holder.img_toppic);
            holder.crd_top.setVisibility(View.VISIBLE);
            holder.lay_side_img.setVisibility(View.GONE);
        }else{
            Picasso.get().load(model_headlines.get(i).getUrlToImage()).error(R.drawable.icon).into(holder.img_pic);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model_headlines.get(i).getUrl()));
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
        return model_headlines.size();
    }


}

