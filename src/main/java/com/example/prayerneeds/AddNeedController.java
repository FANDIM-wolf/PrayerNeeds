package com.example.prayerneeds;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.List;

public class AddNeedController {

    @FXML
    private TextField needNameField;

    @FXML
    private TextArea needDescriptionField;

    @FXML
    private ChoiceBox<Member> memberChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        // Инициализация ChoiceBox с данными членов церкви из базы данных
        List<Member> members = DatabaseManager.getMembers();
        ObservableList<Member> memberList = FXCollections.observableArrayList(members);
        memberChoiceBox.setItems(memberList);

        // Установка конвертера для отображения только имени в ChoiceBox
        memberChoiceBox.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member != null ? member.getName() : "";
            }

            @Override
            public Member fromString(String string) {
                return memberList.stream().filter(member -> member.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @FXML
    public void handleSaveAction() {
        // Логика для сохранения данных
        String needName = needNameField.getText();
        String needDescription = needDescriptionField.getText();
        Member selectedMember = memberChoiceBox.getValue();
        String selectedDate = datePicker.getValue().toString();

        if (selectedMember != null) {
            int memberIdCode = selectedMember.getIdCode();
            // Сохранение данных в базу данных
            DatabaseManager.savePrayerNeed(memberIdCode, needName, needDescription, selectedDate, 0);
            System.out.println("Название нужды: " + needName);
            System.out.println("Описание нужды: " + needDescription);
            System.out.println("Выбранный член церкви: " + selectedMember.getName());
            System.out.println("ID код члена церкви: " + memberIdCode);
            System.out.println("Выбранная дата: " + selectedDate);
        } else {
            System.out.println("Член церкви не выбран.");
        }
    }
}
