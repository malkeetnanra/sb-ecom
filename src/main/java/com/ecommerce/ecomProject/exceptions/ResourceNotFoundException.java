package com.ecommerce.ecomProject.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String Field;
    String FieldName;
    Long fieldId;

    public ResourceNotFoundException (String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName,field, fieldName));
        this.resourceName = resourceName;
        this.Field = field;
        this.FieldName = fieldName;
    }

    public ResourceNotFoundException (String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName,field, fieldId));
        this.resourceName = resourceName;
        this.Field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String product, String productId, Object o) {
    }
}
