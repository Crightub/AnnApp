package de.tk.annapp.Recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.tk.annapp.Fragments.NewsDetailActivity;
import de.tk.annapp.News;
import de.tk.annapp.R;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Util;


public class RVAdapterNews extends RecyclerView.Adapter<RVAdapterNews.NewsViewHolder> {

    Context context;
    SubjectManager subjectManager;

    public RVAdapterNews(Context context) {
        this.context = context;
        subjectManager = SubjectManager.getInstance();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = subjectManager.getOneNews(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDiscription());
        holder.image.setImageDrawable(news.getImage());



        if (news.getImage() == null)
            holder.image.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title", holder.title.getText().toString());
                intent.putExtra("text", holder.description.getText().toString());

                intent.putExtra("colorPrimary", Util.getColorPrimary(context));
                intent.putExtra("colorPrimaryDark", Util.getColorPrimaryDark(context));

                context.startActivity(intent);

            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO do menu stuff
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectManager.getNewsCount();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        ImageView image;
        ImageButton btn;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_news_card);
            title = itemView.findViewById(R.id.card_news_titel);
            description = itemView.findViewById(R.id.card_news_discription);
            image = itemView.findViewById(R.id.card_news_image);
            btn = itemView.findViewById(R.id.card_news_btn);
        }
    }

}
