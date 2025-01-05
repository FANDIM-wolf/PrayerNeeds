package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

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
        do {
            idCode = UUID.randomUUID().toString();
            isUnique = isIdCodeUnique(idCode);
        } while (!isUnique);
        return idCode;
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
        String sql = "INSERT INTO member(id_code, name, birth_date) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCode);
            pstmt.setString(2, name);
            pstmt.setString(3, birthDate);
            pstmt.executeUpdate();
            System.out.println("Член церкви добавлен успешно.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
