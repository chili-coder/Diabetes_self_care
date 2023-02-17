package com.chilicoder.diabetesself_care.diet;

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
import com.chilicoder.diabetesself_care.db.DatabaseHelper;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class DietAdapter  extends RecyclerView.Adapter<DietAdapter.ViewHolder> {

    private List<DietItem> items;
    private Context context;

    public DietAdapter(List<DietItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public DietAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list, parent, false);

        return new DietAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItem item = items.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textDiet.setText(item.getDietName());
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
                DatabaseHelperDiet databaseHelper = new DatabaseHelperDiet(context);
                databaseHelper.deleteDiet(holder.textDiet.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textDiet.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       MaterialTextView textDiet, textDosageSummary;
        MaterialCardView cardView;
         MaterialCheckBox checkBox;
        ImageButton imageButtonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDosageSummary = itemView.findViewById(R.id.dosage_text_view);
            textDiet = itemView.findViewById(R.id.medicine_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_medicine);
            checkBox = itemView.findViewById(R.id.medicine_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.medicine_delete_button);
        }
    }
}
