package com.wasltec.provider.Fragments.reviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wasltec.provider.Adopters.GoodAdapter;
import com.wasltec.provider.R;

import static com.wasltec.provider.Activities.Reviews.cretical_pastes;

import static com.wasltec.provider.Activities.Reviews.toolbar_review;

public class Critical extends Fragment   {

    private View view;
    RecyclerView recyclerView;
    GoodAdapter criticalAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        toolbar_review.setTitle(R.string.cretical);
        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_good, container, false);

        recyclerView=view.findViewById(R.id.goodRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        criticalAdapter=new GoodAdapter(getActivity(),cretical_pastes);
        recyclerView.setAdapter(criticalAdapter);


        return view;
    }



}
