package com.chilicoder.diabetesself_care.med;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.db.DatabaseHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MedFragment extends Fragment {

    //

    public MedFragment() {
        // Required empty public constructor
    }

    private MedViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    private String[] medicineNames, dosageNames;

    MainActivity homeActivity;
    DatabaseHelper databaseHelper;


    private List<MedItem> medItems;
    private TextView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeViewModel =
                ViewModelProviders.of(this).get(MedViewModel.class);

        root = inflater.inflate(R.layout.fragment_med, container, false);
        final TextView emptyView= root.findViewById(R.id.text_home_1);

        recyclerView = root.findViewById(R.id.recycler_view_medicine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       medItems = new ArrayList<>();

       loadMedicines();



        ExtendedFloatingActionButton fabAddMedicine = root.findViewById(R.id.fab_add_medicine);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add Medicine", Toast.LENGTH_SHORT).show();
                AddDialog addMedicineDialog = new AddDialog(MedFragment.this);
                addMedicineDialog.show(getFragmentManager(), "Add_Dialog");
            }
        });

        return root;
    }

    void loadMedicines() {
        databaseHelper = new DatabaseHelper(getContext());
        medItems = databaseHelper.getMedicineList();

        adapter = new MedAdapter(medItems, getActivity());
        recyclerView.setAdapter(adapter);
    }
}