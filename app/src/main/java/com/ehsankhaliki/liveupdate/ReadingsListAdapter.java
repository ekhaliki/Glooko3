package com.ehsankhaliki.liveupdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This adapter populates the list view
 * Created by ekhaliki on 4/29/15.
 */
public class ReadingsListAdapter extends ArrayAdapter {

    private int preLast=-1;  //used to stop re-toasting last position
    private GlucoseReadingModel[] readings;
    private TextView topText;
    private AbsListView listView;

    public ReadingsListAdapter(Context context, int resource, GlucoseReadingModel[] objects, TextView topTextView, AbsListView listView) {
        super(context, resource, objects);
        readings =  objects;
        topText = topTextView;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.readings_list_item_layout, null);
        }

        if(readings[position] == null) return convertView;

        toastLastPosition();
        String date = readings[position].timestamp.substring(0, 10);
        String time = readings[position].timestamp.substring(11);

        TextView dateText = (TextView) convertView.findViewById(R.id.readingItemDateValue);
        TextView readingText = (TextView) convertView.findViewById(R.id.readingItemBgValue);
        TextView timeText = (TextView) convertView.findViewById(R.id.readingItemTimeValue);

        dateText.setText(date);
        readingText.setText(Integer.toString(readings[position].value));
        timeText.setText(time);

        return convertView;
    }

    /**
     * used to make a toast when reached end of list view
     */
    private void toastLastPosition() {
        listView.post(new Runnable() {

            @Override
            public void run() {
                GlucoseReadingModel first = readings[listView.getFirstVisiblePosition()];
                GlucoseReadingModel last = readings[listView.getLastVisiblePosition()];
                topText.setText(first.timestamp.substring(0,10) + " - " + last.timestamp.substring(0,10));
                int lastPositionVisible = listView.getLastVisiblePosition();
                if(preLast!= lastPositionVisible) {
                    if (lastPositionVisible == readings.length - 1) {
                        Toast.makeText(getContext(), "Bottom Of List Reached!", Toast.LENGTH_SHORT).show();
                    }
                    preLast = lastPositionVisible;
                }
            }
        });
    }
}
