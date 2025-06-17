package com.example.quanlyphimdienanh.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String description;
    private MovieGenre genre;
    private String year;
    private String director;
    private String posterUrl;
    private double rating;

    public Movie(String title, String description, MovieGenre genre, String year, String director, String posterUrl, double rating) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.director = director;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        description = in.readString();
        genre = MovieGenre.valueOf(in.readString());
        year = in.readString();
        director = in.readString();
        posterUrl = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MovieGenre getGenre() { return genre; }
    public void setGenre(MovieGenre genre) { this.genre = genre; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getGenreDisplayName(Context context) {
        return genre.getDisplayName(context);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(genre.name());
        dest.writeString(year);
        dest.writeString(director);
        dest.writeString(posterUrl);
        dest.writeDouble(rating);
    }
} 