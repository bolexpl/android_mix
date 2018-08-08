package com.example.bolek.testy.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolek.testy.activities.MusicPlayerActivity;
import com.example.bolek.testy.adapters.FileAdapter;
import com.example.bolek.testy.models.FileItem;
import com.example.bolek.testy.R;

public class FilesFragment extends Fragment {

    ListView listaKomponent;
    FileAdapter adapter;
    String katalog = Environment.getExternalStorageDirectory().getAbsolutePath();

    public FilesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        final TextView t = (TextView) getActivity().findViewById(R.id.space);
        final EditText edit = (EditText) getActivity().findViewById(R.id.directory);
        Button bt = (Button) getActivity().findViewById(R.id.enter);

        edit.setText(katalog);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                katalog = edit.getText().toString();

                if ((new File(katalog)).isDirectory()) {
                    refresh(katalog, edit);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Nie ma takiego katalogu.", Toast.LENGTH_LONG).show();
                }
            }
        });

        StatFs stat = new StatFs(katalog);
        long miejsce;

        if (android.os.Build.VERSION.SDK_INT >= 18) {
            miejsce = stat.getBlockSizeLong() * stat.getBlockCountLong();
        } else {
            miejsce = (long) stat.getBlockSize() * stat.getBlockCount();
        }

        t.setText("Wolne miejsce: " + miejsce / 1024 / 1024 + " MB");

        refresh(katalog, edit);
    }

    public void refresh(final String directory, final EditText edit) {
        listaKomponent = (ListView) getActivity().findViewById(R.id.lista);
        File plik = new File(directory);
        String[] elementy = plik.list();
        final ArrayList<FileItem> listaArray = new ArrayList<>();

        if (!directory.equals("/")) {
            listaArray.add(new FileItem("..", "", 'd'));
        }
        if (elementy == null) {
            Toast.makeText(getActivity(), "Folder jest pusty", Toast.LENGTH_SHORT).show();
        } else {
            char type;
            for (String elem : elementy) {
                File f = new File(directory + "/" + elem);
                String attr = "";
                if (f.isDirectory()) {
                    attr += " d";
                    type = 'd';
                } else {
                    attr += " f";
                    type = 'f';
                }
                if (f.canRead()) {
                    attr += "r";
                } else {
                    attr += "-";
                }
                if (f.canWrite()) {
                    attr += "w";
                } else {
                    attr += "-";
                }
                if (f.canExecute()) {
                    attr += "x";
                } else {
                    attr += "-";
                }
                Date d = new Date();
                d.setTime(f.lastModified());
                attr += "  (S: " + f.length() / 1024 + "KB, LM: " + d + ")";
                listaArray.add(new FileItem(elem, attr, type));
            }
        }

        listaKomponent.setOnItemClickListener(new MyListener(directory, edit, listaArray));

        adapter = new FileAdapter(getActivity().getApplicationContext(), listaArray);
        listaKomponent.setAdapter(adapter);
    }

    class MyListener implements AdapterView.OnItemClickListener {
        String directory;
        EditText edit;
        ArrayList<FileItem> listaArray;

        private MyListener(String dir, EditText ed, ArrayList<FileItem> lista) {
            directory = dir;
            edit = ed;
            listaArray = lista;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0 && !directory.equals("/")) {
                String tab[] = directory.split("/");
                directory = directory.replace("/" + tab[tab.length - 1], "");
                if (directory.equals("")) {
                    directory = "/";
                }
            } else {
                File f = new File(directory + "/" + listaArray.get(position).getName());
                if (f.isDirectory()) {
                    if (!directory.equals("/")) {
                        directory += "/" + listaArray.get(position).getName();
                    } else {
                        directory += listaArray.get(position).getName();
                    }
                } else {
                    Intent i = new Intent(getActivity(), MusicPlayerActivity.class);
                    i.putExtra("file", f);
                    startActivity(i);
                }
            }
            edit.setText(directory);
            refresh(directory, edit);
        }
    }
}
