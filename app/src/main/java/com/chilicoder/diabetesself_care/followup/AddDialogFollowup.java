package com.chilicoder.diabetesself_care.followup;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.chilicoder.diabetesself_care.time.TimeAdapter2;
import com.chilicoder.diabetesself_care.time.TimeAdapter3;
import com.chilicoder.diabetesself_care.time.TimeSelectorItem;
import com.chilicoder.diabetesself_care.tobacco.AddDialogTobacco;
import com.chilicoder.diabetesself_care.tobacco.AlarmRevicerTobacco;
import com.chilicoder.diabetesself_care.tobacco.AlarmTobaccoActivity;
import com.chilicoder.diabetesself_care.tobacco.DatabaseHelperTobacco;
import com.chilicoder.diabetesself_care.tobacco.TobaccoActivity;
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

public class AddDialogFollowup  extends DialogFragment implements Toolbar.OnMenuItemClickListener {
    public static final String TAG = "Add_Dialog_Tobacco";

    /*Encoding the page where the alarm was added by the user*/

    /*(1) In this section, the ids of the components on the add_medicine_dialog.xml page are defined*/
    private MaterialToolbar toolbar;
    private MaterialTextView textViewDate;
    private EditText editTextDoctorName, editTextHospitalName, editTextLocationName;
    private ChipGroup chipGroupScheduleTimes, chipGroupAlertType;
    private Chip chipSelected;
    private int[] chipArrayIds = {R.id.chip1_followup};
    private int[] chipAlertArrayIds = {R.id.chip_notification_followup, R.id.chip_alarm_followup};

    private List<TimeSelectorItem> timeSelectorItems;
    private int mPerDay = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    //private NumberPicker numberPicker;
    private int noOfTotalTimes;
    private String alertType;

    private Calendar calendar;

    private FollowupActivity homeFragment;

