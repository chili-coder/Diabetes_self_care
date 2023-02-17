package com.chilicoder.diabetesself_care.med;

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

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {

    private List<MedItem> medItems;
    private Context context;

    public MedAdapter(List<MedItem> medItems, Context context) {
        this.medItems = medItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedAdapter.ViewHolder holder, int position) {

        MedItem medItem = medItems.get(position);

        //This is the page where the alarm deletion function is written.

        holder.textMedicine.setText(medItem.getMedicineName());
        holder.textDosageSummary.setText(medItem.getDosageSummary());
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
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteMedicine(holder.textMedicine.getText().toString());
                holder.cardView.setVisibility(View.GONE);
                Toast.makeText(context, holder.textMedicine.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return medItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView textMedicine;
        public MaterialTextView textDosageSummary;
        public MaterialCardView cardView;
        public MaterialCheckBox checkBox;
        public ImageButton imageButtonDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDosageSummary = itemView.findViewById(R.id.dosage_text_view);
            textMedicine = itemView.findViewById(R.id.medicine_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_medicine);
            checkBox = itemView.findViewById(R.id.medicine_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.medicine_delete_button);
        }
    }
}
