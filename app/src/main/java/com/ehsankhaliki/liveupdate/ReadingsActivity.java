package com.ehsankhaliki.liveupdate;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * main activity
 */
public class ReadingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ReadingsListAdapter adapter;
        private ArrayList<GlucoseReadingModel> readings;
        private AbsListView mListView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_readings, container, false);

            GlucoseReadingsFactory parser = new GlucoseReadingsFactory("JohnDoe", getActivity());
            readings = parser.getReadingsController().getReadingsList();
            GlucoseReadingModel[] objectsPlaceHolder = new GlucoseReadingModel[readings.size()];
            mListView = (AbsListView) rootView.findViewById(R.id.listView);
            adapter = new ReadingsListAdapter(getActivity().getApplicationContext(),R.id.readingItemBgLevelLabel,
                    readings.toArray(objectsPlaceHolder), (TextView)rootView.findViewById(R.id.dateSpan),
                    mListView);
            mListView.setAdapter(adapter);

            return rootView;
        }
    }
}
