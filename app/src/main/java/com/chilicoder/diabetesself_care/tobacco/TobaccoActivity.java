package com.chilicoder.diabetesself_care.tobacco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.chilicoder.diabetesself_care.med.TimeItem;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TobaccoActivity extends AppCompatActivity {
    public static ArrayList<TimeItem> timeItems= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private String[] dietNames, dosageNames;

    MainActivity homeActivity;

    private TobaccoViewModel homeViewModel;
    DatabaseHelperTobacco databaseHelper;


    private List<Tobacco> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tobacco);
        getSupportActionBar().setTitle("Avoiding Tobacco");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textView = findViewById(R.id.text_home_tobacco);
        recyclerView = findViewById(R.id.recycler_view_tobacco);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        loadTobacco();
        ExtendedFloatingActionButton fabAddMedicine = findViewById(R.id.fab_add_tobacco);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                DialogFragment dialog = TobacoDialog.newInstance();
//                ((TobacoDialog) dialog).setCallback(new TobacoDialog.Callback() {
//                    @Override
//                    public void onActionClick(String name) {
//                        Toast.makeText(TobaccoActivity.this, name, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.show(getSupportFragmentManager(), "tag");

//                Intent intent =new Intent(TobaccoActivity.this,AddTobaccoActivity.class);
//                startActivity(intent);

                AddDialogTobacco addMedicineDialog = new   AddDialogTobacco(TobaccoActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog_Tobacco");


            }
        });
    }

    void loadTobacco() {
        databaseHelper = new DatabaseHelperTobacco(this);
        items = databaseHelper.getTabaccoList();

        adapter = new TobaccoAdapter(items, this);
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