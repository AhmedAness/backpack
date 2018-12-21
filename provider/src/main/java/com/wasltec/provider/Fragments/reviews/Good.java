package com.wasltec.provider.Fragments.reviews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.wasltec.provider.Adopters.GoodAdapter;

import com.wasltec.provider.R;
import com.wasltec.provider.Utils.App_Context;
import com.wasltec.provider.model.Post_item;

import java.util.ArrayList;
import java.util.List;

import static com.wasltec.provider.Activities.Reviews.good_pastes;
import static com.wasltec.provider.Activities.Reviews.toolbar_review;


public class Good extends  Fragment   {

private View view;
    RecyclerView recyclerView;
    GoodAdapter goodAdapter;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
//    toolbar_review.setTitle(R.string.good);
    setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.fragment_good, container, false);


            recyclerView=view.findViewById(R.id.goodRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            goodAdapter=new GoodAdapter(getActivity(),good_pastes);
            recyclerView.setAdapter(goodAdapter);

        return view;
        }


}
