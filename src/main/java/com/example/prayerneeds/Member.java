package com.example.prayerneeds;

public class Member {
    private int idCode;
    private String name;

    public Member(int idCode, String name) {
        this.idCode = idCode;
        this.name = name;
    }

    public int getIdCode() {
        return idCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

