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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrayerNeedController {

    @FXML
    private VBox needsContainer;

    @FXML
    public void initialize() {
        // Загрузка данных при инициализации контроллера
        List<Object[]> prayerNeeds = DatabaseManager.getPrayerNeedsWithNames();
        setData(prayerNeeds);
    }

    public void setData(List<Object[]> prayerNeeds) {
        VBox dynamicContent = new VBox(10);

        // Преобразование списка массивов в список объектов PrayerNeed
        List<PrayerNeed> prayerNeedList = prayerNeeds.stream()
                .map(need -> new PrayerNeed(
                        (int) need[0],
                        (int) need[1],
                        (String) need[2],
                        (String) need[3],
                        LocalDate.parse((String) need[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        (int) need[5],
                        (String) need[6]
                ))
                .collect(Collectors.toList());

        // Сортировка нужд по дате
        List<PrayerNeed> sortedPrayerNeeds = prayerNeedList.stream()
                .sorted(Comparator.comparing(PrayerNeed::getTime).reversed())
                .collect(Collectors.toList());

        for (PrayerNeed need : sortedPrayerNeeds) {
            HBox needBox = new HBox(10);
            needBox.setStyle("-fx-padding-right: 20; -fx-spacing: 20; -fx-alignment: CENTER;"); // Применяем стиль для центрирования

            int id = need.getId();
            int idCode = need.getIdCode();
            String title = need.getTitle();
            String description = need.getDescription();
            LocalDate time = need.getTime();
            int archived = need.getArchived();
            String name = need.getName();

            // Изменение формата даты
            String formattedDate = time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Combine all data into one string with spaces
            String combinedText = name + " - " + title + " / " + formattedDate;

            Label needLabel = new Label(combinedText);
            needLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5px;"); // Применяем стиль напрямую

            Button viewButton = new Button("Посмотреть");
            viewButton.setStyle("-fx-padding: 10 20; -fx-background-color: #00CC00; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 5;"); // Применяем стиль напрямую

            // Add action handlers for buttons if needed
            viewButton.setOnAction(event -> handleViewNeed(id, idCode, title, description, formattedDate, archived, name));

            needBox.getChildren().addAll(needLabel, viewButton);
            dynamicContent.getChildren().add(needBox);
        }

        // Добавляем VBox в ScrollPane
        ScrollPane scrollPane = new ScrollPane(dynamicContent);
        scrollPane.setFitToWidth(true);
        needsContainer.getChildren().add(scrollPane);
    }

    private void handleViewNeed(int id, int idCode, String title, String description, String time, int archived, String name) {
        // Implement view functionality
        System.out.println("View: ID = " + id + ", ID Code = " + idCode + ", Title = " + title + ", Description = " + description + ", Time = " + time + ", Archived = " + archived + ", Name = " + name);

        // Открываем новое окно
        openViewNeedWindow(id, idCode, title, description, time, archived, name);
    }

    private void openViewNeedWindow(int id, int idCode, String title, String description, String time, int archived, String name) {
        VBox root = new VBox(10);
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(20));

        Label headerLabel = new Label("Просмотр нужды");
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-family: Arial, sans-serif;");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Имя члена Церкви");
        nameInput.setText(name); // Заполняем поле данными

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

        // SQL-запрос для обновления значения archived
        String sql = "UPDATE prayer_need SET archived = 1 WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Нужда успешно архивирована.");
            } else {
                System.out.println("Нужда с ID " + id + " не найдена.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void saveArchivedToCSV() {
        // SQL-запрос для получения всех строк с archived = 1
        String sql = "SELECT id, id_code, title, description, time FROM prayer_need WHERE archived = 1";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Используем BufferedWriter с указанием кодировки UTF-8 и BOM
            try (BufferedWriter csvWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("archived_prayer_needs.csv", false), StandardCharsets.UTF_8))) {

                // Добавляем BOM для UTF-8
                csvWriter.write("\uFEFF");
                csvWriter.write("ID,ID Code,Title,Description,Time,Name\n");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idCode = rs.getInt("id_code");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String time = rs.getString("time");

                    // Изменение формата даты
                    LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // Получение имени члена церкви по id_code
                    String name = getMemberNameByIdCode(conn, idCode);

                    // Обработка специальных символов в строке (кавычки, запятые)
                    title = escapeCsvField(title);
                    description = escapeCsvField(description);
                    formattedDate = escapeCsvField(formattedDate);
                    name = escapeCsvField(name);

                    csvWriter.write(String.valueOf(id) + ","
                            + String.valueOf(idCode) + ","
                            + title + ","
                            + description + ","
                            + formattedDate + ","
                            + name + "\n");
                }

                System.out.println("Архивированные нужды успешно сохранены в CSV файл.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Для отладки выводим полный стек-трейс ошибки
            System.err.println("Ошибка сохранения архивированных нужд в CSV файл: " + e.getMessage());
        }
    }

    // Метод для получения имени члена церкви по id_code
    private String getMemberNameByIdCode(Connection conn, int idCode) throws SQLException {
        String sql = "SELECT name FROM member WHERE id_code = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        }
        return ""; // Возвращаем пустую строку, если имя не найдено
    }

    // Метод для обработки полей перед записью в CSV-файл
    private static String escapeCsvField(String field) {
        if (field == null || field.isEmpty()) {
            return "";
        }

        // Если поле содержит кавычку или запятую, экранируем их
        String escapedField = field.replaceAll("\"", "\"\"");
        if (escapedField.contains(",") || escapedField.contains("\"")) {
            escapedField = "\"" + escapedField + "\"";
        }

        return escapedField;
    }
}
