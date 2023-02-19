package com.chilicoder.diabetesself_care.blood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BloodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BloodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Blood fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}