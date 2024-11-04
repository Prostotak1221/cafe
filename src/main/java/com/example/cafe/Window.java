package com.example.cafe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Window {

    private static Stage appStage;
    private final TextField usernameInput = new TextField();
    private final PasswordField passwordInput = new PasswordField();
    private Connection connection;

    public static void setStageTitle(String newTitle) {
        appStage.setTitle(newTitle);
    }

    public void run(Stage appStage) {

        this.appStage = appStage;
        appStage.setTitle("Кофейня");
        appStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cafe.png"))));

        GridPane grid = createLoginGrid();

        Scene scene = new Scene(grid, 650, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        appStage.setScene(scene);
        appStage.show();
    }

    private void setCSS(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    }

    private void setCSS(GridPane scene) {
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
    }

    private GridPane createLoginGrid() {
        Label usernameLabel = new Label("Логин");
        Label passwordLabel = new Label("Пароль");

        Button loginButton = new Button("Подключиться");
        loginButton.setOnAction(e -> handleLogin());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(6);
        grid.setHgap(8);
        grid.getStyleClass().add("login-form");

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameInput, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordInput, 1, 1);
        grid.add(loginButton, 1, 2);

        return grid;
    }

    private void handleLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        UserData.setCredentials(username, password);

        try {
            connection = DBConnect.connect();

            assert connection != null;
            if (connection.isValid(5)) {
                showTableList();

            } else {
                System.out.println("Не удается подключиться к серверу баз данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Button> createTableButtons(List<String> tableNames) {
        List<Button> buttons = new ArrayList<>();

        for (String tableName : tableNames) {
            Button button = new Button(tableName);
            button.setOnAction(e -> handleButtonClick(tableName));
            buttons.add(button);
        }

        return buttons;
    }

    private Scene createTableGrid(List<Button> tableButtons) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(9);
        grid.setHgap(12);

        for (int i = 0; i < tableButtons.size(); i++) {
            int row = i / 4;
            int col = i % 4;

            GridPane.setConstraints(tableButtons.get(i), col, row);
            grid.getChildren().add(tableButtons.get(i));
        }

        grid.getStyleClass().add("tables-list");

        Scene tableScene = new Scene(grid, 650, 400);
        setCSS(tableScene);

        return tableScene;

    }


    private void showTableList() {
        List<String> tableNames = SQLUtils.getTablesNames(connection);
        List<Button> tableButtons = createTableButtons(tableNames);
        Scene tableScene = createTableGrid(tableButtons);
        setCSS(tableScene);

        appStage.setScene(tableScene);
        setStageTitle("Кофейня - список таблиц");
    }

    private void handleButtonClick(String tableName) {
        try (Connection connection = DBConnect.connect()) {
            List<String> columnNames = SQLUtils.getColumnsNames(connection, tableName);
            List<List<String>> tableData = SQLUtils.getDataFromTable(connection, tableName);

            GridPane tableScene = TableEditor.createTable(tableName, columnNames, tableData, this::showTableList);
            setCSS(tableScene);
            appStage.setScene(new Scene(tableScene, 650, 400));
            setStageTitle("Кофейня - редактор таблиц");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

