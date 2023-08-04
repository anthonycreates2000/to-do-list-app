package com.example.keepoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

abstract class ConnectivityAndroidViewModel extends AndroidViewModel {
    public interface IBeforeConnect{
        void beforeConnect();
    }
    protected MutableLiveData<String> bottomDialogMessageLiveData = new MutableLiveData<>();
    protected MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public IBeforeConnect iBeforeConnect;
    public ConnectivityAndroidViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getErrorLiveData(){return errorLiveData;}
    public LiveData<String> getBottomDialogMessageLiveData() {
        return bottomDialogMessageLiveData;
    }
}
