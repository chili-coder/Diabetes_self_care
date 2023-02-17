package com.chilicoder.diabetesself_care.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhysicalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PhysicalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Physical Activity fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}
