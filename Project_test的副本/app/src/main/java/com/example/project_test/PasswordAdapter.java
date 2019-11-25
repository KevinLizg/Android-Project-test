package com.example.project_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PasswordAdapter extends ArrayAdapter<Passw> {

    private int resourceId;

    private Context context;

    public PasswordAdapter(Context context, int resource, List<Passw> objects) {
        super(context, resource,objects);
        resourceId=resource;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Passw pass=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView set=(TextView) view.findViewById(R.id.set_dis);
        set.setText(pass.getDis());
        return view;
    }
}
