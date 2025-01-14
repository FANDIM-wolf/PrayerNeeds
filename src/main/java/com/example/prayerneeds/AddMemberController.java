package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class AddMemberController {

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private void handleAddMember() {
        String name = nameField.getText();
        LocalDate birthDate = birthDatePicker.getValue();

        if (name.isEmpty() || birthDate == null) {
            System.out.println("Пожалуйста, заполните все поля.");
            return;
        }

        String idCode = generateUniqueIdCode();
        if (idCode != null) {
            addMemberToDatabase(idCode, name, birthDate.toString());
        } else {
            System.out.println("Не удалось сгенерировать уникальный id_code.");
        }
    }

    private String generateUniqueIdCode() {
        String idCode;
        boolean isUnique;
        Random random = new Random();
        do {
            idCode = generateRandomIdCode(random);
            isUnique = isIdCodeUnique(idCode);
        } while (!isUnique);
        return idCode;
    }

    private String generateRandomIdCode(Random random) {
        StringBuilder idCode = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            idCode.append(random.nextInt(10)); // Генерируем случайное число от 0 до 9
        }
        return idCode.toString();
    }

    private boolean isIdCodeUnique(String idCode) {
        String sql = "SELECT COUNT(*) FROM member WHERE id_code = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void addMemberToDatabase(String idCode, String name, String birthDate) {
        // Вставка данных в таблицу member
        String sqlMember = "INSERT INTO member (id_code, name, birth_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmtMember = conn.prepareStatement(sqlMember)) {

            pstmtMember.setString(1, idCode);
            pstmtMember.setString(2, name);
            pstmtMember.setString(3, birthDate);
            pstmtMember.executeUpdate();

            // Вставка данных в таблицу members_in_church
            String sqlMembersInChurch = "INSERT INTO members_in_church (id_code, name) VALUES (?, ?)";
            try (PreparedStatement pstmtMembersInChurch = conn.prepareStatement(sqlMembersInChurch)) {
                pstmtMembersInChurch.setString(1, idCode);
                pstmtMembersInChurch.setString(2, name);
                pstmtMembersInChurch.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
