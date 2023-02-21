package com.chilicoder.diabetesself_care.tobacco;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chilicoder.diabetesself_care.MainActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.diet.AddDialogDiet;
import com.chilicoder.diabetesself_care.diet.AlarmDietActivity;
import com.chilicoder.diabetesself_care.diet.AlarmReciverDiet;
import com.chilicoder.diabetesself_care.diet.DatabaseHelperDiet;
import com.chilicoder.diabetesself_care.diet.DietFragment;
import com.chilicoder.diabetesself_care.time.TimeAdapter;
import com.chilicoder.diabetesself_care.time.TimeAdapter2;
import com.chilicoder.diabetesself_care.time.TimeSelectorItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddDialogTobacco extends DialogFragment implements Toolbar.OnMenuItemClickListener {
    public static final String TAG = "Add_Dialog_Tobacco";

    private MaterialToolbar toolbar;
    private MaterialTextView textViewDate;
    private EditText editTextMedicineName;
    private ChipGroup chipGroupScheduleTimes, chipGroupAlertType;
    private Chip chipSelected;
    private int[] chipArrayIds = {R.id.chip1_tobacco, R.id.chip2_tobacco, R.id.chip3_tobacco, R.id.chip4_tobacco, R.id.chip5_tobacco};
    private int[] chipAlertArrayIds = {R.id.chip_notification_tobacco, R.id.chip_alarm_tobacco};

    private List<TimeSelectorItem> timeSelectorItems;
    private int mPerDay = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private NumberPicker numberPicker;
    private int noOfTotalTimes;
    private String alertType;

    private Calendar calendar;

    private TobaccoActivity homeFragment;

    public AddDialogTobacco(TobaccoActivity homeFragment) {
        this.homeFragment = homeFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.add_dialog_tobacco, container, false);

        toolbar = root.findViewById(R.id.toolbar_tobacco);
        textViewDate = root.findViewById(R.id.text_view_select_date_tobacco);
        editTextMedicineName = root.findViewById(R.id.editText_tobacco_name);
        chipGroupScheduleTimes = root.findViewById(R.id.chip_group_times_tobacco);
        recyclerView = root.findViewById(R.id.recycler_view_time_tobacco);
        numberPicker = root.findViewById(R.id.number_picker_number_doses_tobacco);
        chipGroupAlertType = root.findViewById(R.id.chip_group_alert_type_tobacco);
        chipSelected = root.findViewById(chipGroupAlertType.getCheckedChipId());

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(AddDialogTobacco.this.getContext(), "It is closed", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        toolbar.setTitle("Add Tobacco");
        toolbar.inflateMenu(R.menu.add_dialog_menu);
        toolbar.setOnMenuItemClickListener(this);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); //
        int mMonth = c.get(Calendar.MONTH); //
        int mDay = c.get(Calendar.DAY_OF_MONTH); //

        calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        textViewDate.setText(format.format(calendar.getTime()));


        textViewDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view2, year, monthOfYear, dayOfMonth) -> {

                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        textViewDate.setText(format1.format(calendar.getTime()));


                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            datePickerDialog.show();
        });

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        format = new SimpleDateFormat("h:mm a");



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        timeSelectorItems = new ArrayList<>();

        MainActivity.timeItems.clear();
        if (mPerDay > 0) {
            numberPicker.setMinValue(mPerDay);
        } else {
            numberPicker.setMinValue(0);
        }
        timeSelectorItems.clear();
        for (int i = 0; i < mPerDay; i++) {
            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Select Time (Click here)");
            timeSelectorItems.add(timeSelectorItem);
        }
        adapter = new TimeAdapter2(timeSelectorItems, getActivity());
        recyclerView.setAdapter(adapter);
        chipGroupScheduleTimes.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = chipGroup.findViewById(id);
            if (chip != null){
                for (int iTmp = 0; iTmp < chipArrayIds.length; iTmp++) {
                    if (chipGroup.getCheckedChipId() == chipArrayIds[iTmp]) {
                        mPerDay = iTmp + 1;
                        MainActivity.timeItems.clear();
                        if (mPerDay > 0) {
                            numberPicker.setMinValue(mPerDay);
                        } else {
                            numberPicker.setMinValue(0);
                        }
                        timeSelectorItems.clear();
                        for (int i = 0; i < mPerDay; i++) {

                            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Select Time (Click here)");
                            timeSelectorItems.add(timeSelectorItem);
                        }
                        adapter = new TimeAdapter2(timeSelectorItems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(mPerDay);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                noOfTotalTimes = numberPicker.getValue();
                Log.d("picker value", String.valueOf(noOfTotalTimes));
            }
        });

        chipGroupAlertType.setOnCheckedChangeListener((chipGroup, id) -> {
            chipSelected = chipGroup.findViewById(id);
            if (chipSelected != null)
                alertType = chipSelected.getText().toString();
            else
                showAlertDialog("Alert Type");
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TobaccoActivity homeActivity = (TobaccoActivity) getActivity();
        String medicineName = editTextMedicineName.getText().toString();
        if (medicineName.isEmpty()) {
            editTextMedicineName.setError("Enter a Name");  //error message appears in margin when no name is entered
            return false;
        }
        if (homeActivity.timeItems.size() != mPerDay) {
            showAlertDialog("Time");
            return false;
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int noOfTimesPerDay = mPerDay;
        int noOfDoses = noOfTotalTimes = numberPicker.getValue();
        String reminderAlterType = alertType = chipSelected.getText().toString();


        ArrayList<String> takeTime = new ArrayList<>();
        for (int i = 0; i < homeActivity.timeItems.size(); i++) {
            takeTime.add(homeActivity.timeItems.get(i).getHour() + ":" + homeActivity.timeItems.get(i).getMinute());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("timingArrays", new JSONArray(takeTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String timingList = json.toString();
        Log.d(TAG, "arrayList:" + timingList);
        DatabaseHelperTobacco databaseHelper = new DatabaseHelperTobacco(getContext());
        databaseHelper.insertNewTabacco(medicineName, day, month, year, noOfTimesPerDay, noOfDoses, timingList, reminderAlterType);
        Calendar calendar = Calendar.getInstance();
        //BUG: Fixed the alarm sounding from the system day even though the user has selected a day.
        //The day selected by the user was only displayed in the edittext, but not in the setAlarm function.
        // fixed.
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        calendar.set(Calendar.HOUR_OF_DAY, homeActivity.timeItems.get(0).getHour());
        calendar.set(Calendar.MINUTE, homeActivity.timeItems.get(0).getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        switch (alertType) {
            case "Bildirim":
                setNotification(calendar, medicineName);
                break;
            case "Alarm":
                setAlarmDiet(calendar, medicineName);
                break;
            default:
                setAlarmDiet(calendar, medicineName);
                setNotification(calendar, medicineName);
                break;

        }
        Log.i("AddDialogTobacco.java", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        homeFragment.loadTobacco();
        dismiss();
        return true;
    }

    public void setAlarmDiet(Calendar mAlarmTime, String medicineName) {
        Intent intent = new Intent(getActivity(), AlarmTobaccoActivity.class);
        intent.putExtra("tobaccoName", medicineName);

        PendingIntent operation = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /** Getting a reference to the System Service ALARM_SERVICE */
        AlarmManager alarmManagerNew = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManagerNew.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);
        } else
            alarmManagerNew.setExact(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);

    }

    private void setNotification(Calendar mNotificationTime, String medicineName) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        Intent notificationIntent = new Intent(getContext(), AlarmRevicerTobacco.class);
        notificationIntent.putExtra("tobaccoName", medicineName);
        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), broadcast);
        Toast.makeText(getContext(), mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        Log.d(TAG, mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE));
    }

    public void showAlertDialog(String nonSelectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Please select all the fields.\n\nNon-selected item(s) found: \n" + nonSelectedItem)
                .setTitle("Select all fields to continue");

        builder.setNeutralButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();
    }

}