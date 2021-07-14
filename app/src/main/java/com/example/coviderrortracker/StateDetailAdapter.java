package com.example.coviderrortracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StateDetailAdapter extends RecyclerView.Adapter<StateDetailAdapter.MyViewHolder> {

    List<StateCoronaDetailsModel> stateCoronaDetailsModels = new ArrayList<>();

    public StateDetailAdapter(List<StateCoronaDetailsModel> stateCoronaDetailsModels) {
        this.stateCoronaDetailsModels = stateCoronaDetailsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateDetailAdapter.MyViewHolder holder, int position) {
        String stateName, confirmedCases, recoveredCases, deaths;
        stateName = stateCoronaDetailsModels.get(position).getStateName();
        confirmedCases = stateCoronaDetailsModels.get(position).getConfirmedCases();
        recoveredCases = stateCoronaDetailsModels.get(position).getRecoveredCases();
        deaths = stateCoronaDetailsModels.get(position).getDeaths();

        holder.setCoronaDetails(stateName, confirmedCases, recoveredCases, deaths);

    }

    @Override
    public int getItemCount() {
        return stateCoronaDetailsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stateNameTxt,confirmedCasesTxt, recoveredCasesTxt, activeCasesTxt, deathTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stateNameTxt = itemView.findViewById(R.id.stateNameTxt);
            confirmedCasesTxt = itemView.findViewById(R.id.confirmedCasesTxt);
            recoveredCasesTxt = itemView.findViewById(R.id.recoveredCasesTxt);
            deathTxt = itemView.findViewById(R.id.deathTxt);

        }

        private void setCoronaDetails(String stateName,String confirmedCases,
                                     String recoveredCases,String deaths){
            stateNameTxt.setText(stateName);
            confirmedCasesTxt.setText(confirmedCases);
            recoveredCasesTxt.setText(recoveredCases);
            deathTxt.setText(deaths);
        }
    }
}
