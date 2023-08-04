package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.R;
import com.example.keepoapp.data.response.SearchToDoData;
import com.example.keepoapp.network.api.SearchToDoAPIManager;
import com.example.keepoapp.validation.ValidationController;
import com.example.keepoapp.validation.container.CheckboxContainer;
import com.example.keepoapp.validation.container.TextValidationContainer;
import com.example.keepoapp.validation.container.ValidationContainer;
import com.example.keepoapp.validation.data.CheckBoxSelectedValidation;
import com.example.keepoapp.validation.data.NotEmptyTextValidation;
import com.example.keepoapp.validation.data.TextValidationData;

import java.util.HashMap;

public class SearchToDoViewModel extends ConnectivityAndroidViewModel{
    private MutableLiveData<SearchToDoData> searchToDoDataLiveData = new MutableLiveData<>();
    public SearchToDoViewModel(@NonNull Application application) {
        super(application);
    }
    public void loadPeopleData(String searchValue, HashMap<String, Integer> categories){
        SearchToDoAPIManager searchToDoAPIManager = new SearchToDoAPIManager(searchValue, categories);
        searchToDoAPIManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        searchToDoAPIManager.iOnSuccess = (call, response) -> {
            SearchToDoData searchToDoData = (SearchToDoData) response.body();
            Log.d("Information", searchToDoData.toString());
            Log.d("Information", searchToDoData.getMessage());
            Log.d("Information", response.body().toString());
            searchToDoDataLiveData.postValue(searchToDoData);
        };
        searchToDoAPIManager.iOnFailed = (call, t) -> {
            bottomDialogMessageLiveData.postValue(t.getMessage());
        };
        searchToDoAPIManager.startRequest();
    }
    public void validateThenSearch(String searchValue, CheckBox[] checkBoxes){
        HashMap<String, Integer> hashSearch = new HashMap<String, Integer>();
        NotEmptyTextValidation notEmptyTextValidation = new NotEmptyTextValidation();
        notEmptyTextValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.field_text_not_empty);
        };
        TextValidationData[] textValidationData = new TextValidationData[] {notEmptyTextValidation};
        ValidationContainer editSearchContainer = new TextValidationContainer(searchValue, textValidationData);

        CheckBoxSelectedValidation selectedValidation = new CheckBoxSelectedValidation();

        selectedValidation.iValue = (value, tag, isChecked) -> {
            if (isChecked){
                hashSearch.put(tag, 1);
            }
            else{
                hashSearch.put(tag, 0);
            }
        };
        CheckBoxSelectedValidation[] searchCheckBoxValidations = new CheckBoxSelectedValidation[] {selectedValidation};
        ValidationContainer checkBoxContainer = new CheckboxContainer(checkBoxes, searchCheckBoxValidations);
        selectedValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.checkbox_no_select_error);
        };
        ValidationContainer[] containers = new ValidationContainer[]{editSearchContainer, checkBoxContainer};
        ValidationController validationController = new ValidationController(containers);
        validationController.iOnSuccess = () -> {
            loadPeopleData(searchValue, hashSearch);
        };
        validationController.iOnError = (error) -> {
            Log.e("Information", "Called: " + error);
            errorLiveData.postValue(error);
        };
        validationController.evaluate();
    }
    public LiveData<String> getErrorLiveData(){
        return errorLiveData;
    }
    public LiveData<SearchToDoData> getSearchToDoDataLiveData(){
        return searchToDoDataLiveData;
    }

}
