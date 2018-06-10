package de.tk.annapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        textView = this.findViewById(R.id.textViewText);
        imageViewToolbar = this.findViewById(R.id.imageViewToolbar);
        appBarLayout = this.findViewById(R.id.appbar);

        System.out.println("onCreateView");

        Bundle bundle = getIntent().getExtras();

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        TypedValue colorPrimary = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, colorPrimary, true);

        collapsingToolbarLayout.setContentScrimColor(colorPrimary.data);
        appBarLayout.setBackgroundColor(colorPrimary.data);

        TypedValue colorPrimaryDark = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);

        //Change the color on top of the toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = NewsDetailActivity.this.getWindow();
            window.setStatusBarColor(colorPrimaryDark.data);
        }

        this.setTitle(bundle.getString("title"));
        this.setTitleColor(android.R.color.white);

        textView.setText(bundle.getString("text"));
        System.out.println(bundle.getString("text"));


    }


}
