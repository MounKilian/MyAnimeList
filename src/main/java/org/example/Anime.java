package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Anime {
    private List<Anime> anime;
    private String name;
    private String imgSrc;
    private String type;
    private String episodeAndSeason;
    private String rank;
    private String director;
    private String description;

    public Anime(){}

    public Anime(String name, String type, String imgSrc) {
        this.name = name;
        this.type = type;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }

    public String getEpisodeAndSeason() {
        return episodeAndSeason;
    }

    public void setEpisodeAndSeason(String episodeAndSeason) {
        this.episodeAndSeason = episodeAndSeason;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
