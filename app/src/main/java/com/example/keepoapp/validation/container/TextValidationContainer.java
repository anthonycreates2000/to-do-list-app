package com.example.keepoapp.validation.container;

import com.example.keepoapp.validation.data.TextValidationData;

public class TextValidationContainer extends ValidationContainer{
    private TextValidationData[] textValidationData;
    private String value;
    private String eccounterErrorMessage = "";
    public TextValidationContainer(String value, TextValidationData ...textValidationData) {
        this.value = value;
        this.textValidationData = textValidationData;
    }
    @Override
    public boolean validate() {
        for (TextValidationData validation : textValidationData) {
            if (!validation.evaluateData(value)){
                eccounterErrorMessage = validation.getErrorMessage();
                return false;
            }
        }
        return true;
    }
    @Override
    public String getErrorMessage(){
        return eccounterErrorMessage;
    }
}
