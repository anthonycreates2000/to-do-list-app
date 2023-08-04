package com.example.keepoapp.validation.data;

import android.widget.CheckBox;

public abstract class CheckboxValidationData extends ValidationData{
    public abstract boolean validateCheckBox(CheckBox[] checkBoxes);
    public String getErrorMessage() {return iErrorMessage.errorMessage();}
}

