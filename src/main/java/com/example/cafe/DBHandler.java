package com.example.cafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBHandler {

    public static void handleSaveChangesToDatabase(TableView<ObservableList<String>> tableView, String tableName) {
        List<List<String>> updatedData = new ArrayList<>();
        List<List<String>> newData = new ArrayList<>();

        for (ObservableList<String> rowData : tableView.getItems()) {
            if (rowData.contains("")) {
                newData.add(new ArrayList<>(rowData));
            } else {
                updatedData.add(new ArrayList<>(rowData));
            }
        }

        try (Connection connection = DBConnect.connect()) {
            if (!newData.isEmpty()) {
                SQLUtils.insertRowsInTable(connection, tableName, newData);
            }

            if (!updatedData.isEmpty()) {
                SQLUtils.updateDataInTable(connection, tableName, updatedData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Window.setStageTitle("Кофейня - редактор таблиц");
    }

    public static void handleAddRow(TableView<ObservableList<String>> tableView) {
        ObservableList<String> newRow = FXCollections.observableArrayList();

        for (int i = 0; i < tableView.getColumns().size(); i++) {
            newRow.add("");
        }

        tableView.getItems().add(newRow);
        Window.setStageTitle("Кофейня - редактор таблиц*");
    }

    public static void handleRemoveRow(TableView<ObservableList<String>> tableView, String tableName) {

        ObservableList<ObservableList<String>> selectedRows = tableView.getSelectionModel().getSelectedItems();
        List<List<String>> rowsToRemove = new ArrayList<>();

        for (ObservableList<String> selectedRow : selectedRows) {
            rowsToRemove.add(new ArrayList<>(selectedRow));
        }

        try (Connection connection = DBConnect.connect()) {
            SQLUtils.deleteRowsInTable(connection, tableName, rowsToRemove);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.getItems().removeAll(selectedRows);
        Window.setStageTitle("Кофейня - редактор таблиц*");
    }
}
