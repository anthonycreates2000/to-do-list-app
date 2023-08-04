package com.example.keepoapp.validation.data;

public abstract class TextValidationData extends ValidationData{
    public abstract boolean evaluateData(String text);
    public String getErrorMessage(){
        return iErrorMessage.errorMessage();
    }
}