    public AddDialogFollowup(FollowupActivity homeFragment) {
        this.homeFragment = homeFragment;
    }
    /*(1)*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //(2) normal stil ve açılan xml dosyasının full ekran olmasını sağlayan kod.
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {  //(3) dialog başlatıldığı an ekrandaki boyutlarının ayarlanması
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);//(3.1) Boyutlar set edilir.

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.add_followup_dialog, container, false);
        //(4) dialogdaki asıl bileşen id'leri buradaki id'lere bağlanır.
        toolbar = root.findViewById(R.id.toolbar_followup);
        textViewDate = root.findViewById(R.id.text_view_select_date_followup);
        editTextDoctorName = root.findViewById(R.id.editText_name_followup);
        editTextHospitalName = root.findViewById(R.id.editText_hospital_followup);
        editTextLocationName = root.findViewById(R.id.editText_location_followup);
        chipGroupScheduleTimes = root.findViewById(R.id.chip_group_times_followup);
        recyclerView = root.findViewById(R.id.recycler_view_time_followup);
        chipGroupAlertType = root.findViewById(R.id.chip_group_alert_type_followup);
        chipSelected = root.findViewById(chipGroupAlertType.getCheckedChipId());

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(AddDialogFollowup.this.getContext(), "It is closed", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        toolbar.setTitle("Add Follow-up");
        toolbar.inflateMenu(R.menu.add_dialog_menu);
        toolbar.setOnMenuItemClickListener(this);

        final Calendar c = Calendar.getInstance(); //içinde bulunduğumuz gün ay ve yıl.
        int mYear = c.get(Calendar.YEAR); //
        int mMonth = c.get(Calendar.MONTH); //
        int mDay = c.get(Calendar.DAY_OF_MONTH); //

        calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy"); //data formatı
        textViewDate.setText(format.format(calendar.getTime())); //datetext e set edilir. Sistem günü xml dosyası açılır açılmaz görünür


        textViewDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        // to the edittext here the system clock is set
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        textViewDate.setText(format1.format(calendar.getTime()));


                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); //HATA DÜZELTİLMESİ: Sistem gününden
            //Access to previous days is denied because access to previous days has stopped the application.
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

        timeSelectorItems.clear();
        for (int i = 0; i < mPerDay; i++) {
            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Select Time (Click here)");
            timeSelectorItems.add(timeSelectorItem);
        }
        adapter = new TimeAdapter3(timeSelectorItems, getActivity());
        recyclerView.setAdapter(adapter);
        chipGroupScheduleTimes.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = chipGroup.findViewById(id);
            if (chip != null){
                for (int iTmp = 0; iTmp < chipArrayIds.length; iTmp++) {
                    if (chipGroup.getCheckedChipId() == chipArrayIds[iTmp]) {
                        mPerDay = iTmp + 1;
                        MainActivity.timeItems.clear();

                        timeSelectorItems.clear();
                        for (int i = 0; i < mPerDay; i++) {

                            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Select Time (Click here)");
                            timeSelectorItems.add(timeSelectorItem);
                        }
                        adapter = new TimeAdapter3(timeSelectorItems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }
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
        FollowupActivity homeActivity = (FollowupActivity) getActivity();
        String followupName = editTextDoctorName.getText().toString();
        String hospital= editTextHospitalName.getText().toString();
        String location = editTextLocationName.getText().toString();





        if (followupName.isEmpty()) {
            editTextDoctorName.setError("Enter Doctor Name");  //error message appears in margin when no name is entered
            return false;
        }
        if (hospital.isEmpty()) {
            editTextHospitalName.setError("Enter Hospital Name");  //error message appears in margin when no name is entered
            return false;
        }
        if (location.isEmpty()) {
            editTextLocationName.setError("Enter Location");  //error message appears in margin when no name is entered
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
        int noOfDoses = noOfTotalTimes = 1;
        String reminderAlterType = alertType = chipSelected.getText().toString();


        int nH =calendar.get(Calendar.HOUR_OF_DAY);
        int nM= calendar.get(Calendar.MINUTE);
        String nHour=String.valueOf(nH);
        String nMin=String.valueOf(nM);

        String nDD=String.valueOf(day);
        String nMM=String.valueOf(month);
        String nYY=String.valueOf(year);

       // String mTime="Time: "+nHour+":"+nMin;
      //  String mDate="Date: "+nDD+"-"+nMM+"-"+nYY;


        String mDate=textViewDate.getText().toString();



        ArrayList<String> takeTime = new ArrayList<>();
        for (int i = 0; i < homeActivity.timeItems.size(); i++) {
            takeTime.add(homeActivity.timeItems.get(i).getHour() + ":" + homeActivity.timeItems.get(i).getMinute());
        }
        String mTime="Follow-up";

        JSONObject json = new JSONObject();
        try {
            json.put("timingArrays", new JSONArray(takeTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String timingList = json.toString();
        Log.d(TAG, "arrayList:" + timingList);
        DatabaseHelperFollowup databaseHelper = new DatabaseHelperFollowup(getContext());
        databaseHelper.insertNewFollowup(followupName,hospital,location,mTime,mDate, day, month, year, noOfTimesPerDay, noOfDoses, timingList, reminderAlterType);
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

      //  setNotification2(calendar, followupName);

        switch (alertType) {
            case "Bildirim":
               setNotification(calendar, followupName);
              setNotification2(calendar, followupName);

                break;
            case "Alarm":
                setAlarmFollowup(calendar, followupName);
                break;
            default: //hata mı değil mi tam olarak anlayamadım.
              setAlarmFollowup(calendar, followupName);
              setNotification(calendar, followupName);
           setNotification2(calendar, followupName);
                break;

        }
        Log.i("AddDialogFollowup.java", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        homeFragment.loadFollowup();
        dismiss();
        return true;
    }

    public void setAlarmFollowup(Calendar mAlarmTime, String medicineName) {
        AlarmManager alarmManagerNew = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmFollowupActivity.class);
        intent.putExtra("followupName", medicineName);



        /** Getting a reference to the System Service ALARM_SERVICE */



        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (getActivity(), 0, intent, PendingIntent.FLAG_MUTABLE);
            alarmManagerNew.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), pendingIntent);
        } else {
            pendingIntent = PendingIntent.getActivity
                    (getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerNew.setExact(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), pendingIntent);
        }


    }

    private void setNotification(Calendar mNotificationTime, String medicineName) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent notificationIntent = new Intent(getContext(), AlarmReciverFollowup.class);
        notificationIntent.putExtra("followupName", medicineName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    100,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), pendingIntent);
        } else {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    100,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), pendingIntent);
        }
        Toast.makeText(getContext(), mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        Log.d(TAG, mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE));
    }

    private void setNotification2(Calendar mNotificationTime, String medicineName) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent notificationIntent = new Intent(getContext(), AlarmReciverFollowup.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mNotificationTime.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        long triggerTime = calendar.getTimeInMillis();
        notificationIntent.putExtra("followupName", medicineName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    100,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
            );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), pendingIntent);
        } else {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    100,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), pendingIntent);
        }

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