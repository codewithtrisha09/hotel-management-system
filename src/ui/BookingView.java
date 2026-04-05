package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import model.Booking;
import model.Room;
import service.HotelService;

public class BookingView {

    private static Label status;

    public static Tab getTab() {

        Tab tab = new Tab("Booking");

        Label title = new Label("Room Booking");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:#1565C0;");

        TextField name = new TextField();
        TextField roomNo = new TextField();
        TextField days = new TextField();
        TextField adults = new TextField();
        TextField kids = new TextField();

        name.setPromptText("Customer Name");
        roomNo.setPromptText("Room No");
        days.setPromptText("Days");
        adults.setPromptText("Adults (max 2)");
        kids.setPromptText("Kids");

        CheckBox wifi = new CheckBox("WiFi ₹500/day");
        CheckBox member = new CheckBox("Membership (10% Discount)");
        CheckBox mattress = new CheckBox("Extra Mattress ₹300/day");

        Button bookBtn = new Button("Book Room");
        Button checkoutBtn = new Button("Checkout");

        bookBtn.setMaxWidth(Double.MAX_VALUE);
        checkoutBtn.setMaxWidth(Double.MAX_VALUE);

        status = new Label();
        status.setAlignment(Pos.CENTER);

        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(15);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(65);
        form.getColumnConstraints().addAll(col1, col2);

        form.addRow(0, new Label("Customer Name:"), name);
        form.addRow(1, new Label("Room No:"), roomNo);
        form.addRow(2, new Label("Days:"), days);
        form.addRow(3, new Label("Adults:"), adults);
        form.addRow(4, new Label("Kids:"), kids);

        VBox options = new VBox(10, wifi, member, mattress);
        form.addRow(5, new Label("Options:"), options);

        form.add(bookBtn, 1, 6);
        form.add(checkoutBtn, 1, 7);

        VBox left = new VBox(20, title, form, status);
        left.setPrefWidth(500);

        Image img = new Image("file:/C:/Users/Trisha/OneDrive/Desktop/hotell.jpg");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(320);
        imageView.setPreserveRatio(true);

        HBox root = new HBox(40, left, imageView);
        root.setAlignment(Pos.CENTER);

        tab.setContent(root);

        // ===== BOOK ROOM =====
        bookBtn.setOnAction(e -> {
            try {
                int rno = Integer.parseInt(roomNo.getText());
                int numDays = Integer.parseInt(days.getText());
                int numAdults = Integer.parseInt(adults.getText());
                int numKids = Integer.parseInt(kids.getText());

                if (numAdults < 1 || numAdults > 2) {
                    showStatus("Max 2 adults allowed!", false);
                    return;
                }

                if (numKids > 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Extra mattress auto-added!");
                    alert.showAndWait();
                    mattress.setSelected(true);
                } else if (numKids == 1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Add extra mattress?");
                    alert.showAndWait().ifPresent(res -> mattress.setSelected(res == ButtonType.OK));
                }

                boolean success = HotelService.bookRoom(
                        name.getText(), rno, wifi.isSelected(), numDays,
                        member.isSelected(), numAdults, numKids, mattress.isSelected()
                );

                if (success) {
                    showStatus("Room booked successfully!", true);
                    RoomView.refreshTable();
                } else {
                    showStatus("Room not available!", false);
                }

            } catch (Exception ex) {
                showStatus("Invalid input!", false);
            }
        });

        // ===== CHECKOUT =====
        checkoutBtn.setOnAction(e -> {
            try {
                int rno = Integer.parseInt(roomNo.getText());
                Booking b = HotelService.getBooking(rno);

                if (b == null) {
                    showStatus("No booking found!", false);
                    return;
                }

                // ===== CALCULATIONS =====
                double roomRate = 0;
                for (Room r : HotelService.getRooms()) {
                    if (r.getRoomNo() == rno) {
                        roomRate = r.getPrice();
                        break;
                    }
                }

                double roomCharge = roomRate * b.getDays();
                double wifiCharge = b.isWifi() ? 500 * b.getDays() : 0;
                double mattressCharge = b.isExtraMattress() ? 300 * b.getDays() : 0;

                double subtotal = roomCharge + wifiCharge + mattressCharge;
                double gst = subtotal * 0.10;
                double discount = b.isMember() ? subtotal * 0.10 : 0;
                double total = subtotal + gst - discount;

                // ===== BILL FUNCTION =====
                Runnable showBill = () -> {
                    VBox invoice = new VBox(12);
                    invoice.setAlignment(Pos.CENTER);
                    invoice.setStyle("-fx-padding:25; -fx-background-color:#ffffff;");

                    Label titleLbl = new Label("HOTEL INVOICE");
                    titleLbl.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-text-fill:#1565C0;");

                    Separator s1 = new Separator();

                    VBox info = new VBox(5,
                            new Label("Customer: " + b.getName()),
                            new Label("Room No: " + b.getRoomNo()),
                            new Label("Days: " + b.getDays())
                    );
                    info.setAlignment(Pos.CENTER);

                    Separator s2 = new Separator();

                    GridPane charges = new GridPane();
                    charges.setAlignment(Pos.CENTER);
                    charges.setHgap(80);
                    charges.setVgap(8);

                    charges.addRow(0, new Label("Room Charge"), new Label("₹" + roomCharge));
                    charges.addRow(1, new Label("WiFi"), new Label("₹" + wifiCharge));
                    charges.addRow(2, new Label("Mattress"), new Label("₹" + mattressCharge));

                    Separator s3 = new Separator();

                    GridPane totals = new GridPane();
                    totals.setAlignment(Pos.CENTER);
                    totals.setHgap(80);
                    totals.setVgap(8);

                    totals.addRow(0, new Label("Subtotal"), new Label("₹" + subtotal));
                    totals.addRow(1, new Label("GST (10%)"), new Label("₹" + gst));
                    totals.addRow(2, new Label("Discount"), new Label("-₹" + discount));

                    Label totalLbl = new Label("TOTAL: ₹" + String.format("%.2f", total));
                    totalLbl.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:green;");

                    Label thanks = new Label("Thank you! Visit again 😊");

                    invoice.getChildren().addAll(
                            titleLbl, s1,
                            info,
                            s2,
                            charges,
                            s3,
                            totals,
                            totalLbl,
                            thanks
                    );

                    Stage billStage = new Stage();
                    billStage.setTitle("Invoice");
                    billStage.setScene(new Scene(invoice, 380, 500));
                    billStage.show();

                    HotelService.checkout(rno);
                    RoomView.refreshTable();
                    showStatus("Checked out! Cleaning started...", true);
                };

                // ===== MEMBERSHIP FLOW =====
                if (!b.isMember()) {

                    Alert ask = new Alert(Alert.AlertType.CONFIRMATION);
                    ask.setHeaderText("Do you want to take membership?");
                    ask.setContentText("Get 10% discount and special benefits!");

                    ask.showAndWait().ifPresent(res -> {

                        if (res == ButtonType.OK) {

                            Stage memStage = new Stage();
                            memStage.setTitle("Membership Registration");

                            GridPane memForm = new GridPane();
                            memForm.setHgap(10);
                            memForm.setVgap(10);

                            TextField fullName = new TextField();
                            TextField phone = new TextField();
                            TextField address = new TextField();

                            DatePicker dob = new DatePicker();

                            CheckBox married = new CheckBox("Married?");
                            DatePicker anniversary = new DatePicker();
                            anniversary.setDisable(true);

                            married.selectedProperty().addListener((obs, o, n) -> {
                                anniversary.setDisable(!n);
                            });

                            memForm.addRow(0, new Label("Full Name:"), fullName);
                            memForm.addRow(1, new Label("Phone:"), phone);
                            memForm.addRow(2, new Label("Address:"), address);
                            memForm.addRow(3, new Label("DOB:"), dob);
                            memForm.addRow(4, married);
                            memForm.addRow(5, new Label("Anniversary:"), anniversary);

                            Button submit = new Button("Submit");

                            submit.setOnAction(ev -> {
                                memStage.close();

                                Alert done = new Alert(Alert.AlertType.INFORMATION);
                                done.setHeaderText("Membership Registered!");
                                done.setContentText("Enjoy discounts 🎉");
                                done.showAndWait();

                                showBill.run();
                            });

                            VBox v = new VBox(10, memForm, submit);
                            v.setStyle("-fx-padding:15;");
                            memStage.setScene(new Scene(v, 350, 300));
                            memStage.show();

                        } else {
                            showBill.run();
                        }
                    });

                } else {
                    showBill.run();
                }

            } catch (Exception ex) {
                showStatus("Invalid Room No!", false);
            }
        });

        return tab;
    }

    private static void showStatus(String msg, boolean ok) {
        status.setText(msg);
        status.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}