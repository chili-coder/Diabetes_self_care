package com.chilicoder.diabetesself_care.blood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.followup.AddDialogFollowup;
import com.chilicoder.diabetesself_care.followup.DatabaseHelperFollowup;
import com.chilicoder.diabetesself_care.followup.FollowupActivity;
import com.chilicoder.diabetesself_care.followup.FollowupAdapter;
import com.chilicoder.diabetesself_care.followup.FollowupItem;
import com.chilicoder.diabetesself_care.followup.FollowupViewModel;
import com.chilicoder.diabetesself_care.med.TimeItem;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BloodActivity extends AppCompatActivity {
    public static ArrayList<TimeItem> timeItems= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private String[] bloodCenterName, dosageNames;

    MainActivity homeActivity;

    private BloodViewModel homeViewModel;
    DatabaseHelperBlood databaseHelper;


    private List<BloodItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        getSupportActionBar().setTitle("Blood Glucose Test");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textView = findViewById(R.id.text_home_blood);
        recyclerView = findViewById(R.id.recycler_view_blood);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        loadBlood();
        ExtendedFloatingActionButton fabAddMedicine = findViewById(R.id.fab_add_blood);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AddDialogBlood addMedicineDialog = new    AddDialogBlood(BloodActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog_Blood");


            }
        });

    }

    void loadBlood() {
        databaseHelper = new DatabaseHelperBlood(this);
        items = databaseHelper.getBloodList();
        adapter = new BloodAdapter(items, this);
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