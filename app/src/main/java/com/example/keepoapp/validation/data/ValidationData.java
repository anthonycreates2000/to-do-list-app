package com.example.keepoapp.validation.data;

abstract class ValidationData{
    public interface IErrorMessage{
        String errorMessage();
    }
    public IErrorMessage iErrorMessage;
    public String getErrorMessage(){
        return iErrorMessage.errorMessage();
    }
}
