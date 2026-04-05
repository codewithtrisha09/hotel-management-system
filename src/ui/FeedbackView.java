package ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Feedback;
import service.HotelService;

public class FeedbackView {

    private static TableView<Feedback> table;
    private static ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();
    private static Label status;

    public static Tab getTab() {
        Tab tab = new Tab("Feedback");

        Label title = new Label("Customer Feedback");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: #FF5722; -fx-font-weight: bold;");

        TextField name = new TextField();
        name.setPromptText("Customer Name");

        TextField roomNo = new TextField();
        roomNo.setPromptText("Room No");

        TextArea comments = new TextArea();
        comments.setPromptText("Write your feedback here...");
        comments.setPrefRowCount(4);

        Spinner<Integer> rating = new Spinner<>(1, 5, 5);

        Button submitBtn = new Button("Submit Feedback");
        status = new Label();
        
//CellValueFactory is used to bind table columns with model data
        table = new TableView<>();
        table.setItems(feedbackList);
        TableColumn<Feedback, String> c1 = new TableColumn<>("Customer");
        c1.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomerName())
        );

        TableColumn<Feedback, Integer> c2 = new TableColumn<>("Room No");
        c2.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRoomNo()).asObject());
        TableColumn<Feedback, String> c3 = new TableColumn<>("Comments");
        c3.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getComments()) );
        c3.setPrefWidth(320); // wider column
        c3.setCellFactory(column -> new TableCell<Feedback, String>() {
            private final Label label = new Label();
            {
                label.setWrapText(true);
                label.setMaxWidth(300);
            }
            @Override
            protected void updateItem(String text, boolean empty) {
                super.updateItem(text, empty);

                if (empty || text == null) {
                    setGraphic(null);
                } else {
                    label.setText(text);//Sentiment analysis
                    String lower = text.toLowerCase();
                    if (lower.contains("good") || lower.contains("great") || lower.contains("excellent")
                            || lower.contains("nice") || lower.contains("amazing")) {

                        label.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");}
                    else if (lower.contains("bad") || lower.contains("poor") || lower.contains("worst")
                            || lower.contains("dirty") || lower.contains("slow")) {

                        label.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                    } else {
                        label.setStyle("-fx-text-fill: black;");}
                    setGraphic(label); } } });
        TableColumn<Feedback, Integer> c4 = new TableColumn<>("Rating");
        c4.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getRating()).asObject() );
        table.getColumns().add(c1);
        table.getColumns().add(c2);
        table.getColumns().add(c3);
        table.getColumns().add(c4);
        table.setPrefHeight(250);
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Customer Name:"), name);
        form.addRow(1, new Label("Room No:"), roomNo);
        form.addRow(2, new Label("Comments:"), comments);
        form.addRow(3, new Label("Rating:"), rating);
        form.addRow(4, submitBtn);
        VBox v = new VBox(15, title, form, status, table);
        v.setStyle("-fx-padding: 20; -fx-background-color: #f5f5f5; -fx-background-radius: 12;");
        tab.setContent(v);
        refreshTable();
        submitBtn.setOnAction(e -> {
            try {
                if (name.getText().isEmpty() || comments.getText().isEmpty()) {
                    showStatus("Fields cannot be empty!", false);
                    return;
                }

                int rno = Integer.parseInt(roomNo.getText());
//Ensures only valid customers give feedback
                if (HotelService.getBooking(rno) == null) {
                    showStatus("Invalid room number!", false);
                    return; }
                Feedback f = new Feedback(
                        name.getText(),
                        rno,
                        comments.getText(),
                        rating.getValue()
                );
                HotelService.addFeedback(f);
                refreshTable();
                showStatus("Feedback submitted!", true);
                name.clear();
                roomNo.clear();
                comments.clear();
                rating.getValueFactory().setValue(5);
            } catch (Exception ex) {
                showStatus("Error: Check inputs!", false);}
        });
 return tab;}
    public static void refreshTable() {
        feedbackList.setAll(HotelService.getFeedbacks());
        table.setItems(feedbackList);}
    private static void showStatus(String message, boolean success) {
        status.setText(message);
        status.setStyle(success ? "-fx-text-fill: green;" : "-fx-text-fill: red;"); }}