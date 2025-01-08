package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveMemberController {

    @FXML
    private TextField idCodeField;

    @FXML
    private void handleRemoveMember() {
        String idCode = idCodeField.getText();
        if (idCode != null && !idCode.isEmpty()) {
            // Логика для удаления члена церкви по id_code
            System.out.println("Удаление члена церкви с id_code: " + idCode);

            // Удаление связанных нужд
            deletePrayerNeeds(idCode);

            // Удаление члена церкви
            deleteMember(idCode);
        } else {
            System.out.println("Пожалуйста, введите id_code.");
        }
    }

    private void deletePrayerNeeds(String idCode) {
        String sql = "DELETE FROM prayer_need WHERE id_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idCode);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Связанные нужды успешно удалены.");
            } else {
                System.out.println("Связанные нужды не найдены.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMember(String idCode) {
        String sql = "DELETE FROM member WHERE id_code = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idCode);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Член церкви успешно удален.");
            } else {
                System.out.println("Член церкви не найден.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

