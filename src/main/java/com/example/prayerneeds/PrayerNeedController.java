package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PrayerNeedController {

    @FXML
    private VBox needsContainer;

    @FXML
    public void initialize() {
        // Загрузка данных при инициализации контроллера
        List<Object[]> prayerNeeds = DatabaseManager.getPrayerNeeds();
        setData(prayerNeeds);
    }

    public void setData(List<Object[]> prayerNeeds) {
        VBox dynamicContent = new VBox(10);

        for (Object[] needObj : prayerNeeds) {
            HBox needBox = new HBox(10);
            needBox.setStyle("-fx-padding-right: 20; -fx-spacing: 20; -fx-alignment: CENTER;"); // Применяем стиль для центрирования

            // Assuming needObj is an array with id, id_code, title, description, time, archived
            int id = (int) needObj[0];
            int idCode = (int) needObj[1];
            String title = (String) needObj[2];
            String description = (String) needObj[3];
            String time = (String) needObj[4];
            int archived = (int) needObj[5];

            // Combine all data into one string with spaces
            String combinedText = title + " / " + time;

            Label needLabel = new Label(combinedText);
            needLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5px;"); // Применяем стиль напрямую

            Button viewButton = new Button("Посмотреть");
            viewButton.setStyle("-fx-padding: 10 20; -fx-background-color: #00CC00; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 5;"); // Применяем стиль напрямую

            // Add action handlers for buttons if needed
            final int finalId = id;
            final int finalIdCode = idCode;
            final String finalTitle = title;
            final String finalDescription = description;
            final String finalTime = time;
            final int finalArchived = archived;

            viewButton.setOnAction(event -> handleViewNeed(finalId, finalIdCode, finalTitle, finalDescription, finalTime, finalArchived));

            needBox.getChildren().addAll(needLabel, viewButton);
            dynamicContent.getChildren().add(needBox);
        }

        // Добавляем VBox в ScrollPane
        ScrollPane scrollPane = new ScrollPane(dynamicContent);
        scrollPane.setFitToWidth(true);
        needsContainer.getChildren().add(scrollPane);
    }

    private void handleViewNeed(int id, int idCode, String title, String description, String time, int archived) {
        // Implement view functionality
        System.out.println("View: ID = " + id + ", ID Code = " + idCode + ", Title = " + title + ", Description = " + description + ", Time = " + time + ", Archived = " + archived);

        // Открываем новое окно
        openViewNeedWindow(id, idCode, title, description, time, archived);
    }

    private void openViewNeedWindow(int id, int idCode, String title, String description, String time, int archived) {
        VBox root = new VBox(10);
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(20));

        Label headerLabel = new Label("Просмотр нужды");
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-family: Arial, sans-serif;");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Имя члена Церкви");
        nameInput.setText(title); // Заполняем поле данными

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Описание");
        descriptionArea.setText(description); // Заполняем поле данными
        descriptionArea.setPrefHeight(100); // Увеличиваем высоту поля
        descriptionArea.setPrefWidth(300); // Увеличиваем ширину поля

        Label timeLabel = new Label("Время: " + time);
        timeLabel.setStyle("-fx-font-size: 14; -fx-font-family: Arial, sans-serif;");

        Button archiveButton = new Button("Архивировать");
        archiveButton.setOnAction(event -> handleArchive(id));
        archiveButton.setStyle("-fx-padding: 10 20;\n" +
                "    -fx-background-color: #FF0000;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 16px;\n" +
                "    -fx-background-radius: 5;");
        root.getChildren().addAll(headerLabel, nameInput, descriptionArea, timeLabel, archiveButton);

        Scene scene = new Scene(root, 400, 300);
        Stage stage = new Stage();
        stage.setTitle("Просмотр нужды");
        stage.setScene(scene);
        stage.show();
    }

    private void handleArchive(int id) {
        // Логика для архивирования нужды
        System.out.println("Архивирование нужды с ID: " + id);
        // Добавьте здесь логику для архивирования нужды в базе данных
    }
}
