package com.example.caravoidancegame.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caravoidancegame.DB.AppDatabase;
import com.example.caravoidancegame.DB.AppScore;
import com.example.caravoidancegame.Iterfaces.StepCallBack;
import com.example.caravoidancegame.R;
import com.example.caravoidancegame.Util.MySP;

import com.google.gson.Gson;



public class ListFragments extends Fragment {
    private ListView userList;
    private StepCallBack StepCallBack;
    private AppDatabase scores;
    private final String SCORE = "scores";

    public void setCallBack_list(StepCallBack StepCallBack){
        this.StepCallBack = StepCallBack;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initListView();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                double latitude = scores.getScores().get(position).getLatitude();
                double longitude =  scores.getScores().get(position).getLongitude();
                String userName = scores.getScores().get(position).getUser();
                StepCallBack.setMapLocation(latitude,longitude,userName);
            }
        });

        return view;
    }

    private void initListView() {
        scores = new Gson().fromJson(MySP.getInstance().getStrSP(SCORE,""),AppDatabase.class);
        if(scores != null){
            ArrayAdapter<AppScore> adapter = new ArrayAdapter<AppScore>(getActivity(), android.R.layout.simple_expandable_list_item_1, scores.getScores()) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(10);
                    return view;
                }
            };

            userList.setAdapter(adapter);
        }
    }

    private void findViews(View view){
        this.userList = view.findViewById(R.id.main_LST_players);
    }
}