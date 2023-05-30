package com.example.finalproj.enumm;

public enum Status {
    ACCEPTED("Принят"),
    CONFIRMED("Оформлен"),
    PENDING("Ожидает"),
    RECEIVED("Получен");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}