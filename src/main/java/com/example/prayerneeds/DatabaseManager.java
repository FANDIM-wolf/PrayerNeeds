package com.example.prayerneeds;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/prayer_needs_data_base.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id_code, name FROM member";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                members.add(new Member(rs.getInt("id_code"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return members;
    }

    public static void savePrayerNeed(int idCode, String title, String description, String time, int archived) {
        String sql = "INSERT INTO prayer_need (id_code, title, description, time, archived) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCode);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, time);
            pstmt.setInt(5, archived);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Object[]> getPrayerNeeds() {
        List<Object[]> prayerNeeds = new ArrayList<>();
        String sql = "SELECT id, id_code, title, description, time, archived FROM prayer_need WHERE archived = 0";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] need = new Object[]{
                        rs.getInt("id"), // Получаем id нужды
                        rs.getInt("id_code"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("time"),
                        rs.getInt("archived")
                };
                prayerNeeds.add(need);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prayerNeeds;
    }
    public static List<Object[]> getPrayerNeedsWithNames() {
        List<Object[]> prayerNeeds = new ArrayList<>();
        String sql = "SELECT pn.id, pn.id_code, pn.title, pn.description, pn.time, pn.archived, m.name " +
                "FROM prayer_need pn " +
                "JOIN member m ON pn.id_code = m.id_code AND pn.archived =0";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                prayerNeeds.add(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("id_code"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("time"),
                        rs.getInt("archived"),
                        rs.getString("name")
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return prayerNeeds;
    }

}


