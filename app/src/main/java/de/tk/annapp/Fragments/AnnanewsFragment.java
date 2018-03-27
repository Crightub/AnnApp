
package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tk.annapp.R;

/**
 * Created by Tobi on 20.09.2017.
 */

public class AnnanewsFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("AnnaNews");
        root = inflater.inflate(R.layout.fragment_annanews, container, false);
        RecyclerView rv = root.findViewById(R.id.rv_news);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }
}

