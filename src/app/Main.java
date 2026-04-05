package app;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        LoginView.showLogin(stage); 
    }

    public static void main(String[] args) {
        launch();
    }
}










