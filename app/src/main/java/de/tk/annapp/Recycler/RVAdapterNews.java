package de.tk.annapp.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.tk.annapp.News;
import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;

/**
 * Created by Petrus on 28.03.2018.
 */

public class RVAdapterNews extends RecyclerView.Adapter<RVAdapterNews.NewsViewHolder>{

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
        News news = subjectManager.getNews(position);
        holder.titel.setText(news.getTitle());
        holder.discription.setText(news.getDiscription());
        holder.image.setImageDrawable(news.getImage());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Clicked ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectManager.getNewsCount();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titel;
        TextView discription;
        ImageView image;
        ImageButton btn;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_news_card);
            titel = itemView.findViewById(R.id.card_news_titel);
            discription = itemView.findViewById(R.id.card_news_discription);
            image = itemView.findViewById(R.id.card_news_image);
            btn = itemView.findViewById(R.id.card_news_btn);
        }
    }

}
