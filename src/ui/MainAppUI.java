package ui;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import service.HotelService;

import java.time.LocalTime;

public class MainAppUI {

    public static void load(Stage stage) {

        Label title = new Label("🏨 Hotel Management Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label role = new Label("Logged in as: " + LoginView.currentUserRole);
        role.setStyle("-fx-text-fill: #E3F2FD;");

        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color:#D32F2F; -fx-text-fill:white;");
        logout.setOnAction(e -> LoginView.showLogin(stage));

        HBox topBar = new HBox(30, title, role, logout);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color:#1565C0; -fx-padding:15;");

        TabPane tabPane = new TabPane();

        tabPane.getTabs().add(getWelcomeTab(tabPane));
        tabPane.getTabs().add(RoomView.getTab());
        tabPane.getTabs().add(BookingView.getTab());
        tabPane.getTabs().add(FeedbackView.getTab());

        if (LoginView.currentUserRole.equals("ADMIN")) {
            tabPane.getTabs().add(RevenueView.getTab());
        }

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tabPane);

        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Dashboard");
    }

    // ===== WELCOME TAB =====
    private static Tab getWelcomeTab(TabPane tabPane) {

        Tab tab = new Tab("Welcome");

        // ===== GREETING =====
        String greeting = getGreeting();

        Label welcome = new Label(greeting + ", " + LoginView.currentUserRole + " 👋");
        welcome.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill:white;");

        Label subtitle = new Label("Manage your hotel operations efficiently");
        subtitle.setStyle("-fx-text-fill:#E3F2FD;");

        // ===== LIVE VALUE LABELS =====
        Label roomsValue = new Label();
        Label bookingValue = new Label();
        Label revenueValue = new Label();

        VBox stat1 = createStatBox("Rooms", roomsValue);
        VBox stat2 = createStatBox("Bookings", bookingValue);
        VBox stat3 = createStatBox("Revenue", revenueValue);

        HBox stats = new HBox(30, stat1, stat2, stat3);
        stats.setAlignment(Pos.CENTER);

        // ===== NAVIGATION CARDS =====
        VBox card1 = createCard("🛏 Rooms", "Manage rooms");
        card1.setOnMouseClicked(e -> tabPane.getSelectionModel().select(1));

        VBox card2 = createCard("📅 Booking", "Handle bookings");
        card2.setOnMouseClicked(e -> tabPane.getSelectionModel().select(2));

        VBox card3 = createCard("💬 Feedback", "View feedback");
        card3.setOnMouseClicked(e -> tabPane.getSelectionModel().select(3));

        VBox card4 = createCard("📊 Revenue", "View analytics");

     // CHANGE BACKGROUND TO PINK
     card4.setStyle(
             "-fx-background-color: #FFC0CB;" +   // pink
             "-fx-padding:15;" +
             "-fx-background-radius:12;" +
             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8,0,0,3);" +
             "-fx-cursor: hand;"
     );
        card4.setOnMouseClicked(e -> {
            if (LoginView.currentUserRole.equals("ADMIN")) {
                tabPane.getSelectionModel().select(4);
            }
        });

        HBox cards = new HBox(20, card1, card2, card3, card4);
        cards.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, welcome, subtitle, stats, cards);
        content.setAlignment(Pos.CENTER);

        // ===== BACKGROUND =====
        Image bgImage = new Image("file:/C:/Users/Trisha/OneDrive/Desktop/WelcomePg.jpg");

        BackgroundSize size = new BackgroundSize(
                BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true
        );

        BackgroundImage bImg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                size
        );

        StackPane root = new StackPane();
        root.setBackground(new Background(bImg));

        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.25);");

        root.getChildren().addAll(overlay, content);

        // ===== AUTO REFRESH (EVERY 2 SECONDS) =====
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {

                    int totalRooms = HotelService.getRooms().size();
                    int totalBookings = HotelService.getAllBookings().size();
                    double revenue = HotelService.getTotalRevenue();

                    roomsValue.setText(String.valueOf(totalRooms));
                    bookingValue.setText(String.valueOf(totalBookings));
                    revenueValue.setText("₹" + String.format("%.0f", revenue));
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        tab.setContent(root);
        tab.setClosable(false);

        return tab;
    }

    // ===== GREETING =====
    private static String getGreeting() {
        int hour = LocalTime.now().getHour();

        if (hour < 12) return "Good Morning";
        else if (hour < 17) return "Good Afternoon";
        else return "Good Evening";
    }

    // ===== CARD =====
    private static VBox createCard(String title, String desc) {

        Label t = new Label(title);
        t.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");

        Label d = new Label(desc);

        VBox box = new VBox(10, t, d);
        box.setAlignment(Pos.CENTER);

        box.setStyle(
                "-fx-background-color: white;" +
                "-fx-padding:15;" +
                "-fx-background-radius:12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8,0,0,3);" +
                "-fx-cursor: hand;"
        );

        box.setOnMouseEntered(e -> {
            box.setScaleX(1.05);
            box.setScaleY(1.05);
        });

        box.setOnMouseExited(e -> {
            box.setScaleX(1);
            box.setScaleY(1);
        });

        return box;
    }

    // ===== STAT BOX =====
    private static VBox createStatBox(String title, Label valueLabel) {

        Label t = new Label(title);
        t.setStyle("-fx-text-fill:#E3F2FD;");

        valueLabel.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-text-fill:white;");

        VBox box = new VBox(5, t, valueLabel);
        box.setAlignment(Pos.CENTER);

        box.setStyle(
                "-fx-background-color: rgba(255,255,255,0.15);" +
                "-fx-padding:15;" +
                "-fx-background-radius:10;"
        );

        return box;
    }
}