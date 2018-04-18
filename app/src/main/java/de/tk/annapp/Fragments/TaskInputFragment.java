package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tk.annapp.R;
/**
 * Created by Jakob on 03.01.2018.
 */

public class TaskInputFragment extends Fragment{
    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.addTask));
        root = inflater.inflate(R.layout.dialog_new_task, container, false);
        return root;
    }
}
