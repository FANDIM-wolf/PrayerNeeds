package com.example.prayerneeds;

import java.time.LocalDate;

public class PrayerNeed {
    private int id;
    private int idCode;
    private String title;
    private String description;
    private LocalDate time;
    private int archived;
    private String name;

    public PrayerNeed(int id, int idCode, String title, String description, LocalDate time, int archived, String name) {
        this.id = id;
        this.idCode = idCode;
        this.title = title;
        this.description = description;
        this.time = time;
        this.archived = archived;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getIdCode() {
        return idCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTime() {
        return time;
    }

    public int getArchived() {
        return archived;
    }

    public String getName() {
        return name;
    }
}
