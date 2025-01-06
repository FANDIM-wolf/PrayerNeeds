package com.example.prayerneeds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/prayerneeds/main_screen.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(HelloApplication.class.getResource("/com/example/prayerneeds/styles.css").toExternalForm());
        // Загрузка иконки из ресурсов

        InputStream iconStream = HelloApplication.class.getResourceAsStream("/bird.png");
        if (iconStream != null) {
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            System.out.println("Icon loaded successfully!");
        } else {
            System.out.println("Icon not found!");
        }
        stage.setTitle("Молитвенные нужды");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}