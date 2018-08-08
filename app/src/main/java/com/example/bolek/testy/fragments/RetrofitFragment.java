package com.example.bolek.testy.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.Toast;

import com.example.bolek.testy.adapters.ChaptersAdapter;
import com.example.bolek.testy.interfaces.WebService;
import com.example.bolek.testy.pojo.Chapter;
import com.example.bolek.testy.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class RetrofitFragment extends Fragment {
    private List<Chapter> chapterList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChaptersAdapter mAdapter;

    RestAdapter retrofit;
    WebService webService;
    SwipeRefreshLayout refreshLayout;

    public RetrofitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retrofit, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);



        RecyclerView.LayoutManager mLayoutManager;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        //sprawdzanie rozmiaru ekranu
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 5);
                } else {
                    mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 4);
                }
                break;
            default:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 4);
                } else {
                    mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                }
        }

        mAdapter = new ChaptersAdapter(chapterList, getContext(),recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Log.i("aaaaaaaaaaaa", "startup");
        prepareData(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("aaaaaaaaaaaa", "onRefresh called from SwipeRefreshLayout");
                prepareData(true);
            }
        });
    }

    private void prepareData(final boolean refresh) {

        retrofit = new RestAdapter.Builder()
                .setEndpoint("http://bolex.cba.pl/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        webService = retrofit.create(WebService.class);

        try {
            webService.getData(new Callback<Chapter[]>() {

                @Override
                public void success(Chapter[] data, Response response) {
                    if (refresh) {
                        chapterList.clear();
                    }

                    for (Chapter item : data) {
                        chapterList.add(item);
                    }
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Rest", error.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.d("Rest", e.toString());
        }
    }
}
