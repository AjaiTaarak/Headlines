package taarak.gloify.headlines.Adapters;

import android.content.Context;
import android.content.Intent;
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

public class Adapter_Shorts extends RecyclerView.Adapter<Adapter_Shorts.MyViewHolder> {
    Context context;
    List<Model_Article> model_breifing;
    LayoutInflater layoutInflater;

public Adapter_Shorts(Context context, List<Model_Article> model_breifing) {
    this.context = context;
    this.model_breifing=model_breifing;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shorts, parent, false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

    holder.txt_title.setText(model_breifing.get(position).getTitle());
    holder.txt_content.setText(model_breifing.get(position).getDescription());
    holder.txt_source.setText(model_breifing.get(position).getSource().getName());
    holder.txt_time.setText(AppConstants.getDateToTimeFormat(model_breifing.get(position).getPublishedAt()));
    Picasso.get().load(model_breifing.get(position).getUrlToImage()).error(R.drawable.icon).into(holder.img_pic);
    holder.btn_readmore.setOnClickListener(new View.OnClickListener() {
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
    TextView txt_title,txt_time,txt_source,txt_content;
    ImageView img_pic;
    Button btn_readmore;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title=itemView.findViewById(R.id.txt_itemshorts_title);
        txt_time=itemView.findViewById(R.id.txt_itemshorts_date);
        txt_source=itemView.findViewById(R.id.txt_itemshorts_source);
        img_pic=itemView.findViewById(R.id.img_itemshorts_pic);
        btn_readmore=itemView.findViewById(R.id.btn_itemshorts_readmore);
        txt_content=itemView.findViewById(R.id.txt_itemshorts_content);
    }
}
}