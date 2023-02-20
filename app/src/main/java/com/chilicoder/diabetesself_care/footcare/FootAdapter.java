package com.chilicoder.diabetesself_care.footcare;

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

public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {

    private List<FootItem> items;
    private Context context;

    public FootAdapter(List<FootItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public FootAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blood_item, parent, false);

        return new FootAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FootAdapter.ViewHolder holder, int position) {
        FootItem item = items.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textFoot.setText(item.getFootName());
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
                DatabaseHelperFoot databaseHelper = new DatabaseHelperFoot(context);
                databaseHelper.deleteFoot(holder.textFoot.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textFoot.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textFoot, textDosageSummary;
        MaterialCardView cardView;
        MaterialCheckBox checkBox;
        ImageButton imageButtonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textFoot = itemView.findViewById(R.id.center_blood_item);
            //   textReport = itemView.findViewById(R.id.report_blood_item);
            textDosageSummary = itemView.findViewById(R.id.date_blood_item);
            cardView = itemView.findViewById(R.id.card_view_blood);
            checkBox = itemView.findViewById(R.id.blood_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.blood_delete_button);
        }
    }
}