package com.example.cafe;

import javafx.stage.Stage;


public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage appStage) {
        Window window = new Window();
        window.run(appStage);
    }
}