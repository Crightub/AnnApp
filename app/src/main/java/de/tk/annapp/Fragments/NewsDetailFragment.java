package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import de.tk.annapp.R;

public class NewsDetailFragment extends Fragment {
    View root;
    String title;
    TextView tvTitle;
    String text;
    LinearLayout linearLayout;

    public static final String TAG = "NewsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("onCreateView");
        getActivity().setTitle("News");
        root = inflater.inflate(R.layout.fragment_news_detail, container, false);

        Bundle bundle = this.getArguments();
        System.out.println(tvTitle.toString() + "      " + bundle.get("title"));


        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle(bundle.getString("title"));

        tvTitle = (TextView) root.findViewById(R.id.textViewTitle);


        tvTitle.setText(bundle.getString("title"));


        linearLayout = root.findViewById(R.id.linearLayout);

        setText(bundle.getString("text"));

        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new AnnanewsFragment());
                    return true;
                }
                return false;
            }
        });


        return root;
    }

    public void setTitle(String title) {
        System.out.println("setTitle");
        this.title = title;
        tvTitle.setText(title);
    }

    public void setText(String text) {
        this.text = text;

    }


}