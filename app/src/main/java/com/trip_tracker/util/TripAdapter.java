package com.trip_tracker.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.trip_tracker.R;
import java.util.List;

public class TripAdapter extends ArrayAdapter<Trip> {
    public TripAdapter(Context context, List<Trip> trips){
        super(context, 0 , trips);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trip trip = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_item, parent, false);
        }
        String places = trip.getStartLocation() + " - " + trip.getFinishLocation() + "  Date: " + trip.getDate();
        TextView item_places =  convertView.findViewById(R.id.item_destination_STA);
        item_places.setText(places);
        return convertView;
    }
}
