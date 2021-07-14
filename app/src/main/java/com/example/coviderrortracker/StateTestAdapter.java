package com.example.coviderrortracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateTestAdapter extends RecyclerView.Adapter<StateTestAdapter.VaccineViewHolder> {

    List<StateTestDetailsModel> stateVaccineDetailsModelList;

    public StateTestAdapter(List<StateTestDetailsModel> stateVaccineDetailsModelList) {
        this.stateVaccineDetailsModelList = stateVaccineDetailsModelList;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_statewise_item_layout,parent,false);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  VaccineViewHolder holder, int position) {
        String name;
        long fDose, sDose;

        name = stateVaccineDetailsModelList.get(position).getStateName();
        fDose = stateVaccineDetailsModelList.get(position).getFirstDose();
        sDose = stateVaccineDetailsModelList.get(position).getSecondDose();

        holder.setVaccineDetails(name,fDose,sDose);
    }

    @Override
    public int getItemCount() {
        return stateVaccineDetailsModelList.size();
    }

    public class VaccineViewHolder extends RecyclerView.ViewHolder {

        private TextView stateName,firstDose,secondDose;

        public VaccineViewHolder(@NonNull  View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.stateTestDate);
            firstDose = itemView.findViewById(R.id.totalStateTests);
            secondDose = itemView.findViewById(R.id.positiveStateTest);
        }

        private void setVaccineDetails(String name,long fDose,long sDose){
            stateName.setText(name);
            firstDose.setText(fDose+"");
            secondDose.setText(sDose+"");
        }

    }
}
