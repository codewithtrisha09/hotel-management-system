package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginView {

    public static String currentUserRole = "";

    public static void showLogin(Stage stage) {

        Label title = new Label("🏨 Hotel Management");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill:#0D47A1;");

        Label subtitle = new Label("Login to continue");
        subtitle.setStyle("-fx-text-fill:#1565C0;");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        TextField visiblePassword = new TextField();

        
        visiblePassword.textProperty().bindBidirectional(password.textProperty());//for hiding password
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
//used bidirectional binding to sync the password fields and toggle visibility dynamically
        CheckBox showPass = new CheckBox("Show Password");

        showPass.setOnAction(e -> {
            boolean show = showPass.isSelected();
            visiblePassword.setVisible(show);
            visiblePassword.setManaged(show);
            password.setVisible(!show);
            password.setManaged(!show);
        });

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color:#1565C0; -fx-text-fill:white;");

        Label status = new Label();

        loginBtn.setOnAction(e -> {
            String user = username.getText();
            String pass = password.getText();

            if (user.equals("admin") && pass.equals("admin123")) {
                currentUserRole = "ADMIN";
                MainAppUI.load(stage);
            } else if (user.equals("staff") && pass.equals("staff123")) {
                currentUserRole = "STAFF";
                MainAppUI.load(stage);
            } else {
                status.setText("Invalid login!");
                status.setStyle("-fx-text-fill:red;");
            }
        });

        VBox card = new VBox(15,
                title, subtitle,
                username,
                password, visiblePassword,
                showPass,
                loginBtn,
                status
        );

        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.9);" +
                "-fx-padding:30;" +
                "-fx-background-radius:15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12,0,0,5);"
        );

        // ===== IMAGE VIEW (KEY FIX) =====
        Image image = new Image("file:/C:/Users/Trisha/OneDrive/Desktop/hotelLounge.jpg");

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        // FIT TO WINDOW (no cropping)
        imageView.setFitWidth(900);
        imageView.setFitHeight(600);

        // ===== OVERLAY (VERY LIGHT) =====
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
        
//used StackPane to layer the background image, overlay, and login form for better UI design
        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, overlay, card);

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}

