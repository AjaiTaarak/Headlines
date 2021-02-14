package taarak.gloify.headlines.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.R;
import taarak.gloify.headlines.SetupActivity2;

public class Adapter_topics extends RecyclerView.Adapter<Adapter_topics.SimpleViewHolder>{


    int flags;
    List<String> model_topics;
    LayoutInflater inflter;
    SharedPreferences sp;
    SharedPreferences.Editor edt;


    public Adapter_topics(Context applicationContext, List<String> model_topics) {


        this.model_topics =model_topics;
        this.flags=model_topics.size();

        inflter = (LayoutInflater.from(applicationContext));
        sp=inflter.getContext().getSharedPreferences("Key",inflter.getContext().MODE_PRIVATE);
        edt=sp.edit();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView txt_topic;

        public SimpleViewHolder(View view) {
            super(view);

            txt_topic =view.findViewById(R.id.btn_itemtopicnames);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topicnames, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int i) {

        holder.txt_topic.setText("#"+model_topics.get(i));

        holder.txt_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!sp.getBoolean(model_topics.get(i),false))
                {

                    edt.putBoolean(model_topics.get(i),true);
                    edt.putInt("SEL_TOPICS_COUNT",(1+sp.getInt("SEL_TOPICS_COUNT",0)));
                    edt.apply();
                    edt.commit();
                    SetupActivity2.triggerButton();

                    Drawable img = inflter.getContext().getResources().getDrawable(R.drawable.ic_check_black_24dp);
                    img.setBounds(0, 0, 60, 60);
                    holder.txt_topic.setCompoundDrawables(null,null,img,null);

                    holder.txt_topic.setCompoundDrawablePadding(5);
                    holder.txt_topic.setBackground(inflter.getContext().getResources().getDrawable(R.drawable.chip_nonselected));

                }else{
                    edt.putBoolean(model_topics.get(i),false);
                    edt.putInt("SEL_TOPICS_COUNT",(sp.getInt("SEL_TOPICS_COUNT",0)-1));
                    edt.apply();
                    edt.commit();
                    SetupActivity2.triggerButton();
                    holder.txt_topic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    holder.txt_topic.setBackground(inflter.getContext().getResources().getDrawable(R.drawable.chip_selected));

                }
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

