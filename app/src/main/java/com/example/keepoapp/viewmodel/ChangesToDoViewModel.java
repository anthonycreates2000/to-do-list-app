package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.R;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.validation.ValidationController;
import com.example.keepoapp.validation.container.TextValidationContainer;
import com.example.keepoapp.validation.container.ValidationContainer;
import com.example.keepoapp.validation.data.InRangeLengthTextValidation;
import com.example.keepoapp.validation.data.NotEmptyTextValidation;
import com.example.keepoapp.validation.data.TextValidationData;

public abstract class ChangesToDoViewModel extends ConnectivityAndroidViewModel{
    protected MutableLiveData<String> userIDLiveData = new MutableLiveData<>();
    protected MutableLiveData<ToDo> toDoMutableLiveData = new MutableLiveData<>();
    protected MutableLiveData<CommonResponse> commonResponseLiveData = new MutableLiveData<>();

    public ChangesToDoViewModel(@NonNull Application application) {
        super(application);
    }

    public interface IFailedCheck{
        void failedCheck(String message);
    }
    public interface INoErrorDescription{
        void noErrorDescription();
    }
    public IFailedCheck iFailedCheck;
    public INoErrorDescription iNoErrorDescription;
    public abstract void applyData();
    public void evaluateDataThenApply(String title, String description){
        Log.d("Title", title);
        NotEmptyTextValidation notEmptyValidation = new NotEmptyTextValidation();
        InRangeLengthTextValidation inRangeLengthValidation = new InRangeLengthTextValidation(0, 100);
        inRangeLengthValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.exceeded_length);
        };
        notEmptyValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.cannot_empty_string);
        };
        TextValidationData[] titleValidations = new TextValidationData[]{notEmptyValidation};
        TextValidationData[] descriptionValidations = new TextValidationData[] {notEmptyValidation, inRangeLengthValidation};

        TextValidationContainer titleContainer = new TextValidationContainer(title, titleValidations);
        TextValidationContainer descriptionContainer = new TextValidationContainer(description, descriptionValidations);

        ValidationContainer[] containers = new ValidationContainer[]{titleContainer, descriptionContainer};
        ValidationController controller = new ValidationController(containers);
        controller.iOnError = (errorMessage) -> {
            iFailedCheck.failedCheck(errorMessage);
        };
        controller.iOnSuccess = () -> {
            toDoMutableLiveData.getValue().setTitle(title);
            toDoMutableLiveData.getValue().setDescription(description);
            this.applyData();
        };
        controller.evaluate();
    }
    public void evaluateDescriptionText(String description){
        InRangeLengthTextValidation inRangeLengthValidation = new InRangeLengthTextValidation(0, 100);
        inRangeLengthValidation.iErrorMessage = () -> {
            return "Your description exceeded the maximum words!";
        };
        TextValidationData[] descriptionValidations = new TextValidationData[]{inRangeLengthValidation};
        TextValidationContainer descriptionTextValidationContainer = new TextValidationContainer(description, descriptionValidations);

        ValidationContainer[] validationContainers = new ValidationContainer[]{descriptionTextValidationContainer};
        ValidationController controller = new ValidationController(validationContainers);
        controller.iOnError = (errorMessage) -> {
            iFailedCheck.failedCheck(errorMessage);
        };
        controller.iOnSuccess = () -> {
            iNoErrorDescription.noErrorDescription();
        };
        controller.evaluate();
    }
    public void saveToDoData(String title, String description){
        ToDo todo = new ToDo(this.toDoMutableLiveData.getValue().getTodo_id(), title, description);
        this.toDoMutableLiveData.postValue(todo);
    }
    public void setUserID(String user_id){
        this.userIDLiveData.setValue(user_id);
    }
    public void saveToDoData(ToDo todo){
        this.toDoMutableLiveData.postValue(todo);
    }
    public LiveData<ToDo> getToDoLiveData(){
        return toDoMutableLiveData;
    }
    public LiveData<CommonResponse> getCommonResponseLiveData(){
        return commonResponseLiveData;
    }
}
