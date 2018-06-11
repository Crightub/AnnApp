package de.tk.annapp.Fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.tk.annapp.News;
import de.tk.annapp.R;
import de.tk.annapp.Recycler.RVAdapterNews;
import de.tk.annapp.SubjectManager;

/**
 * Created by Tobi on 20.09.2017.
 */

public class AnnanewsFragment extends Fragment {
    private View root;
    private SwipeRefreshLayout mSwipeLayout;
    private ArrayList<News> mFeedModelList;
    private SubjectManager subjectManager;

    public static final String TAG = "AnnanewsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("AnnaNews");
        root = inflater.inflate(R.layout.fragment_annanews, container, false);
        RecyclerView rv = root.findViewById(R.id.rv_news);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new RVAdapterNews(getContext()));
        mSwipeLayout = root.findViewById(R.id.swipeRefreshLayout);
        subjectManager = SubjectManager.getInstance();

        new FetchFeedTask().execute((Void) null);

        //TODO nur zum testen
        //subjectManager.addNews(new News("Supernatural", "link", "Ach ja… Sam und Dean Winchester, wer kennt die Beiden nicht? Zugegeben, die meisten unserer jüngeren Leser sollten die beiden tatsächlich nicht kennen, immerhin ist die Serie, in der die beiden eher ungleichen Brüder die Hauptrolle spielen, erst ab 16 freigegeben. Das ist wahrscheinlich auch besser so, denn Supernatural, so heißt die Serie, handelt von eben diesen Brüdern, die, seit ihre Mutter  von einem bösartigen Dämon getötet wurde, zusammen auf Dämonenjagd gehen, mit einem einzigen Ziel: Den Mörder ihrer Mutter zu finden und zu töten. Das allerdings gestaltet sich als schwierig, da ihr Vater, der die Beiden ausgebildet hat, spurlos verschwunden zu sein scheint.  Auf der Suche nach ihrem Vater und dem Dämon treffen sie auf einige „interessante“ Wesen, von Geistern bis zu Vampiren, die sie alle mehr oder weniger glamourös besiegen und damit nicht immer die Welt, aber zumindest einige amerikanische Kleinstädte vor dem Untergang retten.  Leider haben die beiden immer wieder mit Spannungen innerhalb ihrer kleinen Gruppe zu kämpfen, da Sam, der schon in seiner Jugend Probleme mit seinem Vater hatte, seinem Bruder immer wieder  vorwürft, zu loyal dem Vater gegenüber zu sein. In den späteren Staffeln wird die ganze Geschichte dann etwas größer und aufgebauschter, so schließt sich den beiden zum Beispiel ein Engel an, Dean verbringt 40 Jahre in der Hölle und sogar mit Gott knüpfen sie Kontakte.", getResources().getDrawable(R.drawable.supernatural)));


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });


        return root;
    }


    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            urlLink = "http://gym-anna.de/wordpress/?feed=rss2";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                subjectManager.mergeNews(parseFeed(inputStream));
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    private String xmlcut(String content, String startTag, String endTag){
        return content.substring(0,content.indexOf(startTag))+content.substring(content.indexOf(endTag,content.indexOf(startTag))+endTag.length(),content.length());
    }

    private String xmlget(String content, String startTag, String endTag){
        String ret = content.substring(content.indexOf(startTag)+startTag.length());
        return ret.substring(0,ret.indexOf(endTag));
    }

    public ArrayList<News> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        /*String title = null;
        String link = null;
        String description = null;
        Drawable image = null;
        boolean isItem = false;*/
        ArrayList<News> items = new ArrayList<>();
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        String content =  s.hasNext() ? s.next() : "";
        content = content.replaceAll("\n","");
        content = htmlToString(content);
        inputStream.close();
        while (content.contains("<item>")){
            String item = xmlget(content,"<item>","</item>");
            content = content.substring(content.indexOf("</item>")+"</item>".length());
            String title = xmlget(item,"<title>","</title>");
            String link = xmlget(item,"<link>","</link>");
            String description = xmlget(item,"<description><![CDATA[","]]></description>");
            String imageurl = item.contains("<content:encoded><![CDATA[<p><a href=\"") ? xmlget(item,"<content:encoded><![CDATA[<p><a href=\"","\">"):null;
            String article = xmlget(item,"<content:encoded><![CDATA[","]]></content:encoded>");
            article = article.replaceAll("</p><p>","\n\n");
            while (article.contains("<a"))
                article = xmlcut(article, "<a","</a>");
            while (article.contains("<span"))
                article = xmlcut(article, "<span","n></span>");
            article = article.replaceAll("<p>","");
            article = article.replaceAll("</p>","");
            while (article.contains("<p "))
                article = xmlcut(article,"<p ",">");
            Drawable image = SubjectManager.getInstance().getFromURl(imageurl);
            items.add(new News(title,link,description,article,imageurl,image));
        }
        return items;


        /*try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {

                        //TODO Testing
                        News item = new News(title, link, description, image);
                        items.add(item);

                        title = null;
                        link = null;
                        description = null;

                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                //Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = htmlToString(result);
                    System.out.println(result);
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                    System.out.println(result);
                } else if (name.equalsIgnoreCase("description")) {
                    description = htmlToString(result);
                    System.out.println(result);
                } else if (name.equalsIgnoreCase("content:encoded")) {
                    result = result.replace("<p><a href=\"", "");
                    String[] results = result.split("\\\"");
                    result = results[0];
                    System.out.println(result);
                    image = drawableFromUrl(result);
                }

                if (title != null && link != null && description != null && false) {
                    if (isItem) {
                        News item = new News(title, link, description, image);
                        items.add(item);
                    } else {
                        //mFeedTitle = title;
                        //mFeedLink = link;
                        //mFeedDescription = description;
                    }
                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }*/
    }

    public String htmlToString(String string) {
        String edit;
        edit = string.replaceAll("&#8220;", "“");
        edit = edit.replaceAll("&#8221;", "”");
        edit = edit.replaceAll("&#8216;", "‘");
        edit = edit.replaceAll("&#8217;", "’");
        edit = edit.replaceAll("&#8230;", "...");
        edit = edit.replaceAll("&nbsp;", "");
        edit = edit.replaceAll("&#8211;", "–");

        return edit;
    }
}

