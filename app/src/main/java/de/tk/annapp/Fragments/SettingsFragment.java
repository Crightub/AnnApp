
package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.tk.annapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tobi on 20.09.2017.
 */

public class SettingsFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(R.string.settings);
        root = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText theme = (EditText) root.findViewById(R.id.theme);
        Button btnTheme = (Button) root.findViewById(R.id.btnTheme);

        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theme.getText().toString().equals("green"))
                    getContext().setTheme(R.style.AppTheme_green);
                else
                    getContext().setTheme(R.style.AppTheme);

                getActivity().recreate();

                SharedPreferences.Editor editor = getContext().getSharedPreferences("prefs", MODE_PRIVATE).edit();
                editor.putString("theme", theme.getText().toString());
                editor.apply();
            }
        });

        return root;
    }
}

