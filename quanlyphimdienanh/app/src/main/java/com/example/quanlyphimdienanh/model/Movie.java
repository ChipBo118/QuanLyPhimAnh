package com.example.quanlyphimdienanh.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String description;
    private MovieGenre genre;
    private String releaseDate;
    private String director;
    private String posterUrl;
    private double rating;

    public Movie(int id, String title, String description, MovieGenre genre, String releaseDate, String director, String posterUrl, double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        genre = MovieGenre.valueOf(in.readString());
        releaseDate = in.readString();
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
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MovieGenre getGenre() { return genre; }
    public void setGenre(MovieGenre genre) { this.genre = genre; }

    public String getGenreDisplayName() { return genre.getDisplayName(); }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(genre.name());
        dest.writeString(releaseDate);
        dest.writeString(director);
        dest.writeString(posterUrl);
        dest.writeDouble(rating);
    }
} 