package com.chilicoder.diabetesself_care.med;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is medicine fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}