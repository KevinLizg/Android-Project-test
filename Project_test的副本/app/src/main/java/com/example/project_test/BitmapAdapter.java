package com.example.project_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BitmapAdapter extends ArrayAdapter<Bm> {

    private int resourceId;

//    private Context context;

    public BitmapAdapter(Context context, int resource, List<Bm> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bm test = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//        TextView set=(TextView) view.findViewById(R.id.shit);
//        ImageView bitmap = view.findViewById(R.id.shit);
        TextView tag = view.findViewById(R.id.get_tag);
//        bitmap.setImageBitmap(test.getImage());
        tag.setText(test.getTag());
        return view;
    }
}