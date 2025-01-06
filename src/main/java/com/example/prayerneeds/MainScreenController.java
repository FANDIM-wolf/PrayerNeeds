package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainScreenController {

    @FXML
    private void handleRecordNeed() throws IOException {
        // Логика для записи нужды
        System.out.println("Записать нужду");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prayerneeds/add_need.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/prayerneeds/styles.css").toExternalForm());

        Stage stage = new Stage();
        InputStream iconStream = MainScreenController.class.getResourceAsStream("/bird.png");
        if (iconStream != null) {
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            System.out.println("Icon loaded successfully!");
        } else {
            System.out.println("Icon not found!");
        }
        stage.setTitle("Добавить нужду");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleViewNeeds() {
        // Логика для просмотра нужд
        System.out.println("Просмотр нужд");

        try {
            // Создаем новый FXMLLoader для загрузки FXML-файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prayerneeds/prayer_need.fxml"));

            // Загружаем FXML-файл
            Parent root = loader.load();

            // Создаем новую сцену и добавляем CSS стили
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/prayerneeds/styles.css").toExternalForm());

            // Создаем новое окно и устанавливаем сцену
            Stage stage = new Stage();
            InputStream iconStream = MainScreenController.class.getResourceAsStream("/bird.png");
            if (iconStream != null) {
                Image icon = new Image(iconStream);
                stage.getIcons().add(icon);
                System.out.println("Icon loaded successfully!");
            } else {
                System.out.println("Icon not found!");
            }
            stage.setTitle("Просмотр нужд");
            stage.setScene(scene);

            // Отображаем новое окно
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Можно добавить логику для обработки ошибки, например, показать сообщение об ошибке пользователю
        }
    }

    @FXML
    private void handleViewMembers() throws IOException {
        // Логика для просмотра членов церкви
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/prayerneeds/memberlist.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/prayerneeds/styles.css").toExternalForm());

        Stage stage = new Stage();
        InputStream iconStream = MainScreenController.class.getResourceAsStream("/bird.png");
        if (iconStream != null) {
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            System.out.println("Icon loaded successfully!");
        } else {
            System.out.println("Icon not found!");
        }
        stage.setTitle("Члены церкви");
        stage.setScene(scene);
        stage.show();
    }
}
