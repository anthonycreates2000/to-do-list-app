package com.example.keepoapp.validation.data;

import android.widget.CheckBox;

import java.util.ArrayList;

public class CheckBoxSelectedValidation extends CheckboxValidationData{
    public interface IValue{
        void selectedCheckBoxes(String selectedCheckBoxValue, String tag, boolean checked);
    }
    public IValue iValue;
    @Override
    public boolean validateCheckBox(CheckBox[] checkBoxes) {
        boolean isSelected = false;
        for (CheckBox checkBox: checkBoxes){
            if (checkBox.isChecked()){
                isSelected = true;
            }
            iValue.selectedCheckBoxes(checkBox.getText().toString(), checkBox.getTag().toString(), checkBox.isChecked());
        }
        return isSelected;
    }


}
