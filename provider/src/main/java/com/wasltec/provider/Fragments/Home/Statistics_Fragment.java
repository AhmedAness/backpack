package com.wasltec.provider.Fragments.Home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.wasltec.provider.Activities.Home.toolbar;

public class Statistics_Fragment extends Fragment {


    private View view;
    Set set;
    Set set2;
    AnyChartView anyChartView1, anyChartView2;
    Pie pie , pie2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        toolbar.setTitle(R.string.statistics);

//        view =  inflater.inflate(R.layout.empty_statistics_fragment_layout, container, false);


        view =  inflater.inflate(R.layout.activity_chart_common, container, false);

        List<DataEntry> Revenues_data = new ArrayList<>();
        List<DataEntry> Tickets_data = new ArrayList<>();

        AndroidNetworking.get(URLManger.getInstance().getShowStatistics(""+Session.getInstance().getId()))
                .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(getActivity()).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray Revenues = new JSONArray();
                        JSONArray Tickets = new JSONArray();
                        try {
                            Revenues = response.getJSONArray("revenue");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Tickets = response.getJSONArray("tickets");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < Revenues.length() ; i++) {


                            try {
                                Revenues_data.add(new ValueDataEntry(   Revenues.getJSONObject(i).getString("title"),
                                        Revenues.getJSONObject(i).getInt("totalAmount")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        for (int i = 0; i < Tickets.length() ; i++) {
                            try {
                                Tickets_data.add(new ValueDataEntry(   Tickets.getJSONObject(i).getString("title"),
                                        Tickets.getJSONObject(i).getInt("numberOfTickets")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

//////////////////

                        anyChartView1 = view.findViewById(R.id.any_chart_view2);
                        anyChartView2 = view.findViewById(R.id.any_chart_view1);
                        anyChartView1.setProgressBar(view.findViewById(R.id.progress_bar));
                        anyChartView2.setProgressBar(view.findViewById(R.id.progress_bar));

/////////////////////////
                        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
                        pie = AnyChart.pie();
                        pie.title("Activities VS #of tickets");
                        pie.labels().position("outside");
                        pie.legend().title().enabled(true);
                        pie.legend().title()
                                .text("")
                                .padding(0d, 0d, 10d, 0d).enabled(false);
                        pie.legend()
                                .position("center-bottom")
                                .itemsLayout(LegendLayout.HORIZONTAL)
                                .align(Align.CENTER)
                                ;
                        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                            @Override
                            public void onClick(Event event) {
                                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
                        pie.data(Tickets_data);
                        anyChartView2.setChart(pie);
////////////////////

                        APIlib.getInstance().setActiveAnyChartView(anyChartView1);
                        pie2 = AnyChart.pie();
                        pie2.title("Activities VS earnings");
                        pie2.labels().position("outside");
                        pie2.legend().title().enabled(true);
                        pie2.legend().title()
                                .text("")
                                .padding(0d, 0d, 10d, 0d)
                                .enabled(false);
                        pie2.legend()
                                .position("center-bottom")
                                .itemsLayout(LegendLayout.HORIZONTAL)
                                .align(Align.CENTER);
                        APIlib.getInstance().setActiveAnyChartView(anyChartView1);
                        pie2.data(Revenues_data);
                        pie2.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                            @Override
                            public void onClick(Event event) {
                                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        anyChartView1.setChart(pie2);






                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d(TAG, "stattistic api error : "+anError.getMessage());
                    }
                });


















        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.more_tab_menu, menu);

        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.add_item).setVisible(false);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(R.string.statistics);

    }
}
