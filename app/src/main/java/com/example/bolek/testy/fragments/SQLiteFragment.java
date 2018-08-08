package com.example.bolek.testy.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bolek.testy.models.BaseManager;
import com.example.bolek.testy.R;

public class SQLiteFragment extends Fragment {


    public SQLiteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sqlite, container, false);
    }

    Button bt;
    Button deleteButton;
    Button editButton;
    BaseManager db;
    TextView tv;
    EditText text1;
    EditText text2;
    EditText deleteEdit;
    Cursor k;

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        tv = (TextView) getActivity().findViewById(R.id.textView3);
        text1 = (EditText) getActivity().findViewById(R.id.editText);
        text2 = (EditText) getActivity().findViewById(R.id.editText2);
        deleteEdit = (EditText) getActivity().findViewById(R.id.deleteEdit);
        bt = (Button) getActivity().findViewById(R.id.button);
        deleteButton = (Button) getActivity().findViewById(R.id.deleteButton);
        editButton = (Button) getActivity().findViewById(R.id.editButton);

        refresh();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = text1.getText().toString();
                int b = Integer.parseInt(text2.getText().toString());
                db.addOne(a, b);
                refresh();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(deleteEdit.getText().toString());
                db.deleteOne(id);
                refresh();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = text1.getText().toString();
                int b = Integer.parseInt(text2.getText().toString());
                int id = Integer.parseInt(deleteEdit.getText().toString());
                db.updateOne(id,a,b);

                refresh();
            }
        });
    }

    public void refresh(){
        tv.setText("Dane z bazy:");
        db = new BaseManager(getContext());
        k = db.getAll();
        while (k.moveToNext()) {
            int id = k.getInt(0);
            String name = k.getString(1);
            int number = k.getInt(2);
            tv.setText(tv.getText() + "\n" + id + " " + name + " " + number);
        }
    }

}
