package com.chilicoder.diabetesself_care.footcare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FootViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FootViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Foot fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}