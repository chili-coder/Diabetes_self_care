package com.chilicoder.diabetesself_care.time;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.med.TimeItem;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;



public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>  {

    private List<TimeSelectorItem> timeSelectorItems;
    private Context context;
   MainActivity homeActivity;


    public TimeAdapter(List<TimeSelectorItem> timeSelectorItems, Context context) {
        this.timeSelectorItems = timeSelectorItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_time_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        homeActivity = (MainActivity)context;

        TimeSelectorItem timeSelectorItem = timeSelectorItems.get(position);

        holder.textViewTime.setText(timeSelectorItem.getTime());

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        holder.textViewTime.setOnClickListener(view1 -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {
                mCurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                mCurrentTime.set(Calendar.MINUTE, selectedMinute);
                SimpleDateFormat format12 = new SimpleDateFormat("h:mm a");
                holder.textViewTime.setText(format12.format(mCurrentTime.getTime()));
                for (int j = 0; j < homeActivity.timeItems.size(); j++) {
                    if (homeActivity.timeItems.get(j).getPosInRecyclerView() == position) {
                        homeActivity.timeItems.remove(j);
                        break;
                    }
                }
                homeActivity.timeItems.add(new TimeItem(selectedHour, selectedMinute, position));
            }, hour, minute, false);//24 saat
            mTimePicker.show();
        });
    }

    @Override
    public int getItemCount() {
        return timeSelectorItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.text_view_select_time);
        }
    }
}
