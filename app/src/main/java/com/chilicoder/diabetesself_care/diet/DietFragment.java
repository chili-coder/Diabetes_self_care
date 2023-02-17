package com.chilicoder.diabetesself_care.diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.chilicoder.diabetesself_care.med.AddDialog;
import com.chilicoder.diabetesself_care.med.MedAdapter;
import com.chilicoder.diabetesself_care.med.MedFragment;
import com.chilicoder.diabetesself_care.med.MedItem;
import com.chilicoder.diabetesself_care.med.MedViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class DietFragment extends Fragment {


    public DietFragment() {
        // Required empty public constructor
    }
    private DietViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    private String[] dietNames, dosageNames;

    MainActivity homeActivity;
    DatabaseHelperDiet databaseHelper;


    private List<DietItem> items;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeViewModel =
                ViewModelProviders.of(this).get(DietViewModel.class);

        root = inflater.inflate(R.layout.fragment_diet, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });
        recyclerView = root.findViewById(R.id.recycler_view_diet);
         recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items = new ArrayList<>();

        loadDiet();

        ExtendedFloatingActionButton fabAddMedicine = root.findViewById(R.id.fab_add_diet);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add Diet", Toast.LENGTH_SHORT).show();
                AddDialogDiet addMedicineDialog = new AddDialogDiet(DietFragment.this);
                addMedicineDialog.show(getFragmentManager(), "Add_Dialog_Diet");
            }
        });

        return root;
    }

    void loadDiet() {
        databaseHelper = new DatabaseHelperDiet(getContext());
        items = databaseHelper.getDietList();

        adapter = new DietAdapter(items, getActivity());
        recyclerView.setAdapter(adapter);
    }
}