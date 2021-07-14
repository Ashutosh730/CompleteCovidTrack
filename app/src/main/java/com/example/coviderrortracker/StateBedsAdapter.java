package com.example.coviderrortracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class StateBedsAdapter extends RecyclerView.Adapter<StateBedsAdapter.ViewHolder>{

    List<StateBedDetailsModel> stateBedDetailsModelList;

    public StateBedsAdapter(List<StateBedDetailsModel> stateBedDetailsModelList) {
        this.stateBedDetailsModelList = stateBedDetailsModelList;
    }

    @NonNull
    @Override
    public StateBedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bed_statewise_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateBedsAdapter.ViewHolder holder, int position) {

        String name;
        long hospitals, beds;

        name = stateBedDetailsModelList.get(position).getStateName();
        hospitals = stateBedDetailsModelList.get(position).getTotalHospitals();
        beds = stateBedDetailsModelList.get(position).getTotalBeds();

        holder.setHospitalsDetails(name,hospitals,beds);
    }

    @Override
    public int getItemCount() {
        return stateBedDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stateName,totalHospitals,totalBeds;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.bedStateTxt);
            totalHospitals = itemView.findViewById(R.id.totalStateHospitals);
            totalBeds = itemView.findViewById(R.id.totalStateBeds);
        }

        private void setHospitalsDetails(String name,long hospitals,long beds){
            stateName.setText(name);
            totalHospitals.setText(hospitals+"");
            totalBeds.setText(beds+"");
        }

    }
}
