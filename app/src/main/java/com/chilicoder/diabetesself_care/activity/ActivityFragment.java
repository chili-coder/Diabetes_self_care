package com.chilicoder.diabetesself_care.activity;

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
import com.chilicoder.diabetesself_care.diet.AddDialogDiet;
import com.chilicoder.diabetesself_care.diet.DatabaseHelperDiet;
import com.chilicoder.diabetesself_care.diet.DietAdapter;
import com.chilicoder.diabetesself_care.diet.DietFragment;
import com.chilicoder.diabetesself_care.diet.DietItem;
import com.chilicoder.diabetesself_care.diet.DietViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ActivityFragment extends Fragment {


    public ActivityFragment() {
        // Required empty public constructor
    }
    private PhysicalViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    private String[] dietNames, dosageNames;

    MainActivity homeActivity;
    DatabaseHelperPhysical databaseHelper;


    private List<PhysicalItem> items;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeViewModel =
                ViewModelProviders.of(this).get(PhysicalViewModel.class);

        root = inflater.inflate(R.layout.fragment_activity, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });
        recyclerView = root.findViewById(R.id.recycler_view_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items = new ArrayList<>();

        loadPysical();

        ExtendedFloatingActionButton fabAddMedicine = root.findViewById(R.id.fab_add_activity);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add Physical Activity", Toast.LENGTH_SHORT).show();
                AddDialogPhysical addMedicineDialog = new AddDialogPhysical(ActivityFragment.this);
                addMedicineDialog.show(getFragmentManager(), "Add_Dialog_Physical");
            }
        });

        return root;
    }

    void loadPysical() {

        databaseHelper = new DatabaseHelperPhysical(getContext());
        items = databaseHelper.getPhysicalList();

        adapter = new PhysicalAdapter(items, getActivity());
        recyclerView.setAdapter(adapter);
    }
}