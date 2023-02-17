package com.chilicoder.diabetesself_care.diet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DietViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DietViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Diet fragment");

        //this part is not used to write code in the application
    }

    LiveData<String> getText() {
        return mText;
    }
}
