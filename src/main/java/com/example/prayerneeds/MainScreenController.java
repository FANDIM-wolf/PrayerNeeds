package com.example.prayerneeds;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private void handleRecordNeed() {
        // Логика для записи нужды
        System.out.println("Записать нужду");
    }

    @FXML
    private void handleViewNeeds() {
        // Логика для просмотра нужд
        System.out.println("Просмотр нужд");
    }

    @FXML
    private void handleViewMembers() throws IOException {
        // Логика для просмотра членов церкви
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prayerneeds/memberlist.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/prayerneeds/styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Члены церкви");
        stage.setScene(scene);
        stage.show();
    }

}

