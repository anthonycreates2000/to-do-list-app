package com.example.keepoapp.validation.container;

import android.widget.CheckBox;

import com.example.keepoapp.validation.data.CheckboxValidationData;

import com.example.keepoapp.validation.data.CheckboxValidationData;

public class CheckboxContainer extends ValidationContainer{
    private CheckboxValidationData[] validationData;
    private CheckBox[] checkBoxes;
    private String eccounterErrorMessage = "";
    public CheckboxContainer(CheckBox[] checkBoxes, CheckboxValidationData[] validationData){
        this.checkBoxes = checkBoxes;
        this.validationData = validationData;
    }
    @Override
    public boolean validate() {
        for (CheckboxValidationData validationData : validationData){
            if (!validationData.validateCheckBox(checkBoxes)){
                eccounterErrorMessage = validationData.getErrorMessage();
                return false;
            }
        }
        return true;
    }
    @Override
    public String getErrorMessage() {
        return eccounterErrorMessage;
    }
}
