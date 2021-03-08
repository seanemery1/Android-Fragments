package com.example.android_fragments;

public class Character {

    private String name;
    private String status;
    private String species;
    private String imageURL;
    private String gender;
    private String origin;
    private String location;
    private String appearances;

    public Character() {

    }

    public Character(String name, String status, String species,
                     String imageURL, String gender, String origin,
                     String location, String appearances) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.imageURL = imageURL;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.appearances = appearances.replace("https://rickandmortyapi.com/api/episode/", " ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppearances() {
        return appearances;
    }

    public void setAppearances(String appearances) {
        this.appearances = appearances;
    }
}
