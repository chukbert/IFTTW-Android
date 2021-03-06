package com.example.ifttw;

import android.content.Context;
import android.media.MediaDrm;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Routines> routinesList;
    private OnEventListener onEventListener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView parentLayout;
        TextView triggerTxt, actionTxt, idRoutine, statusTxt;
        public MyViewHolder(View v)  {

            super(v);
            parentLayout = v.findViewById(R.id.row_routine);
            idRoutine = (TextView)v.findViewById(R.id.label_idrutin);
            triggerTxt = (TextView)v.findViewById(R.id.label_trigger);
            actionTxt = (TextView)v.findViewById(R.id.label_action);
            statusTxt = (TextView)v.findViewById(R.id.label_status);

            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }
    public RecyclerAdapter(Context mContext, List<Routines> routinesList, OnEventListener onEventListener) {
        this.mContext = mContext;
        this.routinesList = routinesList;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_routine, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Routines album = routinesList.get(position);

        String idTxt =  Integer.toString(album.getIdRoutine()) ;
        Log.d("idTxt", idTxt);
        holder.idRoutine.setText(idTxt);

        String valueTrigger = null;
        int triggerType = album.getTriggerType();
        if (triggerType == 1) valueTrigger = "Timer : Daily";
        else if (triggerType == 2) valueTrigger = "Timer : Weekly";
        else if (triggerType == 3) valueTrigger = "Timer : Once";
        else if (triggerType == 4) valueTrigger = "Sensor : Proximity";
        holder.triggerTxt.setText(valueTrigger);

        String valueAction = null;
        int actionType = album.getActionType();
        if (actionType == 1) valueAction = "Notification";
        else if (actionType == 2) valueAction = "Turn on Wifi";
        else if (actionType == 3) valueAction = "Turn off Wifi";
        else if (actionType == 4) valueAction = "Call external API";
        holder.actionTxt.setText(valueAction);

        String valueStatus = null;
        int status = album.getStatus();
        if (status == 1) valueStatus = "Active";
        else valueStatus = "Inactive";
        holder.statusTxt.setText(valueStatus);

    }

    public interface OnEventListener {
        void onEventClick(int position);
    }

    @Override
    public int getItemCount() {
        return (routinesList != null) ? routinesList.size() : 0;
    }
}

