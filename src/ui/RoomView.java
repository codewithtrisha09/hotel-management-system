package ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Room;
import service.HotelService;
public class RoomView {
    private static TableView<Room> table;
    private static ObservableList<Room> roomList = FXCollections.observableArrayList();
    private static Label status;
    public static Tab getTab() {
        Tab tab = new Tab("Rooms");
        Label title = new Label("Room Management");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: #2E7D32; -fx-font-weight: bold;");
        TextField roomNo = new TextField();
        roomNo.setPromptText("Room No");
        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("Single", "Double", "Deluxe");
        type.setPromptText("Room Type");
        TextField price = new TextField();
        price.setPromptText("Price per day");
        Button addBtn = new Button("Add Room");
        Button showAllBtn = new Button("Show All");
        Button showAvailableBtn = new Button("Available Only");
        status = new Label();
        status.setAlignment(Pos.CENTER);
        table = new TableView<>();
        table.setItems(roomList);
        TableColumn<Room, Integer> c1 = new TableColumn<>("Room No");
        c1.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRoomNo()).asObject()
        );

        TableColumn<Room, String> c2 = new TableColumn<>("Type");
        c2.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getType())
        );

        TableColumn<Room, Double> c3 = new TableColumn<>("Price");
        c3.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrice()).asObject()
        );

        TableColumn<Room, String> c4 = new TableColumn<>("Status");
        c4.setCellValueFactory(cell -> {
            Room r = cell.getValue();
            String s;

            if (r.isCleaning()) s = "Cleaning";
            else if (r.isAvailable()) s = "Available";
            else s = "Booked";

            return new javafx.beans.property.SimpleStringProperty(s);
        });
        table.getColumns().add(c1);
        table.getColumns().add(c2);
        table.getColumns().add(c3);
        table.getColumns().add(c4);
        table.setPrefHeight(250);
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Room No:"), roomNo);
        form.addRow(1, new Label("Type:"), type);
        form.addRow(2, new Label("Price:"), price);
        form.add(addBtn, 1, 3);
        form.add(showAllBtn, 1, 4);
        form.add(showAvailableBtn, 1, 5);

        VBox left = new VBox(15, title, form, table, status);
        left.setAlignment(Pos.TOP_CENTER);
        left.setPrefWidth(500);
        Image img = new Image("file:/C:/Users/Trisha/OneDrive/Desktop/room.jfif");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(320);
        imageView.setPreserveRatio(true);
        HBox root = new HBox(40, left, imageView);
        root.setAlignment(Pos.CENTER);
        tab.setContent(root);
        addBtn.setOnAction(e -> {
            try {
                Room r = new Room(
                        Integer.parseInt(roomNo.getText()),
                        type.getValue(),
                        Double.parseDouble(price.getText()) );
                HotelService.addRoom(r);
                refreshTable();
                showStatus("Room added successfully!", true);
                roomNo.clear();
                type.setValue(null);
                price.clear();  } catch (Exception ex) {
                showStatus("Error: Check inputs!", false);  } });
        showAllBtn.setOnAction(e -> refreshTable());
        showAvailableBtn.setOnAction(e -> {
            ObservableList<Room> available = FXCollections.observableArrayList();
            for (Room r : HotelService.getRooms()) {
                if (r.isAvailable()) {
                    available.add(r);  } }
            table.setItems(available); });
        refreshTable();
        return tab;  }

    public static void refreshTable() {
        roomList.setAll(HotelService.getRooms());
        table.setItems(roomList);
    }

    private static void showStatus(String message, boolean success) {
        status.setText(message);
        status.setStyle(success ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}