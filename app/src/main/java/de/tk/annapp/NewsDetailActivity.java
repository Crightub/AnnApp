package de.tk.annapp;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsDetailActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageViewToolbar;
    AppBarLayout appBarLayout;
    News news;
    int colorPrimary;
    int colorAccent;
    int colorPrimaryDark;
    int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        this.setTheme(R.style.AppThemeColorful);
        System.out.println("colorSchemePosition: "+bundle.getInt("colorSchemePosition"));
        switch (bundle.getInt("colorSchemePosition")){
            case 0:
                //TODO nur zum Anzeigen
                //setTheme(R.style.Tim);
                break;
            case 1:
                setTheme(R.style.AppThemeOrange);
                break;
            case 2:
                setTheme(R.style.AppThemeBlue);
                break;
            case 3:
                setTheme(R.style.AppThemeColorful);
        }
        setContentView(R.layout.activity_news_detail);

        textView = this.findViewById(R.id.textViewText);
        imageViewToolbar = this.findViewById(R.id.imageViewToolbar);
        appBarLayout = this.findViewById(R.id.appbar);

        System.out.println("onCreateView");

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        colorPrimary=typedValue.data;
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        colorPrimaryDark=typedValue.data;
        getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        colorAccent=typedValue.data;
        getTheme().resolveAttribute(R.attr.defaultTextColor, typedValue, true);
        defaultTextColor=typedValue.data;



        news = (News) bundle.getSerializable("news");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getColor(android.R.color.white));
        setSupportActionBar(toolbar);


        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        /*collapsingToolbarLayout.setContentScrimColor(colorPrimary);
        appBarLayout.setBackgroundColor(colorPrimary);*/

        //Change the color on top of the toolbar
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = NewsDetailActivity.this.getWindow();
            window.setStatusBarColor(colorPrimaryDark);
        }*/

        this.setTitle(news.getTitle());

        imageViewToolbar.setImageDrawable(SubjectManager.getInstance().getFromURl(news.getImageurl()));

        textView.setText(news.getArticle());
        System.out.println(news.getArticle());


    }


}
