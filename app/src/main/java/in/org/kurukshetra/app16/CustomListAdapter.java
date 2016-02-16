package in.org.kurukshetra.app16;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;

    public CustomListAdapter(Activity activity, List<Movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView event = (TextView) convertView.findViewById(R.id.event);
        TextView round = (TextView) convertView.findViewById(R.id.round);
        TextView posi = (TextView) convertView.findViewById(R.id.position);


        // getting movie data for the row
        Movie m = movieItems.get(position);

        // title
        event.setText(m.getTitle());

        // round
        round.setText(m.getThumbnailUrl());

        // position
        posi.setText(m.getYear());

        return convertView;
    }

}