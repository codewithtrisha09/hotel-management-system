package model;

public class Room {
    private int roomNo;
    private String type;
    private double price;
    private boolean available;
    private boolean cleaning;

    public Room(int roomNo, String type, double price) {
        this.roomNo = roomNo;
        this.type = type;
        this.price = price;
        this.available = true;
        this.cleaning = false;
    }

    public int getRoomNo() { return roomNo; }
    public String getType() { return type; }
    public double getPrice() { return price; }

    public boolean isAvailable() { 
        return available && !cleaning; 
    }
    public void setAvailable(boolean available) { this.available = available; }

    public boolean isCleaning() { return cleaning; }
    public void setCleaning(boolean cleaning) { this.cleaning = cleaning; }
}