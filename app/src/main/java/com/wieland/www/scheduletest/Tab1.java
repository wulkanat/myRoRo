package com.wieland.www.scheduletest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Wieland on 07.05.2017.
 */

public class Tab1 extends Fragment {

    SwipeRefreshLayout SwipeRefresh;
    RecyclerView myList;
    Context context;

    OnHeadlineSelectedListener mCallback;

    int index = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main2, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SwipeRefresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiperefresh);
        myList = (RecyclerView) getActivity().findViewById(R.id.theListOfDoom);
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.onRefreshed();
            }
        });


        context = getActivity();
        SetTextTask setText = new SetTextTask(Schedule.getSchedule(index, context), index, context);
        setText.execute();
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onRefreshed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    public class SetTextTask extends AsyncTask<Void, Void, Boolean> {

        private final Document doc;
        private final Context context;
        private Layout_Row adapter;

        SetTextTask (Document doc, int index, Context context) {
            this.doc = doc;
            this.context = context;
        }

        @Override
        public Boolean doInBackground(Void... params) {
            ArrayList<String> willBeSet;

            ArrayList<ArrayList<android.text.Spanned>> listInList = new ArrayList<>();



            ScheduleHandler myHandler = new ScheduleHandler(index, context);
            willBeSet = myHandler.getClassList();

            for (int i = 0; i < willBeSet.size(); i++) {
                listInList.add(myHandler.getClassInfo(willBeSet.get(i)));
            }

            Layout_Row adapter = new Layout_Row(willBeSet, listInList, this.context);
            this.adapter = adapter;

            return true;
        }

        @Override
        public void onPostExecute(final Boolean success) {
            myList.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
            myList.setLayoutManager(layoutManager);
            myList.setAdapter(this.adapter);

            SharedPreferences pref = getActivity().getSharedPreferences("Tralala", MODE_PRIVATE);

            //Header set username, just some cosmetic stuff
            NavigationView menu2 = (NavigationView) getActivity().findViewById(R.id.nav_view);
            View mHeaderView = menu2.getHeaderView(0);
            TextView username_view = (TextView) mHeaderView.findViewById(R.id.textView_username);
            username_view.setText(pref.getString("set_username", "[Nutzername]"));
            //setTitle(Schedule.getDate(this.index, this.context));
            SwipeRefresh.setRefreshing(false);
        }
    }
}
