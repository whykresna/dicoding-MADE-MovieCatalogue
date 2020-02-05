package com.example.moviecatalogue;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Item> items = new ArrayList<>();
    private ProgressBar progressBar;
    private ListItemAdapter listItemAdapter;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar);

        showRecyclerList();

        return view;
    }

    private void showRecyclerList(){
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItemAdapter = new ListItemAdapter(items);
        listItemAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listItemAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        mainViewModel.setTvShow();

        showLoading(true);

        listItemAdapter.setOnItemClickCallback(new ListItemAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Item item) {
                showSelectedTvShow(item);
            }
        });

        mainViewModel.getItem().observe(Objects.requireNonNull(getActivity()), new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if (items != null) {
                    listItemAdapter.setListItem(items);
                    showLoading(false);
                }
            }
        });
    }

    private void showSelectedTvShow(Item item) {
        Intent tvshowDetail = new Intent(getContext(), DetailActivity.class);
        tvshowDetail.putExtra(DetailActivity.EXTRA_ITEM, item);
        Objects.requireNonNull(getContext()).startActivity(tvshowDetail);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
