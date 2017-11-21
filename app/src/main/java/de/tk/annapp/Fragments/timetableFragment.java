package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tk.annapp.R;

/**
 * Created by Tobi on 20.09.2017.
 */

public class timetableFragment extends Fragment  {
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Stundenplan");
        root = inflater.inflate(R.layout.fragment_timetable, container, false);
        return root;
    }
}
