package com.example.keepoapp.validation.data;

public class InRangeLengthTextValidation extends TextValidationData {
    private int minimumValue;
    private int maximumValue;

    public InRangeLengthTextValidation(int minimumValue, int maximumValue) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    @Override
    public boolean evaluateData(String value) {
        int integerValue = value.length();
        if (integerValue < minimumValue || integerValue > maximumValue){
            return false;
        }
        return true;
    }
}
