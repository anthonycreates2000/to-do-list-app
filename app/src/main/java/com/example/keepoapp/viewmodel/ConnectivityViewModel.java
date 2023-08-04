package com.example.keepoapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

abstract class ConnectivityViewModel extends ViewModel {
    protected MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    protected MutableLiveData<String> bottomDialogMessageLiveData = new MutableLiveData<>();
    public interface IBeforeConnect{
        void beforeConnect();
    }
    public LiveData<String> getBottomDialogMessageLiveData() {
        return bottomDialogMessageLiveData;
    }
    public IBeforeConnect iBeforeConnect;
}
