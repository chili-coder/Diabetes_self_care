package com.chilicoder.diabetesself_care.followup;

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
import com.chilicoder.diabetesself_care.tobacco.DatabaseHelperTobacco;
import com.chilicoder.diabetesself_care.tobacco.Tobacco;
import com.chilicoder.diabetesself_care.tobacco.TobaccoAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class FollowupAdapter extends RecyclerView.Adapter<FollowupAdapter.ViewHolder> {

    private List<FollowupItem> items;
    private Context context;

    public FollowupAdapter(List<FollowupItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public FollowupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followup_item, parent, false);

        return new FollowupAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowupAdapter.ViewHolder holder, int position) {
        FollowupItem item = items.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textDr.setText(item.getFollowupName());
        holder.textHospital.setText(item.getHospital());
        holder.textLocation.setText(item.getLocation());


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
                DatabaseHelperFollowup databaseHelper = new DatabaseHelperFollowup(context);
                databaseHelper.deleteFollowup(holder.textDr.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textDr.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textDr, textHospital,textLocation,textTime,textDate;
        MaterialCardView cardView;
        MaterialCheckBox checkBox;
        ImageButton imageButtonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDr = itemView.findViewById(R.id.dr_name_text);
            textHospital = itemView.findViewById(R.id.hospital_name_text);
            textLocation = itemView.findViewById(R.id.location_text);
            textTime = itemView.findViewById(R.id.time_text);
            textDate = itemView.findViewById(R.id.date_text);
            cardView = itemView.findViewById(R.id.card_view_followup);
            checkBox = itemView.findViewById(R.id.followup_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.followup_delete_button);
        }
    }
}