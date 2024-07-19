package com.example.pcos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class D_HomeAdapter extends RecyclerView.Adapter<D_HomeAdapter.ViewHolder> {
    private List<Patient> patients;
    private static ItemClickListener mClickListener;

    public D_HomeAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView usernameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View patientView = inflater.inflate(R.layout.patient_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(patientView);
        viewHolder.itemView.setOnClickListener(v -> mClickListener.onItemClick(v, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.usernameTextView.setText(patient.getUsername());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void filterList(ArrayList<Patient> filteredList) {
        patients = filteredList;
        notifyDataSetChanged();
    }

}
