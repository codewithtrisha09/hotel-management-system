package model;

public class Feedback {
    private String customerName;
    private int roomNo;
    private String comments;
    private int rating;

    public Feedback(String customerName, int roomNo, String comments, int rating) {
        this.customerName = customerName;
        this.roomNo = roomNo;
        this.comments = comments;
        this.rating = rating;
    }

    public String getCustomerName() { return customerName; }
    public int getRoomNo() { return roomNo; }
    public String getComments() { return comments; }
    public int getRating() { return rating; }
}