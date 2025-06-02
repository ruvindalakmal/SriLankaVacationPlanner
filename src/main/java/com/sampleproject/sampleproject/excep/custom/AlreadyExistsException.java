package com.sampleproject.sampleproject.excep.custom;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(String message) {
        super(message);
    }

}
