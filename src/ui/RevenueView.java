package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Booking;
import model.Room;
import service.HotelService;

public class RevenueView {

    private static TableView<Booking> table = new TableView<>();
    private static ObservableList<Booking> revenueList = FXCollections.observableArrayList();
    private static Label totalRevenueLabel = new Label("Total Revenue: ₹0.00");

    private static BarChart<String, Number> chart;

    public static Tab getTab() {

        Tab tab = new Tab("Revenue");

        totalRevenueLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #1565C0; -fx-font-weight: bold;");

        // ===== TABLE =====
        TableColumn<Booking, Integer> c1 = new TableColumn<>("Room No");
        c1.setCellValueFactory(b -> new javafx.beans.property.SimpleIntegerProperty(b.getValue().getRoomNo()).asObject());

        TableColumn<Booking, String> c2 = new TableColumn<>("Customer Name");
        c2.setCellValueFactory(b -> new javafx.beans.property.SimpleStringProperty(b.getValue().getName()));

        TableColumn<Booking, Integer> c3 = new TableColumn<>("Days");
        c3.setCellValueFactory(b -> new javafx.beans.property.SimpleIntegerProperty(b.getValue().getDays()).asObject());

        TableColumn<Booking, Integer> c4 = new TableColumn<>("Adults");
        c4.setCellValueFactory(b -> new javafx.beans.property.SimpleIntegerProperty(b.getValue().getAdults()).asObject());

        TableColumn<Booking, Integer> c5 = new TableColumn<>("Kids");
        c5.setCellValueFactory(b -> new javafx.beans.property.SimpleIntegerProperty(b.getValue().getKids()).asObject());

        TableColumn<Booking, String> c6 = new TableColumn<>("WiFi");
        c6.setCellValueFactory(b -> new javafx.beans.property.SimpleStringProperty(
                b.getValue().isWifi() ? "Yes" : "No"));

        TableColumn<Booking, String> c7 = new TableColumn<>("Mattress");
        c7.setCellValueFactory(b -> new javafx.beans.property.SimpleStringProperty(
                b.getValue().isExtraMattress() ? "Yes" : "No"));

        TableColumn<Booking, String> c8 = new TableColumn<>("Membership");
        c8.setCellValueFactory(b -> new javafx.beans.property.SimpleStringProperty(
                b.getValue().isMember() ? "Yes" : "No"));

        TableColumn<Booking, String> c9 = new TableColumn<>("Total Bill (₹)");
        c9.setCellValueFactory(b -> {
            double subtotal = b.getValue().getBill();
            double gst = subtotal * 0.10;
            double total = subtotal + gst;
            if (b.getValue().isMember()) total *= 0.9;
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", total));
        });

        table.getColumns().add(c1);
        table.getColumns().add(c2);
        table.getColumns().add(c3);
        table.getColumns().add(c4);
        table.getColumns().add(c5);
        table.getColumns().add(c6);
        table.getColumns().add(c7);
        table.getColumns().add(c8);
        table.getColumns().add(c9);
        table.setItems(revenueList);

        // ===== GRAPH =====
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Room Type");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Bookings");

        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Room Type Popularity");

        // ===== BUTTON =====
        Button refresh = new Button("Refresh Revenue");
        refresh.setMaxWidth(Double.MAX_VALUE);
        refresh.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold;");
        refresh.setOnAction(e -> refreshRevenue());

        VBox layout = new VBox(20, table, totalRevenueLabel, chart, refresh);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        tab.setContent(layout);

        refreshRevenue(); // initial load

        return tab;
    }

    // ===== REFRESH DATA + GRAPH =====
    public static void refreshRevenue() {

        revenueList.setAll(HotelService.getRevenueBookings());
        table.setItems(revenueList);

        double totalRevenue = 0;

        int single = 0, dbl = 0, deluxe = 0;

        for (Booking b : revenueList) {

            double subtotal = b.getBill();
            double gst = subtotal * 0.10;
            double total = subtotal + gst;
            if (b.isMember()) total *= 0.9;

            totalRevenue += total;

            // find room type
            String type = "";
            for (Room r : HotelService.getRooms()) {
                if (r.getRoomNo() == b.getRoomNo()) {
                    type = r.getType();
                    break;
                }
            }

            if (type.equalsIgnoreCase("Single")) single++;
            else if (type.equalsIgnoreCase("Double")) dbl++;
            else if (type.equalsIgnoreCase("Deluxe")) deluxe++;
        }

        totalRevenueLabel.setText("Total Revenue: ₹" + String.format("%.2f", totalRevenue));

        // ===== GRAPH =====
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings");

        series.getData().add(new XYChart.Data<>("Single", single));
        series.getData().add(new XYChart.Data<>("Double", dbl));
        series.getData().add(new XYChart.Data<>("Deluxe", deluxe));

        chart.getData().clear();
        chart.getData().add(series);
    }
}