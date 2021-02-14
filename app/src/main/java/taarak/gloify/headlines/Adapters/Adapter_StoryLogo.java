package taarak.gloify.headlines.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import taarak.gloify.headlines.R;
import taarak.gloify.headlines.Storyactivity;

public class Adapter_StoryLogo extends RecyclerView.Adapter<Adapter_StoryLogo.SimpleViewHolder>{

    int images[];
    String channelnames[];
    LayoutInflater inflter;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    Activity lastactivity;

    public Adapter_StoryLogo(Context applicationContext, int images[], String channelnames[], Activity lastactivity) {
        this.images=images;
        this.channelnames=channelnames;
        this.lastactivity=lastactivity;
        inflter = (LayoutInflater.from(applicationContext));
        sp=inflter.getContext().getSharedPreferences("Key",inflter.getContext().MODE_PRIVATE);
        edt=sp.edit();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        ImageView img_pic;


        public SimpleViewHolder(View view) {
            super(view);

            img_pic=view.findViewById(R.id.img_story_channellogo);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storyholder, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int i) {

        holder.img_pic.setBackgroundResource(images[i]);
        holder.img_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edt.putString("SEL_CHANNEL",channelnames[i]);
                edt.apply();
                edt.commit();
                inflter.getContext().startActivity(new Intent(inflter.getContext(), Storyactivity.class));
                lastactivity.overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

}