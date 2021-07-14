package com.example.coviderrortracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CasesFragment extends Fragment {


    private RecyclerView recyclerView;
    private static StateDetailAdapter stateDetailAdapter;
    private String baseUrl = "https://api.rootnet.in/covid19-in/";
    private TextView summaryRecoveredCases,summaryConfirmedCases,summaryDeathCases;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cases, container, false);

        recyclerView = view.findViewById(R.id.stateWiseCasesRecyclerView);
        summaryConfirmedCases = view.findViewById(R.id.summaryConfirmedCasesTxt);
        summaryRecoveredCases = view.findViewById(R.id.summaryRecoveredCasesTxt);
        summaryDeathCases = view.findViewById(R.id.summaryDeathCasesTxt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        String stateCasesUrl = baseUrl+"stats/latest";
        setStateData(stateCasesUrl);

        setSummaryCasesDetails(stateCasesUrl);

        return view;
    }

    private void setSummaryCasesDetails(String stateCasesUrl) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(stateCasesUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray summaryData = data.getJSONArray("unofficial-summary");

                    int cCase,rCases,dCases;
                    JSONObject finalData = summaryData.getJSONObject(0);
                    cCase = finalData.getInt("total");
                    rCases = finalData.getInt("recovered");
                    dCases = finalData.getInt("deaths");

                    summaryConfirmedCases.setText(cCase+"");
                    summaryRecoveredCases.setText(rCases+"");
                    summaryDeathCases.setText(dCases+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void setStateData(String url) {

        List<StateCoronaDetailsModel> stateCoronaDetailsModels = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("regional");

                    for(int i=0;i<jsonArray.length();i++){
                        String name;
                        long confirmed,recovered,death;
                        JSONObject casesDetails = jsonArray.getJSONObject(i);

                        name = casesDetails.getString("loc");
                        confirmed = casesDetails.getLong("confirmedCasesIndian");
                        recovered = casesDetails.getLong("discharged");
                        death = casesDetails.getLong("deaths");


                        stateCoronaDetailsModels.add(new StateCoronaDetailsModel(name,confirmed+"",+recovered+"",
                                death+""));

                        stateDetailAdapter = new StateDetailAdapter(stateCoronaDetailsModels);
                        recyclerView.setAdapter(stateDetailAdapter);
                        stateDetailAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}