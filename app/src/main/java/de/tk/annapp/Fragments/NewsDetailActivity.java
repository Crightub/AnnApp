package de.tk.annapp.Fragments;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.tk.annapp.R;
import de.tk.annapp.Util;

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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(bundle.getInt("colorPrimary"));

        //Change the color on top of the toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = NewsDetailActivity.this.getWindow();
            window.setStatusBarColor(bundle.getInt("colorPrimaryDark"));
        }

        this.setTitle(bundle.getString("title"));
        this.setTitleColor(android.R.color.white);


        linearLayout = findViewById(R.id.linearLayout);

        textView.setText(bundle.getString("text"));
    }


}
