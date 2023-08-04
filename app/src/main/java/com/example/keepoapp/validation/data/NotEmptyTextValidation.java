package com.example.keepoapp.validation.data;

public class NotEmptyTextValidation extends TextValidationData {
    @Override
    public boolean evaluateData(String text) {
        return !text.trim().isEmpty();
    }
}
