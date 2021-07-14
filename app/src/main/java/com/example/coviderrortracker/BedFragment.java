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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BedFragment extends Fragment {

    private RecyclerView recyclerView;
    private StateBedsAdapter stateBedsAdapter;
    private String url = "https://api.rootnet.in/covid19-in/hospitals/beds";
    private List<StateBedDetailsModel> stateBedDetailsModelList;
    private TextView totalHospital,totalBeds,totalUrbanHospitals,totalUrbanBeds,totalRuralHospital,totalRuralBeds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bed, container, false);
        recyclerView = view.findViewById(R.id.stateWiseBedsRecyclerView);
        totalHospital = view.findViewById(R.id.totalHospital);
        totalBeds = view.findViewById(R.id.totalBeds);
        totalUrbanBeds = view.findViewById(R.id.totalUrbanBeds);
        totalRuralHospital = view.findViewById(R.id.totalRuralHospital);
        totalRuralBeds = view.findViewById(R.id.totalRuralBeds);
        totalUrbanHospitals = view.findViewById(R.id.totalUrbanHospital);

        stateBedDetailsModelList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setStateBedDetails();
        setBedSummaryDetails();

        return view;
    }

    private void setBedSummaryDetails() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONObject summary = data.getJSONObject("summary");

                    long hospitals,beds,urbanHospitals,urbanBeds,ruralHospitals,ruralBeds;

                    hospitals = summary.getLong("totalHospitals");
                    beds = summary.getLong("totalBeds");
                    urbanHospitals = summary.getLong("urbanHospitals");
                    urbanBeds = summary.getLong("urbanBeds");
                    ruralHospitals = summary.getLong("ruralHospitals");
                    ruralBeds = summary.getLong("ruralBeds");

                    totalHospital.setText(hospitals+"");
                    totalBeds.setText(beds+"");
                    totalUrbanBeds.setText(urbanBeds+"");
                    totalRuralHospital.setText(urbanHospitals+"");
                    totalRuralBeds.setText(ruralBeds+"");
                    totalUrbanHospitals.setText(ruralHospitals+"");

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

        queue.add(jsonObjectRequest);
    }

    private void setStateBedDetails() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("regional");

                    for(int i=0;i<jsonArray.length();i++){
                        String name;
                        long hospitals,beds;
                        JSONObject casesDetails = jsonArray.getJSONObject(i);

                        name = casesDetails.getString("state");
                        hospitals = casesDetails.getLong("totalHospitals");
                        beds = casesDetails.getLong("totalBeds");

                        stateBedDetailsModelList.add(new StateBedDetailsModel(name,hospitals,beds));

                        stateBedsAdapter = new StateBedsAdapter(stateBedDetailsModelList);
                        recyclerView.setAdapter(stateBedsAdapter);

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

        queue.add(jsonObjectRequest);
    }
}