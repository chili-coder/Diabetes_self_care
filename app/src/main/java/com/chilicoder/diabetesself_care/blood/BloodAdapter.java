package com.chilicoder.diabetesself_care.blood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.followup.DatabaseHelperFollowup;
import com.chilicoder.diabetesself_care.followup.FollowupAdapter;
import com.chilicoder.diabetesself_care.followup.FollowupItem;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.ViewHolder> {

    private List<BloodItem> items;
    private Context context;

    public BloodAdapter(List<BloodItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public BloodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followup_item, parent, false);

        return new BloodAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodAdapter.ViewHolder holder, int position) {
        BloodItem item = items.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textCenter.setText(item.getBloodCenterName());
        holder.textReport.setText(item.getReport());
        holder.textDate.setText(item.getmDate());

        DatabaseHelperBlood databaseHelper = new DatabaseHelperBlood(context);

//        holder.textTime.setText(item.getTime());
//        holder.textDate.setText(item.getDate());



        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    holder.imageButtonDelete.setVisibility(View.VISIBLE);
                else
                    holder.imageButtonDelete.setVisibility(View.GONE);
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelperBlood databaseHelper = new DatabaseHelperBlood(context);
                databaseHelper.deleteBlood(holder.textCenter.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textCenter.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textCenter,  textReport,textDate;
        MaterialCardView cardView;
        MaterialCheckBox checkBox;
        ImageButton imageButtonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textCenter = itemView.findViewById(R.id.center_blood_item);
            textReport = itemView.findViewById(R.id.report_blood_item);
            textDate = itemView.findViewById(R.id.date_blood_item);
            cardView = itemView.findViewById(R.id.card_view_blood);
            checkBox = itemView.findViewById(R.id.blood_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.blood_delete_button);
        }
    }
}