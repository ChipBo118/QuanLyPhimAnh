package com.example.quanlyphimdienanh.model;

public enum MovieGenre {
    ACTION("Hành động"),
    COMEDY("Hài"),
    HORROR("Kinh dị"),
    ROMANCE("Tình cảm");

    private final String displayName;

    MovieGenre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MovieGenre fromDisplayName(String displayName) {
        for (MovieGenre genre : values()) {
            if (genre.displayName.equals(displayName)) {
                return genre;
            }
        }
        return null;
    }
} 