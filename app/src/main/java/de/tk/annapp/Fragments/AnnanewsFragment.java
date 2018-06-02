package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tk.annapp.News;
import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterNews;
import de.tk.annapp.SubjectManager;

/**
 * Created by Tobi on 20.09.2017.
 */

public class AnnanewsFragment extends Fragment {
    View root;

    public static final String TAG = "AnnanewsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("AnnaNews");
        root = inflater.inflate(R.layout.fragment_annanews, container, false);
        RecyclerView rv = root.findViewById(R.id.rv_news);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new RVAdapterNews(getContext()));

        //TODO nur zum testen
        SubjectManager subjectManager = SubjectManager.getInstance();
        subjectManager.addNews(new News("Supernatural", "Ach ja… Sam und Dean Winchester, wer kennt die Beiden nicht? Zugegeben, die meisten unserer jüngeren Leser sollten die beiden tatsächlich nicht kennen, immerhin ist die Serie, in der die beiden eher ungleichen Brüder die Hauptrolle spielen, erst ab 16 freigegeben. Das ist wahrscheinlich auch besser so, denn Supernatural, so heißt die Serie, handelt von eben diesen Brüdern, die, seit ihre Mutter  von einem bösartigen Dämon getötet wurde, zusammen auf Dämonenjagd gehen, mit einem einzigen Ziel: Den Mörder ihrer Mutter zu finden und zu töten. Das allerdings gestaltet sich als schwierig, da ihr Vater, der die Beiden ausgebildet hat, spurlos verschwunden zu sein scheint.  Auf der Suche nach ihrem Vater und dem Dämon treffen sie auf einige „interessante“ Wesen, von Geistern bis zu Vampiren, die sie alle mehr oder weniger glamourös besiegen und damit nicht immer die Welt, aber zumindest einige amerikanische Kleinstädte vor dem Untergang retten.  Leider haben die beiden immer wieder mit Spannungen innerhalb ihrer kleinen Gruppe zu kämpfen, da Sam, der schon in seiner Jugend Probleme mit seinem Vater hatte, seinem Bruder immer wieder  vorwürft, zu loyal dem Vater gegenüber zu sein. In den späteren Staffeln wird die ganze Geschichte dann etwas größer und aufgebauschter, so schließt sich den beiden zum Beispiel ein Engel an, Dean verbringt 40 Jahre in der Hölle und sogar mit Gott knüpfen sie Kontakte.", getResources().getDrawable(R.drawable.supernatural)));

        return root;
    }
}

