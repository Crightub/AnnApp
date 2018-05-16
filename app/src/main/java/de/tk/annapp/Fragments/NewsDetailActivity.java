package de.tk.annapp.Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.tk.annapp.R;

public class NewsDetailActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        textView = this.findViewById(R.id.textViewText);

        System.out.println("onCreateView");

        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle(bundle.getString("title"));
        this.setTitleColor(android.R.color.white);


        linearLayout = findViewById(R.id.linearLayout);

        textView.setText(bundle.getString("text"));
    }


}
