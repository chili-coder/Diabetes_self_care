package com.chilicoder.diabetesself_care.footcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.blood.AddDialogBlood;
import com.chilicoder.diabetesself_care.blood.BloodActivity;
import com.chilicoder.diabetesself_care.blood.BloodAdapter;
import com.chilicoder.diabetesself_care.blood.BloodItem;
import com.chilicoder.diabetesself_care.blood.BloodViewModel;
import com.chilicoder.diabetesself_care.blood.DatabaseHelperBlood;
import com.chilicoder.diabetesself_care.med.TimeItem;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FootActivity extends AppCompatActivity {
    public static ArrayList<TimeItem> timeItems= new ArrayList<>();


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private String[] bloodCenterName, dosageNames;

    MainActivity homeActivity;

    private FootViewModel homeViewModel;
    DatabaseHelperFoot databaseHelper;


    private List<FootItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot);

        getSupportActionBar().setTitle("Foot Care");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textView = findViewById(R.id.text_home_foot);
        recyclerView = findViewById(R.id.recycler_view_foot);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        loadFoot();
        ExtendedFloatingActionButton fabAddMedicine = findViewById(R.id.fab_add_foot);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddDialogFoot addMedicineDialog = new AddDialogFoot(FootActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog_Foot");


            }
        });
    }

    void loadFoot() {
        databaseHelper = new DatabaseHelperFoot(this);
        items = databaseHelper.getFootList();
        adapter = new FootAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}