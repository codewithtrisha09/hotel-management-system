package ui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Member;
public class MembershipForm {
    public static Member showForm() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);//This makes the window modal(User must fill/close this first)
        window.setTitle("Membership Details");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        TextField fullName = new TextField();
        TextField dob = new TextField();
        TextField anniversary = new TextField();
        TextField contact = new TextField();
        TextField address = new TextField();
        grid.addRow(0, new Label("Full Name:"), fullName);
        grid.addRow(1, new Label("DOB (yyyy-mm-dd):"), dob);
        grid.addRow(2, new Label("Anniversary (yyyy-mm-dd):"), anniversary);
        grid.addRow(3, new Label("Contact No:"), contact);
        grid.addRow(4, new Label("Address:"), address);
        Button submit = new Button("Submit");
        grid.add(submit, 1, 5);
        final Member[] member = new Member[1];//used an array to store the Member object because lambda expressions require effectively final variables.
        submit.setOnAction(e -> {
            member[0] = new Member(
                    fullName.getText(),
                    dob.getText(),
                    anniversary.getText(),
                    contact.getText(),
                    address.getText());
            window.close(); });
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
        return member[0];  }}