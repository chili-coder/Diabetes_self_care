package com.chilicoder.diabetesself_care.footcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chilicoder.diabetesself_care.AlarmActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.diet.AlarmDietActivity;
import com.chilicoder.diabetesself_care.diet.DatabaseHelperDiet;
import com.chilicoder.diabetesself_care.followup.AlarmFollowupActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import rm.com.clocks.ClockImageView;

public class AlarmFootActivity extends AppCompatActivity {
    private TextView textViewMedicineName, textViewTime;
    private Vibrator mVibrator;
    private Button buttonDismiss, buttonSnooze, buttonTake;
    private ClockImageView clockImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_foot);


        getSupportActionBar().setTitle("Time For Foot Care ");



        Intent intent = getIntent();

        clockImageView = findViewById(R.id.clock_alarm_foot); //Creating the clock icon
        Calendar mCurrentTime = Calendar.getInstance();// Set to current system time
        clockImageView.animateToTime(mCurrentTime.get(Calendar.HOUR_OF_DAY), mCurrentTime.get(Calendar.MINUTE)); //saatin gösterilmesi

        String medicineName = intent.getStringExtra("footName"); // The drug name is shown here
        // The drug name is shown here
        textViewMedicineName = findViewById(R.id.text_view_medicine_name_alarm_foot);
        textViewMedicineName.setText(medicineName);

        textViewTime = findViewById(R.id.text_view_time_alarm_foot);
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        textViewTime.setText(format.format(mCurrentTime.getTime()));


        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); //give the vibration effect
        long[] pattern = {0, 1000, 1000};
        mVibrator.vibrate(pattern, 0);


        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();



        DatabaseHelperFoot databaseHelper = new DatabaseHelperFoot(this);
        List<String> timingList = databaseHelper.getTimings(medicineName);
        for (int i = 0; i < timingList.size(); i++) {
            Log.i(AlarmFootActivity.class.getName(), timingList.get(i));
        }

        Calendar nextAlarmTime = Calendar.getInstance();
        nextAlarmTime.set(Calendar.SECOND, 0);
        nextAlarmTime.set(Calendar.MILLISECOND, 0);


        buttonDismiss = findViewById(R.id.button_dismiss_foot); // button to reject alarm
        // It turns off the alarm completely and does not ring again at the same time the next day.
        buttonDismiss.setOnClickListener(v -> {
            for (int i = 0; i < timingList.size(); i++) {
                String [] time = timingList.get(i).split(":");
                nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                nextAlarmTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));
                if (mCurrentTime.before(nextAlarmTime)) {
                    break;
                } else if (mCurrentTime.after(nextAlarmTime) && i == (timingList.size() - 1) ) {
                    nextAlarmTime.set(Calendar.DAY_OF_MONTH, nextAlarmTime.get(Calendar.DAY_OF_MONTH) + 1);
                    time = timingList.get(0).split(":");
                    nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                    nextAlarmTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));
                }
            }
            mVibrator.cancel();
            ringtone.stop();
            Log.i("daysLeft", String.valueOf(databaseHelper.noOfDaysLeft(medicineName, nextAlarmTime)));
            if (databaseHelper.noOfDaysLeft(medicineName, nextAlarmTime) > 0)
                setAlarmDiet(nextAlarmTime, medicineName);
            finish();
        });

        buttonSnooze = findViewById(R.id.button_snooze_foot); //snooze alarm
        buttonSnooze.setOnClickListener(v -> {
            nextAlarmTime.set(Calendar.MINUTE, nextAlarmTime.get(Calendar.MINUTE) + (1440*7)); // delay 10  minute
            mVibrator.cancel(); // turn off vibration
            ringtone.stop(); //stop the alarm
            setAlarmDiet(nextAlarmTime, medicineName);//set the new alarm time and name back to the alarm setter.
            finish();
        });
        // The acknowledgment button is written between these two parts.
        buttonTake = findViewById(R.id.button_take_foot); //snooze the alarm for one day
        buttonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAlarmTime.set(Calendar.MINUTE, nextAlarmTime.get(Calendar.MINUTE)+(1440*7)); //alarmı bir sonraki güne atar
                mVibrator.cancel();
                ringtone.stop();
                setAlarmDiet(nextAlarmTime,medicineName);
                finish();
            }
        });
        // notifies the application that the drug has been taken and sets the alarm for the next day and repeats at the same time
        //reminds.




    }

    public void setAlarmDiet(Calendar mAlarmTime, String medicineName) {
        AlarmManager alarmManagerNew = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmFootActivity.class);
        intent.putExtra("footName", medicineName);

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (AlarmFootActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);
            alarmManagerNew.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(),  pendingIntent);
        } else {
            pendingIntent = PendingIntent.getActivity
                    (AlarmFootActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerNew.setExact(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), pendingIntent);
        }


    }
}

