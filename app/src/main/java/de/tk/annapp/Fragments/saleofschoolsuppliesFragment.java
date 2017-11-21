
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

public class saleofschoolsuppliesFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Schulsachenverkauf");
        root = inflater.inflate(R.layout.fragment_saleofschoolsupplies, container, false);
        return root;
    }
}

