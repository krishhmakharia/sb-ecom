package com.ecom.project.exceptions;


public class ResourceNotFoundException extends RuntimeException{
    String resource;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException () {
    }

    public ResourceNotFoundException(String resource, String field, String fieldName) {
        super(String.format("%s not found with %s : %s",resource,field,fieldName));
        this.resource = resource;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resource, String field,Long fieldId) {
        super(String.format("%s not found with %s : %s",resource,field,fieldId));
        this.resource = resource;
        this.fieldId = fieldId;
        this.field = field;
    }
}
