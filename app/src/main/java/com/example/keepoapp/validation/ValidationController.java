package com.example.keepoapp.validation;

import com.example.keepoapp.validation.container.ValidationContainer;

public class ValidationController {
    private ValidationContainer[] containers;
    public interface IOnError{
        void onError(String message);
    }
    public interface IOnSuccess{
        void onSuccess();
    }
    public IOnSuccess iOnSuccess;
    public IOnError iOnError;
    public ValidationController(ValidationContainer ...containers) {
        this.containers = containers;
    }
    public void evaluate(){
        for (ValidationContainer container : containers) {
            if (!container.validate()){
                iOnError.onError(container.getErrorMessage());
                return;
            }
        }
        iOnSuccess.onSuccess();
    }
}
