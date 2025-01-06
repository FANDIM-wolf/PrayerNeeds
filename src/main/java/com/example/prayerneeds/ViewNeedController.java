package com.example.prayerneeds;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewNeedController {

    @FXML
    private TextField nameInput;

    @FXML
    private void handleArchive() {
        // Логика для архивирования нужды
        String memberName = nameInput.getText();
        System.out.println("Архивирование нужды для члена церкви: " + memberName);
        // Добавьте здесь логику для архивирования нужды в базе данных
    }
}
