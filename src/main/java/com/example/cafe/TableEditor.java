package com.example.cafe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

import static com.example.cafe.DBHandler.*;

public class TableEditor {
    public static GridPane createTable(String tableName, List<String> columnNames, List<List<String>> tableData,
                                       Runnable backButtonAction) {
        TableView<ObservableList<String>> tableView = createTableView(columnNames, tableData);
        GridPane buttonPane = createButtonPane(tableView, backButtonAction, tableName);

        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1);
        gridPane.add(buttonPane, 0, 0);
        gridPane.add(tableView, 0, 1);

        return gridPane;
    }


    private static void makeRowEditable(TableColumn<ObservableList<String>, String> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            TablePosition<ObservableList<String>, String> position = event.getTablePosition();
            ObservableList<String> row = position.getTableView().getItems().get(position.getRow());
            row.set(position.getColumn(), event.getNewValue());
        });
    }

    private static TableView<ObservableList<String>> createTableView(List<String> columnNames, List<List<String>> tableData) {
        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.getStyleClass().add("table");

        for (int i = 0; i < columnNames.size(); i++) {
            int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(columnIndex)));
            tableView.getColumns().add(column);
            makeRowEditable(column);
        }

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (List<String> rowData : tableData) {
            data.add(FXCollections.observableArrayList(rowData));
        }
        tableView.setItems(data);

        return tableView;
    }

    private static GridPane createButtonPane(TableView<ObservableList<String>> tableView, Runnable backButtonAction,
                                             String tableName) {
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));

        Button backButton = createButton("Вернуться назад", e -> backButtonAction.run(), "button-back");
        Button editButton = createButton("Изменить строку", e ->
        {
            tableView.setEditable(true);
            isEditPressed();
        }, "button-style");

        Button saveButton = createButton("Сохранить изменения", e -> handleSaveChangesToDatabase(tableView, tableName), "button-style");
        Button deleteButton = createButton("Удалить строку", e -> handleRemoveRow(tableView, tableName), "button-style");
        Button addButton = createButton("Добавить строку", e -> handleAddRow(tableView), "button-style");


        GridPane.setConstraints(backButton, 0, 0);
        GridPane.setConstraints(deleteButton, 1, 0);
        GridPane.setConstraints(addButton, 2, 0);
        GridPane.setConstraints(editButton, 3, 0);
        GridPane.setConstraints(saveButton, 4, 0);

        buttonPane.getChildren().addAll(backButton, editButton, saveButton, deleteButton, addButton);

        return buttonPane;
    }

    public static boolean isEditPressed() {
        Window.setStageTitle("Кофейня - редактор таблиц*");
        return true;
    }

    private static Button createButton(String text, EventHandler<ActionEvent> eventHandler, String styleClass) {
        Button button = new Button(text);
        button.setOnAction(eventHandler);
        button.getStyleClass().add(styleClass);
        return button;
    }

}

