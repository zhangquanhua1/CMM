package com.ConstructionManagement.framework.config;

import java.security.Principal;

public class CustomPrinciple implements Principal {

    private String userId;

    @Override
    public String getName() {
        return this.userId;
    }

    public CustomPrinciple() {

    }

    public CustomPrinciple(String userId) {
        this.userId = userId;
    }
}
