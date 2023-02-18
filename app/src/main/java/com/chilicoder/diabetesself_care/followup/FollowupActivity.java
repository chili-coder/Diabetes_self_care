package com.chilicoder.diabetesself_care.followup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.med.TimeItem;
import com.chilicoder.diabetesself_care.tobacco.AddDialogTobacco;
import com.chilicoder.diabetesself_care.tobacco.DatabaseHelperTobacco;
import com.chilicoder.diabetesself_care.tobacco.Tobacco;
import com.chilicoder.diabetesself_care.tobacco.TobaccoActivity;
import com.chilicoder.diabetesself_care.tobacco.TobaccoAdapter;
import com.chilicoder.diabetesself_care.tobacco.TobaccoViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FollowupActivity extends AppCompatActivity {

    public static ArrayList<TimeItem> timeItems= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private String[] dietNames, dosageNames;

    MainActivity homeActivity;

    private FollowupViewModel homeViewModel;
    DatabaseHelperFollowup databaseHelper;


    private List<FollowupItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup);

        getSupportActionBar().setTitle("Follow-up");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final TextView textView = findViewById(R.id.text_home_followup);
        recyclerView = findViewById(R.id.recycler_view_followup);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        loadFollowup();
        ExtendedFloatingActionButton fabAddMedicine = findViewById(R.id.fab_add_followup);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AddDialogFollowup addMedicineDialog = new   AddDialogFollowup(FollowupActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog_Followup");


            }
        });


    }

    void loadFollowup() {
        databaseHelper = new DatabaseHelperFollowup(this);
        items = databaseHelper.getFollowupList();

        adapter = new FollowupAdapter(items, this);
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