package com.demo.phonebook.exception;

import java.util.ArrayList;
import java.util.List;

public class PayloadValidationResponse {
    private List<ExceptionMessage> violations = new ArrayList<>();

    public List<ExceptionMessage> getViolations() {
        return violations;
    }

    public void setViolations(List<ExceptionMessage> violations) {
        this.violations = violations;
    }
}
