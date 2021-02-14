package taarak.gloify.headlines.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.AppConstants;
import taarak.gloify.headlines.Model.Model_Article;
import taarak.gloify.headlines.R;

public class Adapter_Briefnews extends RecyclerView.Adapter<Adapter_Briefnews.MyViewHolder> {
    Context context;
    List<Model_Article> model_breifing;
    LayoutInflater layoutInflater;

public Adapter_Briefnews(Context context, List<Model_Article> model_breifing) {
    this.context = context;
    this.model_breifing=model_breifing;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_breifing, parent, false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

    holder.txt_title.setText(model_breifing.get(position).getTitle());
    holder.txt_source.setText(model_breifing.get(position).getSource().getName());
    holder.txt_time.setText(AppConstants.getDateToTimeFormat(model_breifing.get(position).getPublishedAt()));
    Picasso.get().load(model_breifing.get(position).getUrlToImage()).error(R.drawable.icon).into(holder.img_pic);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model_breifing.get(position).getUrl()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            layoutInflater.getContext().startActivity(intent);
        }
    });
}

@Override
public int getItemCount() {
        return model_breifing.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView txt_title,txt_time,txt_source;
    ImageView img_pic,img_bookmark,img_share;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title=itemView.findViewById(R.id.txt_itembreifing_title);
        txt_time=itemView.findViewById(R.id.txt_itembreifing_time);
        txt_source=itemView.findViewById(R.id.txt_itembreifing_source);
        img_pic=itemView.findViewById(R.id.img_itembreifing_main);
        img_bookmark=itemView.findViewById(R.id.img_itemheadline_bookmark);
        img_share=itemView.findViewById(R.id.img_itemheadline_share);
    }
}
}