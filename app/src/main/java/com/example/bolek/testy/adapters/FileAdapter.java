package com.example.bolek.testy.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bolek.testy.models.FileItem;
import com.example.bolek.testy.R;

import java.util.ArrayList;

public class FileAdapter extends ArrayAdapter<FileItem> {
    Context context;

    public FileAdapter(Context context, ArrayList<FileItem> files) {
        super(context, 0, files);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileItem f = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.file_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView attr = (TextView) convertView.findViewById(R.id.attr);

        if(f.getType() == 'd'){
            name.setTextColor(ContextCompat.getColor(context,R.color.blue));
        }else{
            name.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

        name.setText(f.getName());
        attr.setText(f.getAttr());
        return convertView;
    }
}
