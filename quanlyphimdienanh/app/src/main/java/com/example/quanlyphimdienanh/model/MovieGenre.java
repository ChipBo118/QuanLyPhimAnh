package com.example.quanlyphimdienanh.model;

import android.content.Context;

import com.example.quanlyphimdienanh.R;

public enum MovieGenre {
    ACTION,
    COMEDY,
    HORROR,
    ROMANCE;

    public String getDisplayName(Context context) {
        switch (this) {
            case ACTION:
                return context.getString(R.string.action);
            case COMEDY:
                return context.getString(R.string.comedy);
            case HORROR:
                return context.getString(R.string.horror);
            case ROMANCE:
                return context.getString(R.string.romance);
            default:
                return "";
        }
    }

    public static MovieGenre fromDisplayName(Context context, String displayName) {
        for (MovieGenre genre : values()) {
            if (genre.getDisplayName(context).equals(displayName)) {
                return genre;
            }
        }
        return null;
    }
} 