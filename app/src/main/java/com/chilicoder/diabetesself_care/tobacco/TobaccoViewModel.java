package com.chilicoder.diabetesself_care.tobacco;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TobaccoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TobaccoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tobacco fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}