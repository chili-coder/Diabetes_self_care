package com.chilicoder.diabetesself_care.followup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FollowupViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FollowupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Followup fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}