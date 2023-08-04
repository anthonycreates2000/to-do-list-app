package com.example.keepoapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.databinding.ToDoFormLayoutBinding;
import com.example.keepoapp.viewmodel.ChangesToDoViewModel;

public abstract class ChangesToDoActivity extends BackableConnectivityActivity {
    protected ToDoFormLayoutBinding formBinding;
    protected ChangesToDoViewModel changesToDoViewModel;
    void initializeFirstSetup(){
        formBinding.progressBar.setVisibility(View.GONE);
        formBinding.tvError.setText("");
    }
    int getDescriptionLength(){
        int length = formBinding.edtDescription.getText().toString().length();
        return length;
    }
    void setDescriptionLengthTextView(){
        formBinding.tvDescriptionLength.setText(  getDescriptionLength() + "/100");
        formBinding.tvDescriptionLength.setTextColor(Color.rgb(0, 0, 0));
    }
    abstract void exitActivity();
    abstract void setIntentData(Bundle savedInstanceState);
    void initializeViewModel(){
        changesToDoViewModel.iBeforeConnect = () -> {
            formBinding.progressBar.setVisibility(View.VISIBLE);
        };
        changesToDoViewModel.getToDoLiveData().observe(this, (toDo) -> {
            formBinding.setTodoviewmodel(toDo);
            setDescriptionLengthTextView();
        });
        changesToDoViewModel.getBottomDialogMessageLiveData().observe(this, (message) -> {
            formBinding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(message);
        });
        changesToDoViewModel.getCommonResponseLiveData().observe(this, (commonResponse) -> {
            formBinding.progressBar.setVisibility(View.GONE);
            alertSuccessToUser(commonResponse);
        });
        resetNoErrorDescription();
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    @Override
    public void backClickListener() {
        exitActivity();
    }

    void resetNoErrorDescription(){
        changesToDoViewModel.iNoErrorDescription = () -> {
            formBinding.tvError.setText("");
            formBinding.tvDescriptionLength.setTextColor(Color.rgb(0, 0, 0));
        };
    }
    void alertSuccessToUser(CommonResponse commonResponse){
        Toast.makeText(this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
        exitActivity();
    }
    void setListeners(){
        formBinding.edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setDescriptionLengthTextView();
                changesToDoViewModel.iFailedCheck = (errorMessage) -> {
                    formBinding.tvDescriptionLength.setTextColor(Color.rgb(255, 0, 0));
                    formBinding.tvError.setText(errorMessage);
                    resetNoErrorDescription();
                };
                changesToDoViewModel.evaluateDescriptionText(formBinding.edtDescription.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        changesToDoViewModel.saveToDoData(formBinding.getTodoviewmodel());
        super.onSaveInstanceState(outState);
    }

    public class ApplyFABListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String title = formBinding.edtTitle.getText().toString();
            String description = formBinding.edtDescription.getText().toString();
            changesToDoViewModel.iFailedCheck = (errorMessage) -> {
                formBinding.tvError.setText(errorMessage);
            };

            changesToDoViewModel.evaluateDataThenApply(title, description);
        }
    }
}
