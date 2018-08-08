package com.example.bolek.testy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import com.example.bolek.testy.pojo.Chapter;
import com.example.bolek.testy.R;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.MyViewHolder> {
    private List<Chapter> chapterList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, number, title;
        private ImageView cover;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            cover = (ImageView) view.findViewById(R.id.cover);
            number = (TextView) view.findViewById(R.id.number);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public ChaptersAdapter(List<Chapter> chapterList, Context mContext,RecyclerView recycler) {
        this.chapterList = chapterList;
        this.mContext = mContext;
        mRecyclerView = recycler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_list_item, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mRecyclerView.getChildAdapterPosition(view);
//                Toast.makeText(mContext,Integer.toString(position),Toast.LENGTH_SHORT).show();
                String a = chapterList.get(position).getTitle();
                Toast.makeText(mContext,a,Toast.LENGTH_SHORT).show();

            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.title.setText(chapter.getTitle());
        if(chapter.getDate() == null){
            Log.d("aaaaaaaaaaaaaaaaaaaaa","null");
        }
        Glide.with(mContext).load("https://novelki.pl/uploads/"+chapter.getImage())
                .thumbnail(0.5f)
                .error(R.drawable.no_foto)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cover);
        holder.date.setText(chapter.getDate());
        holder.number.setText("Tom " + chapter.getTomNumber() + " chapter " + chapter.getChapterNumber());
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
}