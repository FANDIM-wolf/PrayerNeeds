package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberListWindowController implements Initializable {

    @FXML
    private TextArea membersTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMembers();
    }

    private void loadMembers() {
        StringBuilder membersData = new StringBuilder();
        String sql = "SELECT id_code, name, birth_date FROM member";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                membersData.append("id_code: ").append(rs.getString("id_code"))
                        .append(" ").append(rs.getString("name"))
                        .append(" ").append(rs.getString("birth_date"))
                        .append("\n");
            }

            membersTextArea.setText(membersData.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleAddMember() throws IOException {
        // Логика для добавления нового члена церкви
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prayerneeds/add_member.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/prayerneeds/styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Добавить члена церкви");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRemoveMember() {
        // Логика для удаления члена церкви из списка
        System.out.println("Убрать из списка");
    }
}



