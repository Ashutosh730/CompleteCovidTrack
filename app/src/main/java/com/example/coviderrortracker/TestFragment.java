package com.example.coviderrortracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestFragment extends Fragment {

    private RecyclerView recyclerView;
    private StateTestAdapter stateVaccineAdapter;
    private List<StateTestDetailsModel> stateTestDetailsModelList;
    private String baseUrl = "https://api.rootnet.in/covid19-in/stats/testing/";
    private TextView date, update,progressPercent,testingInNumber;
    private ProgressBar testingProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);

        recyclerView = view.findViewById(R.id.stateWiseVaccinationRecyclerView);
        date = view.findViewById(R.id.dateTxt);
        update = view.findViewById(R.id.vaccinationUpdateTimeTxt);
        testingProgress = view.findViewById(R.id.testingProgress);
        progressPercent = view.findViewById(R.id.progressPercent);
        testingInNumber = view.findViewById(R.id.testingInNumber);
        stateTestDetailsModelList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        String url = baseUrl + "history";
        setDailyTestDetail(url);

        url = baseUrl + "latest";
        setSummaryTestDetails(url);

        return view;
    }

    private void setSummaryTestDetails(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    String dat;
                    long total, positive;
                    total = data.getLong("totalSamplesTested");

                    dat = "Last Update "+data.getString("day");
                    date.setText(dat);
                    update.setText(data.getString("day"));
                    long progress = (total)*100/1400000000;

                    progressPercent.setText(progress+"%");
                    testingProgress.setProgress((int)progress);

                    testingInNumber.setText("Total Testing Done: "+total+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    },new Response.ErrorListener()

    {
        @Override
        public void onErrorResponse (VolleyError error){
        Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
    }
    });

        requestQueue.add(jsonObjectRequest);
}

    private void setDailyTestDetail(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject object = data.getJSONObject(i);
                        String date;
                        long total, positive;
                        date = object.getString("day");
                        total = object.getLong("totalIndividualsTested");
                        positive = object.getLong("totalPositiveCases");

                        stateTestDetailsModelList.add(new StateTestDetailsModel(date, total, positive));

                        stateVaccineAdapter = new StateTestAdapter(stateTestDetailsModelList);
                        recyclerView.setAdapter(stateVaccineAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}