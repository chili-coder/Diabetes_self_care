package com.chilicoder.diabetesself_care.activity;

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
import com.chilicoder.diabetesself_care.diet.DatabaseHelperDiet;
import com.chilicoder.diabetesself_care.diet.DietAdapter;
import com.chilicoder.diabetesself_care.diet.DietItem;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class PhysicalAdapter  extends RecyclerView.Adapter<PhysicalAdapter.ViewHolder> {

    private List<PhysicalItem> items;
    private Context context;

    public PhysicalAdapter(List<PhysicalItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public PhysicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.physical_item, parent, false);

        return new PhysicalAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhysicalAdapter.ViewHolder holder, int position) {
        PhysicalItem item = items.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textActivity.setText(item.getActivityName());
        holder.timeText.setText(item.getActivityDuration());
        holder.textDosageSummary.setText(item.getDosageSummary());
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
                DatabaseHelperPhysical databaseHelper = new DatabaseHelperPhysical(context);
                databaseHelper.deletePhysical(holder.textActivity.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textActivity.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

  private       MaterialTextView textActivity, textDosageSummary,timeText;
    private     MaterialCardView cardView;
   private      MaterialCheckBox checkBox;
    private     ImageButton imageButtonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDosageSummary = itemView.findViewById(R.id.dosage_activity_text_view);
            timeText = itemView.findViewById(R.id.time_activity_text_view);
            textActivity = itemView.findViewById(R.id.activity_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_activity);
            checkBox = itemView.findViewById(R.id.activity_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.activity_delete_button);
        }
    }
}